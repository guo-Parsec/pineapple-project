package org.pineapple.common.access.config;

import org.pineapple.common.base.utils.Snowflake;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Mybatis配置类</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/17
 */
@Configuration
public class MybatisConfig {
    private final Snowflake snowflake;

    public MybatisConfig(Snowflake snowflake) {
        this.snowflake = snowflake;
    }

//    @Bean
//    public IdInterceptor idInterceptor() {
//        return new IdInterceptor(snowflake);
//    }
}
