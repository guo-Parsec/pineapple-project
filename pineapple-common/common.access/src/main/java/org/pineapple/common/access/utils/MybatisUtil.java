package org.pineapple.common.access.utils;

import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.mapping.MappedStatement;
import org.pineapple.common.base.fault.BizFaultHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * <p>mybatis拦截器工具类</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/18
 */
public class MybatisUtil {
    private static final Logger log = LoggerFactory.getLogger(MybatisUtil.class);
    private static final char DOT = '.';

    /**
     * <p>根据{@link MappedStatement}获取被代理Mapper方法</p>
     *
     * @param ms MappedStatement
     * @return java.lang.reflect.Method
     * @author pinea
     * @date 2023/4/18 18:58
     */
    public static Method getProxyMethod(final MappedStatement ms) {
        return Optional.ofNullable(getProxyMethodPair(ms)).map(Pair::getValue)
                .orElseThrow(() -> BizFaultHelper.record(log, "Failed to get proxy class and proxy method"));
    }

    /**
     * 根据{@link MappedStatement}获取被代理Mapper方法
     *
     * @param ms MappedStatement
     * @return Pair<Class < ?>,Method>
     * @author gcq
     * @date 2023/5/19 9:11
     */
    private static Pair<Class<?>, Method> getProxyMethodPair(final MappedStatement ms) {
        if (ms == null) {
            return null;
        }
        String mappedStatementId = ms.getId();
        if (StrUtil.isBlank(mappedStatementId)) {
            return null;
        }
        int index = mappedStatementId.lastIndexOf(DOT);
        if (index == -1) {
            return null;
        }
        String mapperClassName = mappedStatementId.substring(0, index);
        String methodName = mappedStatementId.substring(index + 1);
        Class<?> proxyClass = null;
        Method proxyMethod = null;
        try {
            proxyClass = Class.forName(mapperClassName);
            proxyMethod = ReflectUtil.getMethodByName(proxyClass, methodName);
        } catch (Exception e) {
            log.warn("Failed to get proxy class and proxy method through reflection, reason: {}", e.getMessage());
            return null;
        }
        if (proxyMethod == null) {
            log.warn("Failed to get proxy class and proxy method, reason: cannot find proxy method");
            return null;
        }
        return Pair.of(proxyClass, proxyMethod);
    }

}
