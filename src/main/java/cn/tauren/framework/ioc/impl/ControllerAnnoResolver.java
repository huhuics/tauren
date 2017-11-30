/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.ioc.impl;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cn.tauren.framework.aop.api.ProxyResolver;
import cn.tauren.framework.ioc.api.BeanFactory;
import cn.tauren.framework.ioc.api.BeanResolver;
import cn.tauren.framework.mvc.ActionResolver;
import cn.tauren.framework.mvc.annotation.Controller;
import cn.tauren.framework.mvc.annotation.RequestMapping;
import cn.tauren.framework.util.ClassUtil;

/**
 * 负责@Controller注解的类的初始化工作
 * @author HuHui
 * @version $Id: ControllerAnnoResolver.java, v 0.1 2017年11月27日 下午9:23:34 HuHui Exp $
 */
public class ControllerAnnoResolver extends BeanResolver {

    public ControllerAnnoResolver(BeanFactory beanFactory, ProxyResolver proxyResolver) {
        super(beanFactory, proxyResolver);
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

    @Override
    public void resolve(List<Class<?>> classes) {
        //1.调用父类方法，将Bean放入容器
        super.resolve(classes);

        //2.处理Controller
        handleController(classes);
    }

    /**
     * 处理Controller的方法
     */
    private void handleController(List<Class<?>> classes) {
        if (CollectionUtils.isEmpty(classes)) {
            return;
        }

        for (Class<?> clazz : classes) {
            //处理Controller每个被@RequestMapping标注的方法
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(RequestMapping.class)) {
                    continue;
                }

                ActionResolver.resolve(method.getAnnotation(RequestMapping.class), beanFactory.getBean(clazz), method);
            }
        }
    }
}
