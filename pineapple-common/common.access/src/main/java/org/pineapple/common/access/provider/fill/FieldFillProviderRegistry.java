package org.pineapple.common.access.provider.fill;

import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.pineapple.common.base.lang.Registry;
import org.pineapple.common.base.lang.SpringAutowiredRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * <p>字段填充提供者注册表</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/20
 */
public class FieldFillProviderRegistry implements Registry<Class<? extends FieldFillProvider>, FieldFillProvider> {
    private static final Logger log = LoggerFactory.getLogger(SpringAutowiredRegistry.class);

    @Getter
    protected Map<Class<? extends FieldFillProvider>, FieldFillProvider> registryTable;

    private volatile static FieldFillProviderRegistry instance;

    public FieldFillProviderRegistry() {
        registryTable = Maps.newConcurrentMap();
    }

    public static FieldFillProviderRegistry getInstance() {
        //第一次检查，如果对象存在就不需要同步操作了
        if (instance == null) {
            //加锁
            synchronized (FieldFillProviderRegistry.class) {
                //第二次检查，防止多线程环境下重复创建对象
                if (instance == null) {
                    instance = new FieldFillProviderRegistry();
                }
            }
        }
        return instance;
    }

    /**
     * <p>向注册表中推送新的注册信息</p>
     *
     * @param infoKey 注册信息的key
     * @author pinea
     * @date 2023/4/20 1:36
     */
    public void push(Class<? extends FieldFillProvider> infoKey) {
        this.push(infoKey, null);
    }

    /**
     * <p>向注册表中推送新的注册信息</p>
     *
     * @param infoKey 注册信息的key
     * @param infoVal 注册信息的value
     * @author pinea
     * @date 2023/4/20 0:52
     */
    @Override
    public void push(Class<? extends FieldFillProvider> infoKey, FieldFillProvider infoVal) {
        try {
            FieldFillProvider provider = SpringUtil.getBean(infoKey);
            registryTable.put(infoKey, provider);
        } catch (Exception e) {
            log.error("error :", e);
            log.warn("bean[{}] cannot be found in IOC container, the infoVal you input may not be managed by Spring.", infoKey.getName());
            registryTable.put(infoKey, infoVal);
        }
    }

    /**
     * <p>从注册表中获取key指定的注册信息</p>
     *
     * @param infoKey 注册信息的key
     * @author pinea
     * @date 2023/4/20 0:52
     */
    @Override
    public final FieldFillProvider get(Class<? extends FieldFillProvider> infoKey) {
        FieldFillProvider provider = registryTable.get(infoKey);
        if (provider == null) {
            return null;
        }
        try {
            FieldFillProvider bean = SpringUtil.getBean(infoKey);
            if (bean != provider) {
                log.warn("bean[{}] cannot be found in IOC container, the infoVal you input may not be managed by Spring.", infoKey.getName());
            }
            return bean;
        } catch (Exception e) {
            log.warn("bean[{}] cannot be found in IOC container, the infoVal you input may not be managed by Spring.", infoKey.getName());
            return registryTable.get(infoKey);
        }
    }

    /**
     * <p>从注册表中删除key指定的注册信息</p>
     *
     * @param infoKey 注册信息的key
     * @author pinea
     * @date 2023/4/20 0:53
     */
    @Override
    public void remove(Class<? extends FieldFillProvider> infoKey) {
        registryTable.remove(infoKey);
    }

    /**
     * <p>判断注册表中是否含有key指定的注册信息</p>
     *
     * @param infoKey
     * @return boolean
     * @author pinea
     * @date 2023/4/20 0:53
     */
    @Override
    public boolean contains(Class<? extends FieldFillProvider> infoKey) {
        return registryTable.containsKey(infoKey);
    }

    /**
     * <p>判断注册表是否为空</p>
     *
     * @return boolean
     * @author pinea
     * @date 2023/4/20 0:51
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
     * @date 2023/4/20 0:51
     */
    @Override
    public int size() {
        return registryTable == null ? 0 : registryTable.size();
    }
}
