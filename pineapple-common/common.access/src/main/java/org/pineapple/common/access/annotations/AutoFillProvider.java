package org.pineapple.common.access.annotations;

import org.pineapple.common.access.provider.fill.FieldFillProvider;

import java.lang.annotation.*;

/**
 * <p>通过指定{@code consumers}，使得指定消费者采用当前{@link FieldFillProvider}</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface AutoFillProvider {
    /**
     * <p>指定{@code autoFillProvider}的provider</p>
     *
     * @return java.lang.Class<?>
     * @author pinea
     * @date 2023/4/20 0:25
     */
    Class<? extends FieldFillProvider> provider();

    /**
     * <p>是否允许永久生效,即便被{@link #ineffectiveProviders}指定为不允许生效，也会强制生效,默认不开启</p>
     *
     * @return boolean
     * @author pinea
     * @date 2023/4/20 0:41
     */
    boolean alwaysEffective() default false;

    /**
     * <p>指定不允许生效的{@link FieldFillProvider}</p>
     *
     * @return java.lang.Class<? extends org.pineapple.common.access.provider.fill.FieldFillProvider>[]
     * @author pinea
     * @date 2023/4/20 0:42
     */
    Class<? extends FieldFillProvider>[] ineffectiveProviders() default {};
}
