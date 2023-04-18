package org.pineapple.common.access.annotations;

import org.pineapple.common.access.provider.fill.FieldFillProvider;

import java.lang.annotation.*;

/**
 * <p>字段自动注入注解</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/18
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AutoFill {
    Class<? extends FieldFillProvider> fillBeanClass();
}
