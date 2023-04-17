package org.pineapple.common.base.lang;

import java.util.Objects;

/**
 * <p>可清空对象</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/17
 */
public interface Clearable {
    /**
     * <p>清除对象内容</p>
     *
     * @author pinea
     * @date 2023/4/17 16:48:18
     */
    void clear();

    /**
     * <p>清楚指定对象</p>
     *
     * @param obj obj
     * @author pinea
     * @date 2023/4/17 16:49:37
     */
    static void clear(Object obj) {
        Objects.requireNonNull(obj);
        if (obj instanceof Clearable) {
            ((Clearable) obj).clear();
        }
    }
}
