package com.sunveee.tauren.ioc.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.sunveee.tauren.ioc.BeanFactory;
import com.sunveee.tauren.ioc.BeanInjector;
import com.sunveee.tauren.ioc.ClassScanner;
import com.sunveee.tauren.ioc.annotation.Bean;
import com.sunveee.tauren.ioc.annotation.InstanceMethod;
import com.sunveee.tauren.util.AssertUtil;
import com.sunveee.tauren.util.IoCUtil;

/**
 * Bean工厂实现
 * 
 * @author 51
 * @version $Id: DefaultBeanFactory.java, v 0.1 2017年12月1日 下午3:23:15 51 Exp $
 */
public class DefaultBeanFactory implements BeanFactory {

    private final Map<String, Object> beanContainer;

    private final ClassScanner        classScanner;

    private final BeanInjector        beanInjector;

    public DefaultBeanFactory(String pkgName) {
        this.beanContainer = new HashMap<String, Object>();
        this.classScanner = new DefaultClassScanner(pkgName);

        initBeanContainer(); // 填充bean

        this.beanInjector = new DefaultBeanInjector(this);

        inject(); // 依赖注入
    }

    @Override
    public Map<String, Object> getBeans() {
        return beanContainer;
    }

    @Override
    public Object getBean(String beanName) {
        return beanContainer.get(beanName);
    }

    /**
     * 填充bean
     */
    private void initBeanContainer() {
        List<Class<?>> classList = classScanner.getClassListByAnnotation(Bean.class);
        if (CollectionUtils.isNotEmpty(classList)) {
            for (Class<?> _clazz : classList) {
                Bean beanAnno = _clazz.getAnnotation(Bean.class);
                String beanName = IoCUtil.getDefaultBeanNameByClass(_clazz);

                //如果指定了bean的name则修改默认名称
                if (StringUtils.isNotBlank(beanAnno.name())) {
                    beanName = beanAnno.name();
                }

                AssertUtil.assertTrue(!beanContainer.containsKey(beanName), "类名重复");

                beanContainer.put(beanName, generateBean(_clazz));

            }
        }
    }

    /**
     * 创建类实例
     * <p>根据类自身的构造方法创建类实例</p>
     * 
     * <p>优先级：<br>
     * 1. 被<code>@InstanceMethod</code>修饰的构造方法<br>
     * 2. 被<code>@InstanceMethod</code>修饰的静态方法<br>
     * 3. 无参构造方法</p>
     * 
     * <p>不限制<code>@InstanceMethod</code>的数量，但按照优先级生效</p>
     * 
     * @param clazz
     * @return
     */
    private Object generateBean(Class<?> clazz) {
        // 扫描所有构造方法
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> _constructor : constructors) {
            if (_constructor.isAnnotationPresent(InstanceMethod.class)) {
                _constructor.setAccessible(true);
                // TODO 考虑支持更多类型的参数传入
                Object[] args = _constructor.getAnnotation(InstanceMethod.class).args(); // 这里存在向上转型的过程，String-->Object
                try {
                    return _constructor.newInstance(args);
                } catch (Exception e) {
                    throw new RuntimeException("调用构造方法创建实例异常", e);
                }
            }
        }
        // 扫描所有方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method _method : methods) {
            if (_method.isAnnotationPresent(InstanceMethod.class)) {
                _method.setAccessible(true);
                // TODO 考虑支持更多类型的参数传入
                Object[] args = _method.getAnnotation(InstanceMethod.class).args(); // 这里存在向上转型的过程，String-->Object
                try {
                    return _method.invoke(null, args);
                } catch (Exception e) {
                    throw new RuntimeException("调用静态方法创建实例异常", e);
                }
            }
        }
        // 无被@InstanceMethod修饰的方法
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("调用默认构造方法创建实例异常", e);
        }
    }

    /**
     * 依赖注入
     */
    private void inject() {
        this.beanInjector.inject();
    }

}
