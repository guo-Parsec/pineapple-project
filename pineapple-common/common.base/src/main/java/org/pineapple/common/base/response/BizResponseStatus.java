package org.pineapple.common.base.response;

import lombok.Getter;

/**
 * <p>业务响应状态</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/16
 */
@Getter
public enum BizResponseStatus implements ResponseStatus {
    SUCCESS(0, "操作成功"),

    UNKNOWN_ERROR(1, "系统错误"),

    UNSUPPORTED_OPERATION_ERROR(2, "支持的操作"),

    ;

    private final Integer code;

    private final String message;

    BizResponseStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
