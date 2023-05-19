package org.pineapple.common.access.annotations;

import org.pineapple.common.access.interfaces.IdGenerator;

import java.io.Serializable;
import java.lang.annotation.*;

/**
 * id生成器
 *
 * @author gcq
 * @date 2023/5/19 11:16
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface GeneratedStrategy {
    /**
     * 生成器
     *
     * @return Class<IdGenerator>
     * @author gcq
     * @date 2023/5/19 11:22
     */
    Class<? extends IdGenerator<? extends Serializable>> generator();
}
