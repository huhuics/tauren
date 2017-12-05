/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.ioc.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import cn.tauren.framework.aop.api.ProxyResolver;
import cn.tauren.framework.ioc.api.BaseResolver;
import cn.tauren.framework.ioc.api.BeanFactory;
import cn.tauren.framework.orm.TransactionInterceptor;
import cn.tauren.framework.orm.annotation.Transaction;

/**
 * 处理@Bean中被@Transaction标注的类和方法
 * @author HuHui
 * @version $Id: TransactionAnnoResolver.java, v 0.1 2017年12月5日 下午4:05:03 HuHui Exp $
 */
public class TransactionAnnoResolver extends BaseResolver {

    /** 代理类生成器 */
    private ProxyResolver          proxyResolver;

    private TransactionInterceptor transInterceptor;

    public TransactionAnnoResolver() {
        super();
    }

    public TransactionAnnoResolver(BeanFactory beanFactory, ProxyResolver proxyResolver) {
        super(beanFactory);
        transInterceptor = new TransactionInterceptor();
        this.proxyResolver = proxyResolver;
    }

    @Override
    public void resolve(List<Class<?>> classes) {
        if (CollectionUtils.isEmpty(classes)) {
            return;
        }

        for (Class<?> clazz : classes) {

            Object targetInstance = beanFactory.getBean(clazz);

            if (clazz.isAnnotationPresent(Transaction.class)) {//类中所有方法都要增加事务
                Object proxyInstance = proxyResolver.newProxyInstance(transInterceptor, clazz, targetInstance);
                String beanName = getBeanName(clazz);
                beanFactory.remove(clazz, beanName);
                beanFactory.putClass(clazz, beanName, proxyInstance);
            } else {//类中被@Transaction标注的方法要增加事务

            }
        }

    }
}
