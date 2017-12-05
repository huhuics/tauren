/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.ioc.api;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cn.tauren.framework.exception.BeanCreationException;
import cn.tauren.framework.ioc.annotation.Bean;
import cn.tauren.framework.ioc.impl.BeanAnnoResolver;
import cn.tauren.framework.ioc.impl.ControllerAnnoResolver;
import cn.tauren.framework.ioc.impl.InterceptAnnoResolver;
import cn.tauren.framework.util.AssertUtil;
import cn.tauren.framework.util.ClassUtil;

/**
 * 抽象类，负责解决类的实例化
 * @see BeanAnnoResolver
 * @see ControllerAnnoResolver
 * @see InterceptAnnoResolver
 * @author HuHui
 * @version $Id: BaseResolver.java, v 0.1 2017年11月27日 下午9:05:09 HuHui Exp $
 */
public abstract class BaseResolver {

    protected BeanFactory beanFactory;

    public BaseResolver() {
    }

    public BaseResolver(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 返回以驼峰命名的bean name
     */
    public String getBeanName(Class<?> clazz) {
        Bean beanAnno = clazz.getAnnotation(Bean.class);
        String beanName = ClassUtil.humpNaming(clazz.getSimpleName());

        //如果指定了bean的name则修改默认名称
        if (StringUtils.isNotBlank(beanAnno.value())) {
            beanName = beanAnno.value();
        }
        return beanName;
    }

    /**
     * 对象实例化方法
     */
    public Object getInstance(Class<?> clazz) throws Exception {
        return clazz.newInstance();
    }

    /**
     * 实例化参数列表中的类
     */
    public void resolve(List<Class<?>> classes) {
        if (CollectionUtils.isEmpty(classes)) {
            return;
        }

        for (Class<?> clazz : classes) {
            String beanName = getBeanName(clazz);

            AssertUtil.assertTrue(!beanFactory.containsKey(beanName), "类名重复");

            try {
                Object instance = getInstance(clazz);

                beanFactory.putClass(clazz, beanName, instance);
            } catch (Exception e) {
                throw new BeanCreationException("初始化类失败", e);
            }
        }

    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

}
