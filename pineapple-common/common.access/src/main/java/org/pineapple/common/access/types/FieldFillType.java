package org.pineapple.common.access.types;

import lombok.Getter;
import org.apache.ibatis.mapping.SqlCommandType;
import org.pineapple.common.base.lang.CodeEnumeratedValues;

/**
 * <p>字段填充类型</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/18
 */
public enum FieldFillType implements CodeEnumeratedValues<SqlCommandType> {
    INSERT(SqlCommandType.INSERT), UPDATE(SqlCommandType.UPDATE), DELETE(SqlCommandType.DELETE);

    @Getter
    private final SqlCommandType code;

    FieldFillType(SqlCommandType code) {
        this.code = code;
    }


    public static FieldFillType findByCode(SqlCommandType sqlCommandType) {
        for (FieldFillType fieldFillType : values()) {
            if (fieldFillType.getCode() == sqlCommandType) {
                return fieldFillType;
            }
        }
        return null;
    }
}
