package org.pineapple.common.access.provider.fill;

import org.pineapple.common.access.types.FieldFillType;

/**
 * <p>字段填充bean</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/18
 */
public interface FieldFillProvider {
    /**
     * <p>执行填充</p>
     *
     * @param object        对象
     * @param fieldFillType 字段填充类型
     * @author pinea
     * @date 2023/4/18 20:57
     */
    void doFill(Object object, FieldFillType fieldFillType);
}
