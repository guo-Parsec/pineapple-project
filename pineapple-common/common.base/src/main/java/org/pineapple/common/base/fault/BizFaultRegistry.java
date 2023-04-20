package org.pineapple.common.base.fault;

import lombok.Getter;

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
    @Getter
    protected Map<Class<? extends BizFault>, BizFault> registryTable;

    private volatile static BizFaultRegistry instance;

    private BizFaultRegistry() {
        registryTable = new ConcurrentHashMap<>();
        this.push(BizFault.class, new BizFault());
        this.push(UnsupportedOperationFault.class, new UnsupportedOperationFault());
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
        if (registryTable.containsKey(faultClass)) {
            return;
        }
        registryTable.put(faultClass, fault);
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
        return registryTable.get(faultClass);
    }

    /**
     * <p>向注册表中弹出错误类对象</p>
     *
     * @param faultClass 错误类对象
     * @author pinea
     * @date 2023/4/17 16:23:27
     */
    @Override
    public void remove(Class<? extends BizFault> faultClass) {
        registryTable.remove(faultClass);
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
        return registryTable.containsKey(faultClass);
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
        return registryTable == null || registryTable.isEmpty();
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
        return registryTable == null ? 0 : registryTable.size();
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
        if (!registryTable.containsKey(faultClass)) {
            return false;
        }
        BizFault fault = registryTable.get(faultClass);
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
        registryTable.forEach((faultClass, fault) -> {
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
        registryTable.entrySet().removeIf(entry -> entry.getKey() == null || entry.getValue() == null);
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
