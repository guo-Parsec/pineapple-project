package org.pineapple.common.base.fault;

/**
 * <p>错误类注册表接口</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/17
 */
public interface FaultRegistry<F extends Fault> {
    /**
     * <p>向注册表中推送错误类对象</p>
     *
     * @param faultClass 错误类对象
     * @param fault      错误对象
     * @author pinea
     * @date 2023/4/17 16:22:32
     */
    void push(Class<? extends F> faultClass, F fault);

    /**
     * <p>获取错误类</p>
     *
     * @param faultClass 错误类对象
     * @return F 错误对象
     * @author pinea
     * @date 2023/4/17 17:14:38
     */
    F get(Class<? extends F> faultClass);

    /**
     * <p>向注册表中弹出错误类对象</p>
     *
     * @param faultClass 错误类对象
     * @return boolean
     * @author pinea
     * @date 2023/4/17 16:23:27
     */
    void pop(Class<? extends F> faultClass);

    /**
     * <p>判断注册表中是否拥有错误类对象</p>
     *
     * @param faultClass 错误类对象
     * @return boolean
     * @author pinea
     * @date 2023/4/17 16:23:49
     */
    boolean contains(Class<? extends F> faultClass);

    /**
     * <p>判断注册表是否为空</p>
     *
     * @return boolean
     * @author pinea
     * @date 2023/4/17 16:24:16
     */
    boolean isEmpty();

    /**
     * <p>获取注册表长度</p>
     *
     * @return boolean
     * @author pinea
     * @date 2023/4/17 16:24:16
     */
    int size();

    /**
     * <p>重新加载注册表中错误类对象的注册信息</p>
     *
     * @param faultClass 错误类对象
     * @return boolean
     * @author pinea
     * @date 2023/4/17 16:44:35
     */
    boolean reload(Class<? extends F> faultClass);

    /**
     * <p>重新加载注册表中所有的注册信息</p>
     *
     * @author pinea
     * @date 2023/4/17 16:44:35
     */
    void reload();

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
