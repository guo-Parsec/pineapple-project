package org.pineapple.common.base.lang;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * <p>字段继承关系映射</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/18
 */
@Setter
@Getter
public class FieldsExtendsMapping implements Serializable, Comparable<FieldsExtendsMapping> {
    private static final long serialVersionUID = 613852361071925951L;

    /**
     * 上层层级索引：以当前类所在索引为0,其父类依次递减
     */
    private Integer index;

    /**
     * 当前层级类对象
     */
    private Class<?> clazz;

    /**
     * 字段列表
     */
    private List<Field> fields;

    public FieldsExtendsMapping() {
    }

    public FieldsExtendsMapping(Integer index, Class<?> clazz, List<Field> fields) {
        this.index = index;
        this.clazz = clazz;
        this.fields = fields;
    }

    @Override
    public int compareTo(FieldsExtendsMapping o) {
        return o.getIndex().compareTo(this.index);
    }

    @Override
    public String toString() {
        return "FieldsExtendsMapping{" +
                "index=" + index +
                ", clazz=" + clazz +
                ", fields=" + fields +
                '}';
    }
}
