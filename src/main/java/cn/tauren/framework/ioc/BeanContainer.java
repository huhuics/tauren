/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.ioc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cn.tauren.framework.ioc.annotation.Bean;
import cn.tauren.framework.util.AssertUtil;
import cn.tauren.framework.util.ClassUtil;

/**
 * Bean容器
 * <ul>
 *  <li>扫描指定目录下的所有Bean</li>
 *  <li>将带有{@link Bean}注解的类初始化</li>
 *  <li>初始化完毕后放入Map中</li>
 * </ul>
 * 如果Bean存在以下情况将不能被框架实例化：
 * <ul>
 *  <li>没有无参的构造方法</li>
 *  <li>构造方法是私有的</li>
 * </ul>
 * @author HuHui
 * @version $Id: BeanContainer.java, v 0.1 2017年11月16日 上午10:56:56 HuHui Exp $
 */
public class BeanContainer {

    /**
     * 存放类的实例的Map,即用于存储Bean的容器
     * key为类的name
     * value为实例对象
     */
    private final Map<String, Object>   nameContainer;

    /**
     * 存放类的实例Map
     * key为类的类型
     * value为类实例对象
     */
    private final Map<Class<?>, Object> typeContainer;

    /** 类扫描器 */
    private final ClassScanner          scanner;

    public BeanContainer(ClassScanner scanner) {
        nameContainer = new HashMap<String, Object>();
        typeContainer = new HashMap<Class<?>, Object>();
        this.scanner = scanner;
    }

    public void initBean() {
        List<Class<?>> classes = scanner.getClasses();
        if (CollectionUtils.isNotEmpty(classes)) {
            for (Class<?> clazz : classes) {
                if (clazz.isAnnotationPresent(Bean.class)) {
                    Bean beanAnno = clazz.getAnnotation(Bean.class);
                    String beanName = ClassUtil.humpNaming(clazz.getSimpleName());

                    //如果指定了bean的name则修改默认名称
                    if (StringUtils.isNotBlank(beanAnno.value())) {
                        beanName = beanAnno.value();
                    }

                    AssertUtil.assertTrue(!nameContainer.containsKey(beanName), "类名重复");

                    try {
                        Object instance = clazz.newInstance();
                        nameContainer.put(beanName, instance);
                        typeContainer.put(clazz.getClass(), instance);
                    } catch (Exception e) {
                        throw new RuntimeException("初始化类失败", e);
                    }

                }
            }
        }
    }

    public Map<String, Object> getNameContainer() {
        return nameContainer;
    }

}
