package org.pineapple.common.access.interceptors;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.pineapple.common.base.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Properties;

/**
 * <p>id拦截器</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/17
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
@Component
public class IdInterceptor implements Interceptor {
    private Snowflake snowflake;

    @Autowired
    public void setSnowflake(Snowflake snowflake) {
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
        // 获取 statementHandler 对象
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        // 获取 mappedStatement 对象和 sqlCommandType （SQL 语句类型）
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        // 判断是否为插入操作
        if (SqlCommandType.INSERT.equals(sqlCommandType)) {
            // 获取方法参数并设置 ID
            Object parameterObject = metaStatementHandler.getValue("delegate.boundSql.parameterObject");
            MetaObject metaParameterObject = SystemMetaObject.forObject(parameterObject);
            metaParameterObject.setValue("id", snowflake.nextId());
        }
        // 继续执行原有逻辑
        return invocation.proceed();
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
    public void setProperties(Properties properties) {}
}
