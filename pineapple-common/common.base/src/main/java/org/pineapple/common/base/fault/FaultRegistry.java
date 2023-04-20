package org.pineapple.common.base.fault;

import org.pineapple.common.base.lang.ReloadableRegistry;

/**
 * <p>错误类注册表接口</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/17
 */
public interface FaultRegistry<F extends Fault> extends ReloadableRegistry<Class<? extends F>, F> {
    /**
     * <p>清空注册表中所有的空错误,即注册表中错误类对象为null或错误为null的注册信息</p>
     *
     * @author pinea
     * @date 2023/4/17 16:41:09
     */
    void clearEmptyFault();

    /**
     * <p>获取默认错误类</p>
     *
     * @return F 默认错误类
     * @author pinea
     * @date 2023/4/17 17:31:33
     */
    F getDefaultFault();
}
