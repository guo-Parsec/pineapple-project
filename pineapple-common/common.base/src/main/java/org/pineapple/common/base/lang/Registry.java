package org.pineapple.common.base.lang;

import java.util.Map;

/**
 * <p>注册表</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/20
 */
public interface Registry<K, V> {
    /**
     * <p>获取注册表信息</p>
     *
     * @author pinea
     * @date 2023/4/20 1:06
     */
    Map<K, V> getRegistryTable();

    /**
     * <p>向注册表中推送新的注册信息</p>
     *
     * @param infoKey 注册信息的key
     * @param infoVal 注册信息的value
     * @author pinea
     * @date 2023/4/20 0:52
     */
    void push(K infoKey, V infoVal);

    /**
     * <p>从注册表中获取key指定的注册信息</p>
     *
     * @param infoKey 注册信息的key
     * @author pinea
     * @date 2023/4/20 0:52
     */
    V get(K infoKey);

    /**
     * <p>从注册表中删除key指定的注册信息</p>
     *
     * @param infoKey 注册信息的key
     * @author pinea
     * @date 2023/4/20 0:53
     */
    void remove(K infoKey);

    /**
     * <p>判断注册表中是否含有key指定的注册信息</p>
     *
     * @return boolean
     * @author pinea
     * @date 2023/4/20 0:53
     */
    boolean contains(K infoKey);

    /**
     * <p>判断注册表是否为空</p>
     *
     * @return boolean
     * @author pinea
     * @date 2023/4/20 0:51
     */
    boolean isEmpty();

    /**
     * <p>获取注册表长度</p>
     *
     * @return boolean
     * @author pinea
     * @date 2023/4/20 0:51
     */
    int size();
}
