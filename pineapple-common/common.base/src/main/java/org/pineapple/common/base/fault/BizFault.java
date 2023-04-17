package org.pineapple.common.base.fault;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import org.pineapple.common.base.response.BizResponseStatus;
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
    protected Integer code;
    @Getter
    protected String message;

    public BizFault() {
        super();
    }

    public BizFault(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BizFault(ResponseStatus responseStatus) {
        this(responseStatus.getCode(), responseStatus.getMessage());
    }

    public BizFault(Throwable throwable) {
        this(BizResponseStatus.UNKNOWN_ERROR.getCode(), throwable.getMessage());
    }

    /**
     * <p>将{@link Throwable}转换为当前错误类型</p>
     *
     * @param throwable JDK错误
     * @return org.pineapple.common.base.fault.Fault
     * @author pinea
     * @date 2023/4/16 20:17:29
     */
    @Override
    public Fault convertThrowable(Throwable throwable) {
        return new BizFault(throwable);
    }

    /**
     * <p>错误记录</p>
     *
     * @param code 操作码
     * @param text 操作文本
     * @param args 参数列表
     * @return java.lang.String
     * @author pinea
     * @date 2023/4/17 00:35:54
     */
    @Override
    public String faultRecord(int code, String text, Object... args) {
        this.code = code;
        if (ArrayUtil.isEmpty(args)) {
            this.message = text;
            return this.message;
        }
        this.message = StrUtil.format(text, args);
        return this.message;
    }

    /**
     * <p>清除对象内容</p>
     *
     * @author pinea
     * @date 2023/4/17 16:48:18
     */
    @Override
    public void clear() {
        this.code = null;
        this.message = null;
    }
}
