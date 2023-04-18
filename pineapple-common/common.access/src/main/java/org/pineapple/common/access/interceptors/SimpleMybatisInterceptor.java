package org.pineapple.common.access.interceptors;

import lombok.Getter;
import org.springframework.beans.factory.BeanNameAware;

/**
 * <p>简单的mybatis拦截器实现</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/18
 */
public abstract class SimpleMybatisInterceptor implements MybatisInterceptor, BeanNameAware {
    protected static final String PARAM_PREFIX = "param";
    @Getter
    protected String name;

    @Override
    @SuppressWarnings("all")
    public void setBeanName(String beanName) {
        this.name = beanName;
    }
}
