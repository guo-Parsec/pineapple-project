package org.pineapple.common.access.annotations;

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
}
