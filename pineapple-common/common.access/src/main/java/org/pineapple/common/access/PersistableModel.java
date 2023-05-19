package org.pineapple.common.access;

import lombok.Getter;
import lombok.Setter;
import org.pineapple.common.access.annotations.PrimaryKey;
import org.pineapple.common.access.generator.SnowflakeGenerator;
import org.pineapple.common.base.CloneableModel;
import org.pineapple.common.base.PrimaryKeyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.StringJoiner;

/**
 * <p>可持久化的模型</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/15
 */
@Setter
@Getter
public abstract class PersistableModel implements PrimaryKeyModel<Long>, CloneableModel {
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(PersistableModel.class);

    /**
     * 类型为K的主键
     */
    @PrimaryKey(generator = SnowflakeGenerator.class)
    protected Long id;

    public PersistableModel() {
        super();
    }

    public PersistableModel(Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PersistableModel.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .toString();
    }
}
