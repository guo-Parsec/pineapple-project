package org.pineapple.common.base.lang;

import java.io.Serializable;

/**
 * <p>key-value类型枚举</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/15
 */
public interface KeyValueEnumeratedValues<K extends Serializable, V> {
    /**
     * <p>获取当前枚举所映射的key</p>
     *
     * @return K
     * @author pinea
     * @date 2023/4/15 21:06:06
     */
    K getKey();

    /**
     * <p>获取当前枚举所映射的value</p>
     *
     * @return V
     * @author pinea
     * @date 2023/4/15 21:05:19
     */
    V getValue();
}
