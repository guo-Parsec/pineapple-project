package org.pineapple.common.base.lang;

import java.io.Serializable;

/**
 * <p>错误类</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/15
 */

public interface Fault extends Serializable {

    /**
     * <p>获取错误码</p>
     *
     * @return int
     * @author pinea
     * @date 2023/4/15 21:21:50
     */
    int getCode();

    /**
     * <p>获取错误信息</p>
     *
     * @return int
     * @author pinea
     * @date 2023/4/15 21:21:50
     */
    String getMessage();
}
