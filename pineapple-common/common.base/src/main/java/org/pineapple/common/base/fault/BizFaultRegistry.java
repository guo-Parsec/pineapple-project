package org.pineapple.common.base.fault;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>业务错误类注册表</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/17
 */
public class BizFaultRegistry implements FaultRegistry<BizFault> {
    protected Map<Class<? extends BizFault>, BizFault> faultStack;

    private volatile static BizFaultRegistry instance;

    private BizFaultRegistry() {
        faultStack = new ConcurrentHashMap<>();
        this.push(BizFault.class, new BizFault());
    }

    public static BizFaultRegistry getInstance() {
        //第一次检查，如果对象存在就不需要同步操作了
        if (instance == null) {
            //加锁
            synchronized (BizFaultRegistry.class) {
                //第二次检查，防止多线程环境下重复创建对象
                if (instance == null) {
                    instance = new BizFaultRegistry();
                }
            }
        }
        return instance;
    }

    /**
     * <p>向注册表中推送错误类对象</p>
     *
     * @param faultClass 错误类对象
     * @param fault      错误对象
     * @author pinea
     * @date 2023/4/17 16:22:32
     */
    @Override
    public void push(Class<? extends BizFault> faultClass, BizFault fault) {
        if (faultStack.containsKey(faultClass)) {
            return;
        }
        faultStack.put(faultClass, fault);
    }

    /**
     * <p>获取错误类</p>
     *
     * @param faultClass 错误类对象
     * @return F 错误对象
     * @author pinea
     * @date 2023/4/17 17:14:38
     */
    @Override
    public BizFault get(Class<? extends BizFault> faultClass) {
        return faultStack.get(faultClass);
    }

    /**
     * <p>向注册表中弹出错误类对象</p>
     *
     * @param faultClass 错误类对象
     * @author pinea
     * @date 2023/4/17 16:23:27
     */
    @Override
    public void pop(Class<? extends BizFault> faultClass) {
        faultStack.remove(faultClass);
    }

    /**
     * <p>判断注册表中是否拥有错误类对象</p>
     *
     * @param faultClass 错误类对象
     * @return boolean
     * @author pinea
     * @date 2023/4/17 16:23:49
     */
    @Override
    public boolean contains(Class<? extends BizFault> faultClass) {
        return faultStack.containsKey(faultClass);
    }

    /**
     * <p>判断注册表是否为空</p>
     *
     * @return boolean
     * @author pinea
     * @date 2023/4/17 16:24:16
     */
    @Override
    public boolean isEmpty() {
        return faultStack == null || faultStack.isEmpty();
    }

    /**
     * <p>获取注册表长度</p>
     *
     * @return boolean
     * @author pinea
     * @date 2023/4/17 16:24:16
     */
    @Override
    public int size() {
        return faultStack == null ? 0 : faultStack.size();
    }

    /**
     * <p>重新加载注册表中错误类对象的注册信息</p>
     *
     * @param faultClass 错误类对象
     * @return boolean
     * @author pinea
     * @date 2023/4/17 16:44:35
     */
    @Override
    public boolean reload(Class<? extends BizFault> faultClass) {
        if (!faultStack.containsKey(faultClass)) {
            return false;
        }
        BizFault fault = faultStack.get(faultClass);
        if (fault == null) {
            return false;
        }
        fault.clear();
        return true;
    }

    /**
     * <p>重新加载注册表中所有的注册信息</p>
     *
     * @author pinea
     * @date 2023/4/17 16:44:35
     */
    @Override
    public void reload() {
        faultStack.forEach((faultClass, fault) -> {
            if (faultClass == null || fault == null) {
                return;
            }
            fault.clear();
        });
    }

    /**
     * <p>清空注册表中所有的空错误,即注册表中错误类对象为null或错误为null的注册信息</p>
     *
     * @author pinea
     * @date 2023/4/17 16:41:09
     */
    @Override
    public void clearEmptyFault() {
        faultStack.entrySet().removeIf(entry -> entry.getKey() == null || entry.getValue() == null);
    }

    /**
     * <p>获取默认错误类</p>
     *
     * @return F 默认错误类
     * @author pinea
     * @date 2023/4/17 17:31:33
     */
    @Override
    public BizFault getDefaultFault() {
        return new BizFault();
    }
}
