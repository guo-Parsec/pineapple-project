package org.pineapple.common.access.provider.fill;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * <p></p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/20
 */
@Component
public class FieldFillProviderPost implements BeanPostProcessor {
    private static final Logger log = LoggerFactory.getLogger(FieldFillProviderPost.class);

    @Override
    public Object postProcessBeforeInitialization(@Nullable Object bean, @Nullable String beanName) throws BeansException {
        if (bean instanceof FieldFillProvider) {
            FieldFillProvider provider = (FieldFillProvider) bean;
            FieldFillProviderRegistry registry = FieldFillProviderRegistry.getInstance();
            registry.getRegistryTable().put(provider.getClass(), provider);
            log.info("provider [{}] has been pushed into registry", provider.getClass());
        }
        return bean;
    }
}
