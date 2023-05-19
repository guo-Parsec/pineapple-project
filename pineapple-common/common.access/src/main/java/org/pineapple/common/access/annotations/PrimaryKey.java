package org.pineapple.common.access.annotations;

import org.pineapple.common.access.interfaces.IdGenerator;

import java.io.Serializable;
import java.lang.annotation.*;

/**
 * <p>主键字段标识注解</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/18
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PrimaryKey {
    /**
     * 生成器
     *
     * @return Class<IdGenerator>
     * @author gcq
     * @date 2023/5/19 11:22
     */
    Class<? extends IdGenerator<? extends Serializable>> generator();
}