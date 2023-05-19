package org.pineapple.common.base.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * <p>Spring提供者</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/20
 */
@Component("springProvider")
public class SpringProvider implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(SpringProvider.class);

    private static ApplicationContext applicationContext;

    /**
     * <p>设置ApplicationContext对象</p>
     *
     * @param applicationContext Spring上下文对象
     * @author pinea
     * @date 2023/4/20 14:55
     */
    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        SpringProvider.applicationContext = applicationContext;
    }

    /**
     * <p>获取ApplicationContext对象</p>
     *
     * @return org.springframework.context.ApplicationContext
     * @author pinea
     * @date 2023/4/20 14:56
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据beanName获取bean
     *
     * @param name beanName
     * @return T
     * @author gcq
     * @date 2023/5/19 10:40
     */
    @SuppressWarnings("all")
    public static <T> T getBean(String name) {
        Object bean = null;
        try {
            bean = applicationContext.getBean(name);
        } catch (BeansException e) {
            log.error("spring get bean error: ", e);
        }
        return (T) bean;
    }

    /**
     * 根据bean类型获取bean
     *
     * @param beanClass bean类型
     * @return T
     * @author gcq
     * @date 2023/5/19 10:40
     */
    public static <T> T getBean(Class<T> beanClass) {
        T bean = null;
        try {
            bean = applicationContext.getBean(beanClass);
        } catch (BeansException e) {
            log.error("spring get bean error: ", e);
        }
        return bean;
    }

    /**
     * 根据beanName和bean类型获取bean
     *
     * @param name      beanName
     * @param beanClass bean类型
     * @return T
     * @author gcq
     * @date 2023/5/19 10:40
     */
    public static <T> T getBean(String name, Class<T> beanClass) {
        T bean = null;
        try {
            bean = applicationContext.getBean(name, beanClass);
        } catch (BeansException e) {
            log.error("spring get bean error: ", e);
        }
        return bean;
    }

    /**
     * 获取环境对象
     *
     * @return Environment
     * @author gcq
     * @date 2023/5/19 10:42
     */
    public static Environment getEnvironment() {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getEnvironment();
    }

    /**
     * 获取配置信息
     *
     * @param key 配置key
     * @return String
     * @author gcq
     * @date 2023/5/19 10:42
     */
    public static String getProperty(String key) {
        return getEnvironment() == null ? null : getEnvironment().getProperty(key);
    }

    /**
     * 获取应用名称
     *
     * @return String
     * @author gcq
     * @date 2023/5/19 10:41
     */
    public static String getApplicationName() {
        return getProperty("spring.application.name");
    }

    /**
     * 获取当前激活的配置文件
     *
     * @return String
     * @author gcq
     * @date 2023/5/19 10:42
     */
    public static String[] getActiveProfiles() {
        if (null == applicationContext) {
            return null;
        }
        return applicationContext.getEnvironment().getActiveProfiles();
    }
}
