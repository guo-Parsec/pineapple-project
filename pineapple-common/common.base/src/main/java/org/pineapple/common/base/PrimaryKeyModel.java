package org.pineapple.common.base;

import java.io.Serializable;

/**
 * <p>拥有主键的的模型</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/15
 */
public interface PrimaryKeyModel<K extends Serializable> extends Model {
    /**
     * <p>类型为{@code K}的主键属性的{@code getter}方法</p>
     *
     * @return K
     * @author pinea
     * @date 2023/4/15 20:20:03
     */
    K getId();

    /**
     * <p>类型为{@code K}的主键属性的{@code setter}方法</p>
     *
     * @param id 类型为{@code K}的主键
     * @author pinea
     * @date 2023/4/15 20:20:03
     */
    void setId(K id);
}
