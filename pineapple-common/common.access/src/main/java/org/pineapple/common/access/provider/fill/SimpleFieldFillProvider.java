package org.pineapple.common.access.provider.fill;

import org.pineapple.common.access.types.FieldFillType;
import org.pineapple.common.base.Model;

/**
 * <p>对{@link  FieldFillProvider}简单实现</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/18
 */
public abstract class SimpleFieldFillProvider<M extends Model> implements FieldFillProvider {
    /**
     * <p>执行新增时的字段填充</p>
     *
     * @param object object
     * @author pinea
     * @date 2023/4/18 21:04
     */
    public abstract void doInsertFill(M object);

    /**
     * <p>执行更新时的字段填充</p>
     *
     * @param object object
     * @author pinea
     * @date 2023/4/18 21:04
     */
    public abstract void doUpdateFill(M object);

    /**
     * <p>执行删除时(特指逻辑删除)的字段填充</p>
     *
     * @param object object
     * @author pinea
     * @date 2023/4/18 21:04
     */
    public abstract void doDeleteFill(M object);

    /**
     * <p>执行填充</p>
     *
     * @param object        对象
     * @param fieldFillType 字段填充类型
     * @author pinea
     * @date 2023/4/18 20:57
     */
    @Override
    @SuppressWarnings("unchecked")
    public void doFill(Object object, FieldFillType fieldFillType) {
        M modelObject = null;
        if (object instanceof Model) {
            modelObject = (M) object;
        }
        if (modelObject == null) {
            return;
        }
        switch (fieldFillType) {
            case INSERT:
                this.doInsertFill(modelObject);
                return;
            case UPDATE:
                this.doUpdateFill(modelObject);
                return;
            case DELETE:
                this.doDeleteFill(modelObject);
        }
    }
}
