package org.pineapple.common.base.lang;

import org.springframework.beans.factory.InitializingBean;

/**
 * <p>由Spring管理的自动装配的注册表</p>
 *
 * @author pinea
 * @version 1.0
 * @project pineapple-project
 * @date 2023/4/20
 */
public abstract class SpringAutowiredRegistry<B> implements Registry<Class<B>, B>, InitializingBean {

}
