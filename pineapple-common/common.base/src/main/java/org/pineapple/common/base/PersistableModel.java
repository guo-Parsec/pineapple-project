package org.pineapple.common.base;

import lombok.Getter;
import lombok.Setter;
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
    protected Long id;

    public PersistableModel() {
        super();
    }

    public PersistableModel(Long id) {
        super();
        this.id = id;
    }

    public PersistableModel(String id) {
        super();
        try {
            this.id = Long.valueOf(id);
        } catch (NumberFormatException e) {
            this.id = null;
            String name = this.getClass().getName();
            log.warn("when class [{}] is instantiated, the conversion fails " +
                    "when converting the string type to the primary key attribute [id] of type Long", name);
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PersistableModel.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .toString();
    }
}
