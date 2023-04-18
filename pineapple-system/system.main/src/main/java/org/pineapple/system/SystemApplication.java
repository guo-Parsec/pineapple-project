package org.pineapple.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>系统模块启动类</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/17
 */
@ComponentScan({
        "org.pineapple.common",
        "org.pineapple.system",
})
//@Import({BizCommonConfig.class, MybatisConfig.class})
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"org.pineapple.**.api"})
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
