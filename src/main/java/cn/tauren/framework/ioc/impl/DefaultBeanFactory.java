/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.ioc.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cn.tauren.framework.ConfigFileReader;
import cn.tauren.framework.aop.annotation.Intercept;
import cn.tauren.framework.aop.api.ProxyResolver;
import cn.tauren.framework.aop.impl.ProxyResolverImpl;
import cn.tauren.framework.exception.BeanCreationException;
import cn.tauren.framework.exception.BeanException;
import cn.tauren.framework.exception.BeanNotOfRequiredTypeException;
import cn.tauren.framework.exception.NoSuchBeanException;
import cn.tauren.framework.ioc.annotation.Bean;
import cn.tauren.framework.ioc.api.BeanFactory;
import cn.tauren.framework.ioc.api.BeanInjector;
import cn.tauren.framework.ioc.api.ClassScanner;
import cn.tauren.framework.util.AssertUtil;
import cn.tauren.framework.util.ClassUtil;

/**
 * Bean工厂
 * <ul>
 *  <li>扫描指定目录下的Bean</li>
 *  <li>将带有{@link Bean}注解的类初始化</li>
 *  <li>初始化完毕后放入Map中</li>
 * </ul>
 * 如果Bean存在以下情况将不能被框架实例化：
 * <ul>
 *  <li>没有无参的构造方法</li>
 *  <li>构造方法是私有的</li>
 * </ul>
 * @author HuHui
 * @version $Id: DefaultBeanFactory.java, v 0.1 2017年11月16日 上午10:56:56 HuHui Exp $
 */
public class DefaultBeanFactory implements BeanFactory {

    /**
     * 存放类的实例的Map,即用于存储Bean的容器
     * key为类的name
     * value为实例对象
     */
    private final Map<String, Object>   nameContainer;

    /**
     * 用于存储Bean的容器
     * key为类的类型
     * value为实例对象
     */
    private final Map<Class<?>, Object> typeContainer;

    /** 类扫描器 */
    private final ClassScanner          scanner;

    /** 类注入器 */
    private final BeanInjector          injector;

    /** 代理类生成器 */
    private final ProxyResolver         proxyResolver;

    public DefaultBeanFactory() {
        this(ConfigFileReader.getScanPackage());
    }

    public DefaultBeanFactory(String pkgName) {
        AssertUtil.assertNotBlank(pkgName, "package location cann't by empty!");

        nameContainer = new HashMap<String, Object>();
        typeContainer = new HashMap<Class<?>, Object>();
        scanner = new DefaultClassScanner(pkgName);
        proxyResolver = new ProxyResolverImpl();

        //初始化容器
        initContainer();
        injector = new DefaultBeanInjector(this, scanner);

        //注入类
        inject();
    }

    @Override
    public Object getBean(String name) throws BeanException {
        AssertUtil.assertNotBlank(name, "bean name can not by empty");

        Object object = nameContainer.get(name);
        if (object == null) {
            throw new NoSuchBeanException("no such bean named " + name);
        }
        return object;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) throws BeanException {
        AssertUtil.assertNotNull(requiredType, "bean type can not be null");

        Object object = null;
        String errMsg = "no such bean's type is " + requiredType.getSimpleName();

        //判断requiredType是接口/抽象类
        if (ClassUtil.isInterfaceOrAbstract(requiredType)) {
            List<Object> beans = getBeansByInterface(requiredType);

            AssertUtil.assertNotBlank(beans, errMsg);
            AssertUtil.assertTrue(beans.size() <= 1, "接口有多个实现类,请使用名称注入方式");

            object = beans.get(0);
        } else {
            object = typeContainer.get(requiredType);
        }

        if (object == null) {
            throw new NoSuchBeanException(errMsg);
        }

        return (T) object;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(String name, Class<T> requiredType) throws BeanException {
        AssertUtil.assertNotBlank(name, "bean name can not by empty");
        AssertUtil.assertNotNull(requiredType, "bean type can not be null");

        Object object = nameContainer.get(name);
        if (object == null) {
            throw new NoSuchBeanException("no such bean named " + name);
        }

        if (!requiredType.isInstance(object)) {
            throw new BeanNotOfRequiredTypeException("bean name which is " + name + " is not match whit the type of " + requiredType.getSimpleName());
        }

        return (T) object;
    }

    @Override
    public Collection<Object> getBeans() {
        return nameContainer.values();
    }

    private void initContainer() {
        List<Class<?>> classes = scanner.getClassesByAnnotation(Bean.class);
        if (CollectionUtils.isNotEmpty(classes)) {
            for (Class<?> clazz : classes) {
                Bean beanAnno = clazz.getAnnotation(Bean.class);
                String beanName = ClassUtil.humpNaming(clazz.getSimpleName());

                //如果指定了bean的name则修改默认名称
                if (StringUtils.isNotBlank(beanAnno.value())) {
                    beanName = beanAnno.value();
                }

                AssertUtil.assertTrue(!nameContainer.containsKey(beanName), "类名重复");

                try {
                    Object instance = clazz.newInstance();

                    if (clazz.isAnnotationPresent(Intercept.class)) {
                        //生成的代理对象替换原对象
                        instance = getProxyInstance(clazz, instance);
                    }

                    nameContainer.put(beanName, instance);
                    typeContainer.put(clazz, instance);
                } catch (Exception e) {
                    throw new BeanCreationException("初始化类失败", e);
                }
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
            interceptor = getBean(interceptorClass);
        } catch (BeanException e) {
            throw new NoSuchBeanException("no such bean's type is " + interceptorClass.getSimpleName());
        }
        return proxyResolver.newProxyInstance(interceptor, targetClass, target);
    }

    /**
     * 通过接口查找bean
     * @param requiredType
     * @return
     */
    private List<Object> getBeansByInterface(Class<?> requiredType) {
        List<Object> objects = new ArrayList<Object>();
        Iterator<Class<?>> iterator = typeContainer.keySet().iterator();
        while (iterator.hasNext()) {
            Class<?> key = iterator.next();
            if (requiredType.isAssignableFrom(key)) {
                objects.add(typeContainer.get(key));
            }
        }
        return objects;
    }

    private void inject() {
        injector.inject();
    }

}
