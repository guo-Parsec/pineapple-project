package org.pineapple.common.access.interceptors;

import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.pineapple.common.access.annotations.AutoFillProvider;
import org.pineapple.common.access.constant.InterceptorNameConstant;
import org.pineapple.common.access.provider.fill.FieldFillProvider;
import org.pineapple.common.access.types.FieldFillType;
import org.pineapple.common.access.utils.MybatisInterceptorUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>字段自动填充拦截器</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/18
 */
@Component(InterceptorNameConstant.FieldAutoFillInterceptor)
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class FieldAutoFillInterceptor extends SimpleMybatisInterceptor implements InitializingBean {
    private static Set<SqlCommandType> allowSqlCommandTypeSet = null;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Executor executor = (Executor) invocation.getTarget();
        // 获取 Executor#update() 方法的两个参数
        Object[] args = invocation.getArgs();
        // 获取 Executor#update() 第一个参数
        MappedStatement ms = (MappedStatement) args[0];
        if (this.isNotAllowIntercept(ms)) {
            return invocation.proceed();
        }
        // 获取 Executor#update() 第二个参数
        Object parameter = args[1];
        SqlCommandType sqlCommandType = ms.getSqlCommandType();
        FieldFillType currentFieldFillType = FieldFillType.findByCode(sqlCommandType);
        if (allowSqlCommandTypeSet.contains(sqlCommandType) && currentFieldFillType != null) {
            return executeFieldAutoFill(executor, ms, parameter, currentFieldFillType);
        }
        return invocation.proceed();
    }


    /**
     * <p>执行字段填充</p>
     *
     * @param executor             执行器
     * @param ms                   MappedStatement
     * @param parameter            parameter
     * @param currentFieldFillType 当前字段填充类型
     * @return java.lang.Object
     * @author pinea
     * @date 2023/4/18 21:34
     */
    private Object executeFieldAutoFill(final Executor executor, final MappedStatement ms, final Object parameter, FieldFillType currentFieldFillType) throws Throwable {
        // 获取被代理的方法
        Method executeMapperMethod = MybatisInterceptorUtil.findExecuteMapperMethod(ms);
        // 获取被代理方法中的参数列表
        Parameter[] parameters = executeMapperMethod.getParameters();
        // 判断是否是MapperMethod.ParamMap类型，如果是则获取MapperMethod.ParamMap
        MapperMethod.ParamMap<?> paramMap = parameter instanceof MapperMethod.ParamMap ? (MapperMethod.ParamMap<?>) parameter : null;
        // 参数顺序从1开始
        int index = 1;
        for (Parameter methodParam : parameters) {
            Param param = methodParam.getAnnotation(Param.class);
            // 获取参数真实值：MapperMethod.ParamMap如果为空，则直接返回原始parameter;MapperMethod.ParamMap中读取真实值
            // 当前参数如果存在Param注解，则从MapperMethod.ParamMap中获取的key即为Param.value();否则按照参数顺序依次使用param+index的方式获取
            Object val = paramMap != null ? paramMap.get(param != null ? param.value() : PARAM_PREFIX + index) : parameter;
            if (val instanceof Collection) {
                // 如果是集合类型则循环遍历注入id
                Collection<?> coll = (Collection<?>) val;
                coll.forEach(collVal -> this.doObjectFieldFill(collVal, currentFieldFillType));
            } else {
                doObjectFieldFill(val, currentFieldFillType);
            }
            // 记录参数顺序自增
            index++;
        }
        return executor.update(ms, parameter);
    }

    /**
     * <p>执行{@code object}的字段填充</p>
     *
     * @param obj                  obj
     * @param currentFieldFillType currentFieldFillType
     * @author pinea
     * @date 2023/4/18 21:34
     */
    private void doObjectFieldFill(Object obj, FieldFillType currentFieldFillType) {
        AutoFillProvider autoFillProvider = null;
        List<FieldFillProvider> providers = Lists.newArrayList();
        Class<?> modelClass = obj.getClass();
        while (modelClass != null) {
            autoFillProvider = modelClass.getAnnotation(AutoFillProvider.class);
            if (autoFillProvider != null) {
                FieldFillProvider provider = SpringUtil.getBean(autoFillProvider.provider());
                if (provider != null) {
                    providers.add(provider);
                }
            }
            modelClass = modelClass.getSuperclass();
        }
        providers.forEach(provider -> {
            provider.doFill(obj, currentFieldFillType);
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        allowSqlCommandTypeSet = Sets.newHashSet();
        allowSqlCommandTypeSet.add(SqlCommandType.INSERT);
        allowSqlCommandTypeSet.add(SqlCommandType.UPDATE);
        allowSqlCommandTypeSet.add(SqlCommandType.DELETE);
    }
}
