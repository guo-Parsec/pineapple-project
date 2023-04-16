package org.pineapple.common.base.lang;

import java.io.Serializable;

/**
 * <p>值类型枚举</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/15
 */
public interface CodeEnumeratedValues<C extends Serializable> extends EnumeratedValues {
    /**
     * <p>获取当前枚举所代表的值</p>
     *
     * @return C 枚举所代表的值
     * @author pinea
     * @date 2023/4/15 21:00:55
     */
    C getCode();
}
