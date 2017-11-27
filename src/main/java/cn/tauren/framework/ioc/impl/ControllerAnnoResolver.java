/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.ioc.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.tauren.framework.aop.api.ProxyResolver;
import cn.tauren.framework.ioc.api.BeanFactory;
import cn.tauren.framework.ioc.api.BeanResolver;
import cn.tauren.framework.mvc.annotation.Controller;
import cn.tauren.framework.util.ClassUtil;

/**
 * 负责@Controller注解的类的初始化工作
 * @author HuHui
 * @version $Id: ControllerAnnoResolver.java, v 0.1 2017年11月27日 下午9:23:34 HuHui Exp $
 */
public class ControllerAnnoResolver extends BeanResolver {

    public ControllerAnnoResolver() {
        super();
    }

    public ControllerAnnoResolver(BeanFactory beanFactory, Map<String, Object> nameContainer, Map<Class<?>, Object> typeContainer, ProxyResolver proxyResolver) {
        super(beanFactory, nameContainer, typeContainer, proxyResolver);
    }

    @Override
    public String getBeanName(Class<?> clazz) {
        Controller contrAnno = clazz.getAnnotation(Controller.class);
        String beanName = ClassUtil.humpNaming(clazz.getSimpleName());

        //如果指定了bean的name则修改默认名称
        if (StringUtils.isNotBlank(contrAnno.value())) {
            beanName = contrAnno.value();
        }
        return beanName;
    }

}
