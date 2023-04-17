package org.pineapple.common.base.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>业务公共属性</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/17
 */
@Getter
@Setter
@Component(value = "bizCommonProperties")
@ConfigurationProperties(prefix = "pineapple")
public class BizCommonProperties {
    /**
     * 雪花算法
     */
    private SnowflakeProperties snowflake;
}
