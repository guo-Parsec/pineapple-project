package org.pineapple.common.base.fault;

import lombok.Getter;
import org.pineapple.common.base.lang.Fault;
import org.pineapple.common.base.response.ResponseStatus;

/**
 * <p>业务类错误信息</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/15
 */

public class BizFault extends RuntimeException implements Fault {
    private static final long serialVersionUID = 1L;
    @Getter
    protected int code;
    @Getter
    protected String message;

    public BizFault() {
        super();
    }

    public BizFault(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BizFault(ResponseStatus responseStatus) {
        this(responseStatus.getCode(), responseStatus.getMessage());
    }
}
