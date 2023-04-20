package org.pineapple.common.base.lang;

/**
 * <p>支持重新加载的注册表。</p>
 * <p>由于重新加载需要清除原有注册信息，
 * 所以所有可以被当前注册表注册的注册信息需要实现{@link Clearable}接口</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/20
 */
public interface ReloadableRegistry<K, V extends Clearable> extends Registry<K, V> {
    /**
     * <p>重新加载注册表中注册信息</p>
     *
     * @param infoKey 注册信息的key
     * @return boolean
     * @author pinea
     * @date 2023/4/20 0:51
     */
    @SuppressWarnings("all")
    boolean reload(K infoKey);

    /**
     * <p>重新加载注册表中所有的注册信息</p>
     *
     * @author pinea
     * @date 2023/4/17 16:44:35
     */
    void reload();
}
