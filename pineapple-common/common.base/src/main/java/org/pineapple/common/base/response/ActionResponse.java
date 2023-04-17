package org.pineapple.common.base.response;

import lombok.Getter;
import lombok.Setter;
import org.pineapple.common.base.Model;

/**
 * <p>动作响应</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/15
 */
@Setter
@Getter
public class ActionResponse<D> implements Model {
    private static final long serialVersionUID = 1L;

    /**
     * 操作状态码
     */
    private int code;

    /**
     * 操作状态码
     */
    private String message;

    /**
     * 操作状态码
     */
    private D data;

    public ActionResponse(ResponseStatus responseStatus) {
        this(responseStatus, null);
    }

    public ActionResponse(ResponseStatus responseStatus, D data) {
        this(responseStatus.getCode(), responseStatus.getMessage(), data);
    }

    public ActionResponse(int code, String message) {
        this(code, message, null);
    }

    public ActionResponse(int code, String message, D data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
