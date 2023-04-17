package org.pineapple.common.base.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.pineapple.common.base.utils.Snowflake;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p></p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/17
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "snowflake")
public class SnowflakeConfig {
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

    @Bean
    public Snowflake snowflake() {
        log.info("正在注入Snowflake");
        return Snowflake.getInstance(datacenterId, machineId);
    }
}
