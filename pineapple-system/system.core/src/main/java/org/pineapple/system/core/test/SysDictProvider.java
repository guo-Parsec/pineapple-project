package org.pineapple.system.core.test;

import org.pineapple.common.access.provider.fill.SimpleFieldFillProvider;
import org.springframework.stereotype.Component;

/**
 * <p></p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/20
 */
@Component
public class SysDictProvider extends SimpleFieldFillProvider<SysDict> {
    /**
     * <p>执行新增时的字段填充</p>
     *
     * @param object object
     * @author pinea
     * @date 2023/4/18 21:04
     */
    @Override
    public void doInsertFill(SysDict object) {
        object.setDictSort(20);
    }

    /**
     * <p>执行更新时的字段填充</p>
     *
     * @param object object
     * @author pinea
     * @date 2023/4/18 21:04
     */
    @Override
    public void doUpdateFill(SysDict object) {

    }

    /**
     * <p>执行删除时(特指逻辑删除)的字段填充</p>
     *
     * @param object object
     * @author pinea
     * @date 2023/4/18 21:04
     */
    @Override
    public void doDeleteFill(SysDict object) {

    }
}
