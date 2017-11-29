/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.context;

import java.util.Collection;

import cn.tauren.framework.ConfigFileReader;
import cn.tauren.framework.Constants;
import cn.tauren.framework.aop.api.ProxyResolver;
import cn.tauren.framework.aop.impl.ProxyResolverImpl;
import cn.tauren.framework.exception.BeanException;
import cn.tauren.framework.ioc.api.BeanFactory;
import cn.tauren.framework.ioc.api.ClassScanner;
import cn.tauren.framework.ioc.impl.DefaultBeanFactory;
import cn.tauren.framework.ioc.impl.DefaultClassScanner;
import cn.tauren.framework.util.AssertUtil;

/**
 * <code>ApplicationContext</code>的默认实现, 粘合框架各模块
 * @author HuHui
 * @version $Id: DefaultApplicationContext.java, v 0.1 2017年11月24日 上午9:16:13 HuHui Exp $
 */
public class DefaultApplicationContext implements ApplicationContext {

    /** 客户端包扫码路径 */
    private final String  pkgName;

    /** IoC容器 */
    private BeanFactory   factory;

    /** 类扫描器 */
    private ClassScanner  scanner;

    /** 代理类生成器 */
    private ProxyResolver proxyResolver;

    public DefaultApplicationContext() {
        this(Constants.DEFAULT_CONFIG_NAME);
    }

    public DefaultApplicationContext(String configFile) {
        pkgName = ConfigFileReader.getScanPackage(configFile);
        AssertUtil.assertNotBlank(pkgName, "package location cann't by empty!");

        scanner = new DefaultClassScanner(pkgName);
        proxyResolver = new ProxyResolverImpl();
        factory = new DefaultBeanFactory(scanner, proxyResolver);
    }

    @Override
    public Object getBean(String name) throws BeanException {
        return factory.getBean(name);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeanException {
        return factory.getBean(requiredType);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeanException {
        return factory.getBean(name, requiredType);
    }

    @Override
    public Collection<Object> getBeans() {
        return factory.getBeans();
    }

    @Override
    public void putClass(Class<?> clazz, String name, Object instance) {
        factory.putClass(clazz, name, instance);
    }

    @Override
    public boolean containsKey(String name) {
        return factory.containsKey(name);
    }

}
