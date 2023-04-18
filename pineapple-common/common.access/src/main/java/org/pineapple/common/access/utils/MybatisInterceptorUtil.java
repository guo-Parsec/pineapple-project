package org.pineapple.common.access.utils;

import cn.hutool.core.util.ReflectUtil;
import org.apache.ibatis.mapping.MappedStatement;

import java.lang.reflect.Method;

/**
 * <p>mybatis拦截器工具类</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/18
 */
public class MybatisInterceptorUtil {
    private static final String DOT = ".";
    /**
     * <p>根据{@link MappedStatement}获取被代理Mapper方法</p>
     *
     * @param ms MappedStatement
     * @return java.lang.reflect.Method
     * @author pinea
     * @date 2023/4/18 18:58
     */
    public static Method findExecuteMapperMethod(final MappedStatement ms) throws ClassNotFoundException {
        String mappedStatementId = ms.getId();
        int index = mappedStatementId.lastIndexOf(DOT);
        String mapperClassName = mappedStatementId.substring(0, index);
        String methodName = mappedStatementId.substring(index + 1);
        Class<?> mapperClass = Class.forName(mapperClassName);
        return ReflectUtil.getMethodByName(mapperClass, methodName);
    }

}
