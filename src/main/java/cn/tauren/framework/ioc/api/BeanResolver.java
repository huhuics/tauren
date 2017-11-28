/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.ioc.api;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import cn.tauren.framework.aop.annotation.Intercept;
import cn.tauren.framework.aop.api.ProxyResolver;
import cn.tauren.framework.exception.BeanCreationException;
import cn.tauren.framework.exception.BeanException;
import cn.tauren.framework.exception.NoSuchBeanException;
import cn.tauren.framework.util.AssertUtil;

/**
 * 抽象类，负责解决类的实例化
 * @author HuHui
 * @version $Id: BeanResolver.java, v 0.1 2017年11月27日 下午9:05:09 HuHui Exp $
 */
public abstract class BeanResolver {

    private BeanFactory   beanFactory;

    /** 代理类生成器 */
    private ProxyResolver proxyResolver;

    public BeanResolver() {
    }

    public BeanResolver(BeanFactory beanFactory, ProxyResolver proxyResolver) {
        this.beanFactory = beanFactory;
        this.proxyResolver = proxyResolver;
    }

    /**
     * 抽象方法，返回以驼峰命名的bean name
     * @return
     */
    public abstract String getBeanName(Class<?> clazz);

    /**
     * 实例化参数列表中的类
     * @param classes
     */
    public void resolve(List<Class<?>> classes) {
        if (CollectionUtils.isEmpty(classes)) {
            return;
        }

        for (Class<?> clazz : classes) {
            String beanName = getBeanName(clazz);

            AssertUtil.assertTrue(!beanFactory.containsKey(beanName), "类名重复");

            try {
                Object instance = clazz.newInstance();

                if (clazz.isAnnotationPresent(Intercept.class)) {
                    //生成的代理对象替换原对象
                    instance = getProxyInstance(clazz, instance);
                }

                beanFactory.putClass(clazz, beanName, instance);
            } catch (Exception e) {
                throw new BeanCreationException("初始化类失败", e);
            }
        }

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

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void setProxyResolver(ProxyResolver proxyResolver) {
        this.proxyResolver = proxyResolver;
    }

}
