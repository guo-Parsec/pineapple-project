package org.pineapple.common.base.response;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.pineapple.common.base.fault.BizFault;
import org.pineapple.common.base.fault.Fault;

/**
 * <p>{@link ActionResponse}类的辅助类以快速创建{@link ActionResponse}</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/16
 * @see ActionResponse
 */
public class ActionHelper {
    /**
     * <p>统一操作响应 - 成功 - 不携带返回数据</p>
     *
     * @return org.pineapple.common.base.response.ActionResponse<D>
     * @author pinea
     * @date 2023/4/16 19:57:54
     */
    public static <D> ActionResponse<D> success() {
        return success(null);
    }

    /**
     * <p>统一操作响应 - 成功 - 携带返回数据</p>
     *
     * @param data data
     * @return org.pineapple.common.base.response.ActionResponse<D>
     * @author pinea
     * @date 2023/4/16 19:56:44
     */
    public static <D> ActionResponse<D> success(D data) {
        return new ActionResponse<>(BizResponseStatus.SUCCESS, data);
    }

    /**
     * <p>统一操作响应 - 失败 - 接收错误异常</p>
     *
     * @param fault 错误异常
     * @return org.pineapple.common.base.response.ActionResponse<D>
     * @author pinea
     * @date 2023/4/16 20:02:49
     */
    public static <D> ActionResponse<D> failed(Fault fault) {
        if (fault == null || fault.isEmpty()) {
            fault = new BizFault(BizResponseStatus.UNKNOWN_ERROR);
        }
        return new ActionResponse<>(fault.getCode(), fault.getMessage());
    }

    /**
     * <p>统一操作响应 - 失败 - 接收响应状态</p>
     *
     * @param responseStatus 响应状态
     * @return org.pineapple.common.base.response.ActionResponse<D>
     * @author pinea
     * @date 2023/4/16 20:04:10
     */
    public static <D> ActionResponse<D> failed(ResponseStatus responseStatus) {
        if (responseStatus == null || responseStatus.equals(BizResponseStatus.SUCCESS)) {
            responseStatus = BizResponseStatus.UNKNOWN_ERROR;
        }
        return new ActionResponse<>(responseStatus);
    }

    /**
     * <p>统一操作响应 - 失败 - 接收操作信息</p>
     *
     * @param message 操作信息
     * @return org.pineapple.common.base.response.ActionResponse<D>
     * @author pinea
     * @date 2023/4/16 20:09:58
     */
    public static <D> ActionResponse<D> failed(String message) {
        return failed(BizResponseStatus.UNKNOWN_ERROR.getCode(), message);
    }

    /**
     * <p>统一操作响应 - 失败 - 接收操作码和操作信息</p>
     *
     * @param code    操作码
     * @param message 操作信息
     * @return org.pineapple.common.base.response.ActionResponse<D>
     * @author pinea
     * @date 2023/4/16 20:09:00
     */
    public static <D> ActionResponse<D> failed(Integer code, String message) {
        if (ObjectUtil.equals(code, BizResponseStatus.SUCCESS)) {
            return new ActionResponse<>(BizResponseStatus.UNKNOWN_ERROR);
        }
        if (StrUtil.isBlank(message)) {
            return new ActionResponse<>(code, BizResponseStatus.UNKNOWN_ERROR.getMessage());
        }
        return new ActionResponse<>(code, message);
    }

}
