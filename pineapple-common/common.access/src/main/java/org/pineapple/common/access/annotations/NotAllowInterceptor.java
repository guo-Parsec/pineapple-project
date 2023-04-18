package org.pineapple.common.access.annotations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * <p>用于Mapper方法上表明当前方法不需要执行的拦截器</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/18
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface NotAllowInterceptor {
    /**
     * <p>表明当前方法不需要执行的拦截器名称</p>
     *
     * @return java.lang.String[]
     * @author pinea
     * @date 2023/4/18 22:56
     */
    @AliasFor("interceptors")
    String[] value() default {};

    /**
     * <p>表明当前方法不需要执行的拦截器名称</p>
     *
     * @return java.lang.String[]
     * @author pinea
     * @date 2023/4/18 22:56
     */
    @AliasFor("value")
    String[] interceptors() default {};
}
