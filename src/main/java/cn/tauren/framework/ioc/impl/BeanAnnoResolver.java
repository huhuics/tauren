/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.ioc.impl;

import java.util.Iterator;
import java.util.List;

import cn.tauren.framework.aop.annotation.Intercept;
import cn.tauren.framework.ioc.api.BaseResolver;
import cn.tauren.framework.ioc.api.BeanFactory;

/**
 * 负责@Bean注解的类的初始化工作
 * @author HuHui
 * @version $Id: BeanAnnoResolver.java, v 0.1 2017年11月27日 下午9:22:23 HuHui Exp $
 */
public class BeanAnnoResolver extends BaseResolver {

    public BeanAnnoResolver() {
        super();
    }

    public BeanAnnoResolver(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public void resolve(List<Class<?>> classes) {
        //移除被@Intercept标记的类
        remvoeInterceptClasses(classes);

        super.resolve(classes);
    }

    private void remvoeInterceptClasses(List<Class<?>> classes) {
        Iterator<Class<?>> iterator = classes.iterator();
        while (iterator.hasNext()) {
            Class<?> next = iterator.next();
            if (next.isAnnotationPresent(Intercept.class)) {
                iterator.remove();
            }
        }
    }

}
