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

import cn.tauren.framework.aop.annotation.Intercept;
import cn.tauren.framework.aop.api.ProxyResolver;
import cn.tauren.framework.exception.BeanException;
import cn.tauren.framework.exception.BeanNotOfRequiredTypeException;
import cn.tauren.framework.exception.NoSuchBeanException;
import cn.tauren.framework.ioc.annotation.Bean;
import cn.tauren.framework.ioc.api.BeanFactory;
import cn.tauren.framework.ioc.api.BeanInjector;
import cn.tauren.framework.ioc.api.BaseResolver;
import cn.tauren.framework.ioc.api.ClassScanner;
import cn.tauren.framework.mvc.annotation.Controller;
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

    private BaseResolver                beanAnnoResolver;

    private BaseResolver                contrAnnoResolver;

    private BaseResolver                interceptAnnoResolver;

    public DefaultBeanFactory(ClassScanner scanner, ProxyResolver proxyResolver) {
        nameContainer = new HashMap<String, Object>();
        typeContainer = new HashMap<Class<?>, Object>();
        this.scanner = scanner;
        beanAnnoResolver = new BeanAnnoResolver(this);
        contrAnnoResolver = new ControllerAnnoResolver(this);
        interceptAnnoResolver = new InterceptAnnoResolver(this, proxyResolver);

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
            AssertUtil.assertTrue(beans.size() <= 1, requiredType.getName() + "接口有多个实现类,请使用名称注入方式");

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

    @Override
    public void putClass(Class<?> clazz, String name, Object instance) {
        nameContainer.put(name, instance);
        typeContainer.put(clazz, instance);
    }

    @Override
    public boolean containsKey(String name) {
        return nameContainer.containsKey(name);
    }

    private void initContainer() {
        //1.处理被@Bean标注的类
        List<Class<?>> beanAnnoClasses = scanner.getClassesByAnnotation(Bean.class);
        beanAnnoResolver.resolve(beanAnnoClasses);

        //2.处理被@Controller标注的类
        List<Class<?>> contrAnnoClasses = scanner.getClassesByAnnotation(Controller.class);
        contrAnnoResolver.resolve(contrAnnoClasses);

        //3.处理被@Intercept标注的类
        List<Class<?>> intcptAnnoClasses = scanner.getClassesByAnnotation(Intercept.class);
        interceptAnnoResolver.resolve(intcptAnnoClasses);
    }

    /**
     * 通过接口查找bean
     * @param requiredType 接口类型
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
