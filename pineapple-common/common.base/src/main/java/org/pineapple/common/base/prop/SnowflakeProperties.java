package org.pineapple.common.base.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>雪花算法属性</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/17
 */
@Component
@ConfigurationProperties(prefix = "pineapple.snowflake")
public class SnowflakeProperties {
    /**
     * 数据中心
     */
    @Setter
    @Getter
    private long datacenterId;

    /**
     * 机器标识
     */
    @Setter
    @Getter
    private long machineId;
}
