package com.sunveee.tauren.ioc.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.sunveee.tauren.ioc.BeanFactory;
import com.sunveee.tauren.ioc.BeanInjector;
import com.sunveee.tauren.ioc.ClassScanner;
import com.sunveee.tauren.ioc.annotation.Bean;
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

                // 创建实例
                Object instance;
                try {
                    // TODO 这里直接调用了类的无参构造方法创建实例,后续还考虑加上对@InstanceConstructor注解的支持
                    instance = _clazz.newInstance();
                    beanContainer.put(beanName, instance);
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("初始化类失败", e);
                }

            }
        }
    }

    /**
     * 依赖注入
     */
    private void inject() {
        this.beanInjector.inject();
    }

}
