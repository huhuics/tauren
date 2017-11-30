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

        initBeanContainer(); // 初始化bean容器

        this.beanInjector = new DefaultBeanInjector(this.classScanner, this);

        inject(); // 依赖注入
    }

    @Override
    public Map<String, Object> getBeans() {
        return beanContainer;
    }

    /**
     * 初始化bean容器
     */
    private void initBeanContainer() {
        List<Class<?>> classList = classScanner.getClassListByAnnotation(Bean.class);
        if (CollectionUtils.isNotEmpty(classList)) {
            for (Class<?> clazz : classList) {
                Bean beanAnno = clazz.getAnnotation(Bean.class);
                String beanName = IoCUtil.getDefaultBeanNameByClass(clazz);

                //如果指定了bean的name则修改默认名称
                if (StringUtils.isNotBlank(beanAnno.name())) {
                    beanName = beanAnno.name();
                }

                AssertUtil.assertTrue(!beanContainer.containsKey(beanName), "类名重复");

                // TODO 创建实例
                Object instance = null;

                beanContainer.put(beanName, instance);
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
