package org.pineapple.common.access.interfaces;

import java.io.Serializable;

/**
 * 主键生成器
 *
 * @author gcq
 * @date 2023/5/19 11:19
 */
public interface IdGenerator<K extends Serializable> {
    /**
     * 获取下一个id
     *
     * @return K 下一个id
     * @author gcq
     * @date 2023/5/19 11:28
     */
    K nextId();
}
