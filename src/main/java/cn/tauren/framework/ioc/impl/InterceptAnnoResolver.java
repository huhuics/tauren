/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.ioc.impl;

import cn.tauren.framework.aop.annotation.Intercept;
import cn.tauren.framework.aop.api.ProxyResolver;
import cn.tauren.framework.exception.BeanException;
import cn.tauren.framework.exception.NoSuchBeanException;
import cn.tauren.framework.ioc.api.BeanFactory;
import cn.tauren.framework.ioc.api.BaseResolver;

/**
 * 负责@Intercept注解的类的初始化工作
 * @author HuHui
 * @version $Id: InterceptAnnoResolver.java, v 0.1 2017年12月5日 下午2:25:10 HuHui Exp $
 */
public class InterceptAnnoResolver extends BaseResolver {

    /** 代理类生成器 */
    private ProxyResolver proxyResolver;

    public InterceptAnnoResolver() {
        super();
    }

    public InterceptAnnoResolver(BeanFactory beanFactory, ProxyResolver proxyResolver) {
        super(beanFactory);
        this.proxyResolver = proxyResolver;
    }

    @Override
    public Object getInstance(Class<?> clazz) throws Exception {
        Object instance = super.getInstance(clazz);

        //生成的代理对象替换原对象
        instance = getProxyInstance(clazz, instance);

        return instance;
    }

    /**
     * @param clazz  被代理类的类型(类或接口)
     * @param target 被代理类的实例
     * @return       代理类
     */
    private Object getProxyInstance(Class<?> clazz, Object target) {
        Object proxyIns = target;
        Intercept interAnno = clazz.getAnnotation(Intercept.class);

        Class<?>[] types = interAnno.type();
        for (Class<?> type : types) {
            proxyIns = doGetProxyInstance(type, clazz, proxyIns);
        }

        return proxyIns;
    }

    private Object doGetProxyInstance(Class<?> interceptorClass, Class<?> targetClass, Object target) {
        Object interceptor = null;

        try {
            interceptor = beanFactory.getBean(interceptorClass);
        } catch (BeanException e) {
            throw new NoSuchBeanException("no such bean's type is " + interceptorClass.getSimpleName());
        }
        return proxyResolver.newProxyInstance(interceptor, targetClass, target);
    }

}
