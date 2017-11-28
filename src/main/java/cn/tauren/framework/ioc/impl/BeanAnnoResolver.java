/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.ioc.impl;

import org.apache.commons.lang3.StringUtils;

import cn.tauren.framework.aop.api.ProxyResolver;
import cn.tauren.framework.ioc.annotation.Bean;
import cn.tauren.framework.ioc.api.BeanFactory;
import cn.tauren.framework.ioc.api.BeanResolver;
import cn.tauren.framework.util.ClassUtil;

/**
 * 负责@Bean注解的类的初始化工作
 * @author HuHui
 * @version $Id: BeanAnnoResolver.java, v 0.1 2017年11月27日 下午9:22:23 HuHui Exp $
 */
public class BeanAnnoResolver extends BeanResolver {

    public BeanAnnoResolver() {
        super();
    }

    public BeanAnnoResolver(BeanFactory beanFactory, ProxyResolver proxyResolver) {
        super(beanFactory, proxyResolver);
    }

    @Override
    public String getBeanName(Class<?> clazz) {
        Bean beanAnno = clazz.getAnnotation(Bean.class);
        String beanName = ClassUtil.humpNaming(clazz.getSimpleName());

        //如果指定了bean的name则修改默认名称
        if (StringUtils.isNotBlank(beanAnno.value())) {
            beanName = beanAnno.value();
        }
        return beanName;
    }

}
