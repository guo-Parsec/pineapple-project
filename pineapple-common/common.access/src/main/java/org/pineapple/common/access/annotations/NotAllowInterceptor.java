package org.pineapple.common.access.annotations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * <p></p>
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
    @AliasFor("interceptors")
    String[] value() default {};
    @AliasFor("value")
    String[] interceptors() default {};
}
