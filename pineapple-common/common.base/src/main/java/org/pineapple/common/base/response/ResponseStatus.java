package org.pineapple.common.base.response;

import org.pineapple.common.base.lang.KeyValueEnumeratedValues;

/**
 * <p>响应状态类型枚举值</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/15
 */
public interface ResponseStatus extends KeyValueEnumeratedValues<Integer, String> {
    /**
     * <p>获取响应状态编码</p>
     *
     * @return java.lang.Integer
     * @author pinea
     * @date 2023/4/15 21:16:05
     */
    Integer getCode();

    /**
     * <p>获取响应状态内容</p>
     *
     * @return java.lang.String
     * @author pinea
     * @date 2023/4/15 21:16:08
     */
    String getMessage();

    /**
     * <p>获取当前枚举所映射的key</p>
     *
     * @return K
     * @author pinea
     * @date 2023/4/15 21:06:06
     * @deprecated 建议直接使用getCode()方法
     */
    @Override
    @Deprecated
    default Integer getKey() {
        return this.getCode();
    }

    /**
     * <p>获取当前枚举所映射的value</p>
     *
     * @return V
     * @author pinea
     * @date 2023/4/15 21:05:19
     * @deprecated 建议直接使用getMessage()方法
     */
    @Override
    @Deprecated
    default String getValue() {
        return this.getMessage();
    }
}
