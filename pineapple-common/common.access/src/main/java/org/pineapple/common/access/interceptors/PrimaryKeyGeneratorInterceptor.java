package org.pineapple.common.access.interceptors;

import cn.hutool.core.util.ReflectUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.pineapple.common.access.annotations.PrimaryKey;
import org.pineapple.common.base.Model;
import org.pineapple.common.base.PrimaryKeyModel;
import org.pineapple.common.base.utils.Snowflake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Properties;

/**
 * <p>主键生成拦截器</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/18
 */
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class PrimaryKeyGeneratorInterceptor implements Interceptor {
    private static final Logger log = LoggerFactory.getLogger(PrimaryKeyGeneratorInterceptor.class);
    private static final String DOT = ".";

    private static final String PARAM_PREFIX = "param";
    private final Snowflake snowflake;

    public PrimaryKeyGeneratorInterceptor(Snowflake snowflake) {
        this.snowflake = snowflake;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Executor executor = (Executor) invocation.getTarget();
        // 获取 Executor#update() 方法的两个参数
        Object[] args = invocation.getArgs();
        // 获取 Executor#update() 第一个参数
        MappedStatement ms = (MappedStatement) args[0];
        // 获取 Executor#update() 第二个参数
        Object parameter = args[1];
        if (ms.getSqlCommandType() != SqlCommandType.INSERT) {
            return invocation.proceed();
        }
        // 执行主键生成问题
        return executePrimaryKeyGenerate(executor, ms, parameter);
    }

    /**
     * <p>执行主键生成</p>
     *
     * @param executor  Mybatis执行器
     * @param ms        MappedStatement
     * @param parameter sql执行入参
     * @return java.lang.Object
     * @author pinea
     * @date 2023/4/18 18:41
     */
    private Object executePrimaryKeyGenerate(final Executor executor, final MappedStatement ms, final Object parameter) throws Throwable {
        // 获取被代理的方法
        Method executeMapperMethod = findExecuteMapperMethod(ms);
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
            // 根据获得的参数真实值处理参数对象以生成主键
            processParameterValToGeneratePrimaryKey(val);
            // 记录参数顺序自增
            index++;
        }
        // 执行sql
        return executor.update(ms, parameter);
    }

    /**
     * <p>根据获得的参数真实值处理参数对象以生成主键</p>
     *
     * @param parameterVal 参数真实值
     * @author pinea
     * @date 2023/4/18 20:45
     */
    private void processParameterValToGeneratePrimaryKey(Object parameterVal) {
        if (parameterVal instanceof Collection) {
            // 如果是集合类型则循环遍历注入id
            Collection<?> coll = (Collection<?>) parameterVal;
            coll.forEach(this::generateSnowflakeToPrimaryKeyInObject);
        } else if (parameterVal instanceof PrimaryKeyModel) {
            // 如果是主键模型类型直接获取id
            @SuppressWarnings("unchecked")
            PrimaryKeyModel<Long> model = (PrimaryKeyModel<Long>) parameterVal;
            model.setId(snowflake.nextId());
        } else if (parameterVal instanceof Model) {
            // 通过反射获取类中的主键字段
            generateSnowflakeToPrimaryKeyInObject(parameterVal);
        }
    }

    /**
     * <p>为{@code object}中的主键字段生成雪花id</p>
     *
     * @param obj obj
     * @author pinea
     * @date 2023/4/18 20:39
     */
    private void generateSnowflakeToPrimaryKeyInObject(Object obj) {
        Field[] fields = ReflectUtil.getFields(obj.getClass(), field -> field.isAnnotationPresent(PrimaryKey.class));
        for (Field field : fields) {
            ReflectUtil.setFieldValue(obj, field, snowflake.nextId());
            field.setAccessible(false);
        }
    }

    /**
     * <p>根据{@link MappedStatement}获取被代理Mapper方法</p>
     *
     * @param ms MappedStatement
     * @return java.lang.reflect.Method
     * @author pinea
     * @date 2023/4/18 18:58
     */
    private Method findExecuteMapperMethod(final MappedStatement ms) throws ClassNotFoundException {
        String mappedStatementId = ms.getId();
        int index = mappedStatementId.lastIndexOf(DOT);
        String mapperClassName = mappedStatementId.substring(0, index);
        String methodName = mappedStatementId.substring(index + 1);
        Class<?> mapperClass = Class.forName(mapperClassName);
        return ReflectUtil.getMethodByName(mapperClass, methodName);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {}
}
