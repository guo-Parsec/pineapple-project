package org.pineapple.common.base.lang;

import org.slf4j.Logger;

/**
 * <p>可被记录的接口</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/17
 */
public interface Recordable {
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
    void record(Logger logger, int code, String text, Object... args);
}
