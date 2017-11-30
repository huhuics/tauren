package com.sunveee.tauren.ioc.impl;

import java.util.Iterator;
import java.util.Map;

import com.sunveee.tauren.ioc.BeanFactory;
import com.sunveee.tauren.ioc.BeanInjector;
import com.sunveee.tauren.ioc.ClassScanner;

/**
 * Bean注入器实现
 * 
 * @author 51
 * @version $Id: DefaultBeanInjector.java, v 0.1 2017年12月1日 下午3:22:58 51 Exp $
 */
public class DefaultBeanInjector implements BeanInjector {

    private final ClassScanner classScanner;

    private final BeanFactory  beanFactory;

    public DefaultBeanInjector(ClassScanner classScanner, BeanFactory beanFactory) {
        this.classScanner = classScanner;
        this.beanFactory = beanFactory;
    }

    @Override
    public void inject() {
        Map<String, Object> beans = beanFactory.getBeans();
        Iterator<String> beansInterator = beans.keySet().iterator();
        while (beansInterator.hasNext()) {
            String _beanName = beansInterator.next();
            Object _bean = beans.get(_beanName);

            // TODO
        }

    }

}
