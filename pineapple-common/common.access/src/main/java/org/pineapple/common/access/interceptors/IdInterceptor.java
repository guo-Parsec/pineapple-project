package org.pineapple.common.access.interceptors;

import cn.hutool.core.util.ReflectUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.pineapple.common.access.InjectIdParam;
import org.pineapple.common.access.PersistableModel;
import org.pineapple.common.base.utils.Snowflake;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

/**
 * <p>id拦截器</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/17
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class IdInterceptor implements Interceptor {
    private final Snowflake snowflake;

    public IdInterceptor(Snowflake snowflake) {
        this.snowflake = snowflake;
    }

    /**
     * <p>拦截 MyBatis 执行的 SQL 语句，并在插入操作前生成雪花算法 ID，并设置到参数中。</p>
     *
     * @param invocation invocation
     * @return java.lang.Object
     * @author pinea
     * @date 2023/4/17 20:07:49
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        boolean isParamModified = parameter instanceof MapperMethod.ParamMap;
        if (ms.getSqlCommandType().equals(SqlCommandType.INSERT)) {
            String id = ms.getId();
            int lastIndex = id.lastIndexOf(".");
            String mapperClassName = id.substring(0, lastIndex);
            String methodName = id.substring(lastIndex + 1);
            Method method = ReflectUtil.getMethodByName(Class.forName(mapperClassName), methodName);
            MapperMethod.ParamMap<?> paramMap = null;
            if (isParamModified) {
                paramMap = (MapperMethod.ParamMap<?>) parameter;
            }
            Parameter[] parameters = method.getParameters();
            int index = 1;
            for (Parameter methodParam : parameters) {
                Param param = methodParam.getAnnotation(Param.class);
                String keyName = "param" + index;
                Object paramVal = null;
                if (param != null && isParamModified) {
                    paramVal = paramMap.containsKey(param.value()) ? paramMap.get(param.value()) : paramMap.get(keyName);
                }
                if (paramMap == null || param == null) {
                    paramVal = parameter;
                }
                InjectIdParam injectIdParam = methodParam.getAnnotation(InjectIdParam.class);
                if (injectIdParam == null) {
                    continue;
                }
                handlerIdInject(paramVal);
            }
        }
        return invocation.proceed();
    }

    @SuppressWarnings("unchecked")
    private void handlerIdInject(Object parameter) {
        if (parameter instanceof PersistableModel) {
            persistableModelIdGenerate(parameter);
        } else if (parameter instanceof Collection) {
            Collection<?> collection = (Collection<?>) parameter;
            collection.forEach(this::persistableModelIdGenerate);
        } else if (parameter instanceof Map) {
            ((Map<String, Object>) parameter).put("id", snowflake.nextId());
        }
    }

    private void persistableModelIdGenerate(Object object) {
        if (object instanceof PersistableModel) {
            PersistableModel model = (PersistableModel) object;
            model.setId(snowflake.nextId());
        }
    }


    /**
     * 返回被代理对象的动态代理对象。
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 设置插件属性。
     */
    @Override
    public void setProperties(Properties properties) {
    }
}
