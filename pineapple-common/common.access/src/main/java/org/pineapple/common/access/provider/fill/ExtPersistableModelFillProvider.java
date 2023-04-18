package org.pineapple.common.access.provider.fill;

import org.pineapple.common.access.ExtPersistableModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * <p>针对{@link ExtPersistableModel}的字段填充</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/18
 */
@Component
public class ExtPersistableModelFillProvider extends SimpleFieldFillProvider<ExtPersistableModel> {
    /**
     * <p>执行新增时的字段填充</p>
     *
     * @param object object
     * @author pinea
     * @date 2023/4/18 21:04
     */
    @Override
    public void doInsertFill(ExtPersistableModel object) {
        LocalDateTime now = LocalDateTime.now();
        object.setCreatedBy("system");
        object.setUpdatedBy("system");
        object.setCreatedTime(now);
        object.setUpdatedTime(now);
    }

    /**
     * <p>执行更新时的字段填充</p>
     *
     * @param object object
     * @author pinea
     * @date 2023/4/18 21:04
     */
    @Override
    public void doUpdateFill(ExtPersistableModel object) {
        LocalDateTime now = LocalDateTime.now();
        object.setUpdatedBy("system");
        object.setUpdatedTime(now);
    }

    /**
     * <p>执行删除时(特指逻辑删除)的字段填充</p>
     *
     * @param object object
     * @author pinea
     * @date 2023/4/18 21:04
     */
    @Override
    public void doDeleteFill(ExtPersistableModel object) {
        LocalDateTime now = LocalDateTime.now();
        object.setUpdatedBy("system");
        object.setUpdatedTime(now);
    }
}
