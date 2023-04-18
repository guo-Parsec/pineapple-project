package org.pineapple.common.access;

import lombok.Getter;
import lombok.Setter;
import org.pineapple.common.access.annotations.AutoFill;
import org.pineapple.common.access.provider.fill.ExtPersistableModelFillProvider;

import java.time.LocalDateTime;

/**
 * <p>拓展后的可持久化的模型</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/17
 */
@Setter
@Getter
@AutoFill(providerBean = ExtPersistableModelFillProvider.class)
public class ExtPersistableModel extends PersistableModel {
    private static final long serialVersionUID = 3344166227393604493L;

    /**
     * 创建人
     */
    protected String createdBy;

    /**
     * 创建时间
     */
    protected LocalDateTime createdTime;

    /**
     * 更新人
     */
    protected String updatedBy;

    /**
     * 更新时间
     */
    protected LocalDateTime updatedTime;
}
