package org.pineapple.common.base.config;

import org.pineapple.common.base.prop.BizCommonProperties;
import org.pineapple.common.base.prop.SnowflakeProperties;
import org.pineapple.common.base.utils.Snowflake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * <p>雪花算法配置类</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/17
 */
@Component
@Configuration
public class BizCommonConfig {
    private final BizCommonProperties bizCommonProperties;

    public BizCommonConfig(BizCommonProperties bizCommonProperties) {
        this.bizCommonProperties = bizCommonProperties;
    }

    @Bean
    public Snowflake snowflake() {
        SnowflakeProperties snowflake = bizCommonProperties.getSnowflake();
        return Snowflake.getInstance(snowflake.getDatacenterId(), snowflake.getMachineId());
    }
}