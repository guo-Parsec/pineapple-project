package org.pineapple.system.core.test;

import lombok.Getter;
import lombok.Setter;
import org.pineapple.common.access.ExtPersistableModel;
import org.pineapple.common.access.annotations.AutoFillProvider;

import java.util.StringJoiner;

/**
 * <p>系统字典</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/17
 */
@Setter
@Getter
@AutoFillProvider(provider = SysDictProvider.class)
public class SysDict extends ExtPersistableModel {
    private static final long serialVersionUID = -5496104259136192597L;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典编码
     */
    private Integer dictCode;

    /**
     * 字典内容
     */
    private String dictText;

    /**
     * 字典排序
     */
    private Integer dictSort;

    @Override
    public String toString() {
        return new StringJoiner(", ", SysDict.class.getSimpleName() + "[", "]")
                .add("dictType='" + dictType + "'")
                .add("dictCode=" + dictCode)
                .add("dictText='" + dictText + "'")
                .add("dictSort=" + dictSort)
                .add("createdBy='" + createdBy + "'")
                .add("createdTime=" + createdTime)
                .add("updatedBy='" + updatedBy + "'")
                .add("updatedTime=" + updatedTime)
                .add("id=" + id)
                .toString();
    }
}
