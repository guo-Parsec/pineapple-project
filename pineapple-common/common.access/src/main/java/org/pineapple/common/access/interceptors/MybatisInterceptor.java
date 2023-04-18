package org.pineapple.common.access.interceptors;

import cn.hutool.core.util.ArrayUtil;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Plugin;
import org.pineapple.common.access.annotations.NotAllowInterceptor;
import org.pineapple.common.access.utils.MybatisInterceptorUtil;
import org.pineapple.common.base.lang.Nameable;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * <p>mybatis拦截器统一接口处理</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/18
 */
public interface MybatisInterceptor extends Interceptor, Nameable {
    @Override
    default Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    default void setProperties(Properties properties) {}

    /**
     * <p>是否不允许拦截</p>
     *
     * @param ms ms
     * @return boolean
     * @author pinea
     * @date 2023/4/18 21:50
     */
    default boolean isNotAllowIntercept(MappedStatement ms) throws Throwable {
        Method executeMapperMethod = MybatisInterceptorUtil.findExecuteMapperMethod(ms);
        NotAllowInterceptor notAllowInterceptor = AnnotationUtils.getAnnotation(executeMapperMethod, NotAllowInterceptor.class);
        if (notAllowInterceptor == null) {
            return false;
        }
        String[] interceptors = notAllowInterceptor.interceptors();
        return ArrayUtil.contains(interceptors, this.getName());
    }
}
