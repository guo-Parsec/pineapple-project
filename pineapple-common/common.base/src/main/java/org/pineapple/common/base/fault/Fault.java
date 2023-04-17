package org.pineapple.common.base.fault;

import cn.hutool.core.util.StrUtil;
import org.pineapple.common.base.lang.Clearable;
import org.pineapple.common.base.lang.Recordable;
import org.slf4j.Logger;

import java.io.Serializable;

/**
 * <p>错误类</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/15
 */

public interface Fault extends Serializable, Recordable, Clearable {

    /**
     * <p>获取错误码</p>
     *
     * @return int
     * @author pinea
     * @date 2023/4/15 21:21:50
     */
    Integer getCode();

    /**
     * <p>获取错误信息</p>
     *
     * @return int
     * @author pinea
     * @date 2023/4/15 21:21:50
     */
    String getMessage();

    /**
     * <p>将{@link Throwable}转换为当前错误类型</p>
     *
     * @param throwable JDK错误
     * @return org.pineapple.common.base.fault.Fault
     * @author pinea
     * @date 2023/4/16 20:17:29
     */
    Fault convertThrowable(Throwable throwable);

    /**
     * <p>判断当前错误是否为空</p>
     *
     * @return boolean
     * @author pinea
     * @date 2023/4/16 20:01:10
     */
    default boolean isEmpty() {
        return this.getCode() == 0 || StrUtil.isBlank(this.getMessage());
    }

    /**
     * <p>记录当前信息</p>
     *
     * @param logger 日志对象
     * @param code   操作码
     * @param text   操作文本
     * @param args   参数列表
     * @author pinea
     * @date 2023/4/17 00:02:59
     */
    @Override
    default void record(Logger logger, int code, String text, Object... args) {
        logger.error(this.faultRecord(code, text, args));
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
    String faultRecord(int code, String text, Object... args);
}
