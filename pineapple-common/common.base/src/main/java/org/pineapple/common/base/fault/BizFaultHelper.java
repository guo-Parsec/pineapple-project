package org.pineapple.common.base.fault;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.ReflectUtil;
import org.pineapple.common.base.response.BizResponseStatus;
import org.pineapple.common.base.response.ResponseStatus;
import org.slf4j.Logger;

/**
 * <p>{@link BizFault}类的辅助类以快速创建{@link BizFault}</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/16
 */
public class BizFaultHelper {
    /**
     * <p>记录错误</p>
     *
     * @param log  日志对象
     * @param text 操作文本
     * @param args 参数列表
     * @return org.pineapple.common.base.fault.BizFault
     * @author pinea
     * @date 2023/4/17 17:20:44
     */
    public static <F extends BizFault> BizFault record(Logger log, String text, Object... args) {
        return record(BizFault.class, log, BizResponseStatus.UNKNOWN_ERROR, text, args);
    }

    /**
     * <p>记录错误</p>
     *
     * @param faultClass 错误类对象
     * @param log        日志对象
     * @param text       操作文本
     * @param args       参数列表
     * @return org.pineapple.common.base.fault.BizFault
     * @author pinea
     * @date 2023/4/17 00:42:15
     */
    public static <F extends BizFault> BizFault record(Class<F> faultClass, Logger log, String text, Object... args) {
        return record(faultClass, log, BizResponseStatus.UNKNOWN_ERROR, text, args);
    }

    /**
     * <p>记录错误</p>
     *
     * @param faultClass     错误类对象
     * @param log            日志对象
     * @param responseStatus 响应状态
     * @param text           操作文本
     * @param args           参数列表
     * @return org.pineapple.common.base.fault.BizFault
     * @author pinea
     * @date 2023/4/17 00:36:32
     */
    public static <F extends BizFault> BizFault record(Class<F> faultClass, Logger log, ResponseStatus responseStatus, String text, Object... args) {
        return record(faultClass, log, responseStatus.getCode(), text, args);
    }

    /**
     * <p>记录错误</p>
     *
     * @param faultClass 错误类对象
     * @param log        日志对象
     * @param code       操作码
     * @param text       操作文本
     * @param args       参数列表
     * @return org.pineapple.common.base.fault.BizFault
     * @author pinea
     * @date 2023/4/17 00:39:53
     */
    @SuppressWarnings("unchecked")
    public static <F extends BizFault> F record(Class<F> faultClass, Logger log, int code, String text, Object... args) {
        BizFaultRegistry registry = BizFaultRegistry.getInstance();
        if (!registry.contains(faultClass)) {
            try {
                F fault = ReflectUtil.newInstance(faultClass);
                fault.clear();
                registry.push(faultClass, fault);
            } catch (UtilException e) {
                BizFault defaultFault = registry.getDefaultFault();
                defaultFault.clear();
                defaultFault.record(log, code, text, args);
                return (F) defaultFault;
            }
        }
        registry.reload(faultClass);
        F fault = (F) registry.get(faultClass);
        fault.record(log, code, text, args);
        return fault;
    }
}
