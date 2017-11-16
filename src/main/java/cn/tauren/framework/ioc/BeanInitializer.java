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

import cn.tauren.framework.Constants;
import cn.tauren.framework.ioc.annotation.Bean;
import cn.tauren.framework.util.AssertUtil;

/**
 * Bean初始化程序
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
 * @version $Id: BeanInitializer.java, v 0.1 2017年11月16日 上午10:56:56 HuHui Exp $
 */
public class BeanInitializer {

    /** 扫描路径 */
    private final String              pkgName;

    /**
     * 存放类的实例的Map
     * key为类的full name
     * value为实例对象
     */
    private final Map<String, Object> classMap;

    private final ClassScanner        scanner;

    public BeanInitializer(String pkgName) {
        this.pkgName = pkgName;
        classMap = new HashMap<String, Object>();
        scanner = new ClassScanner();
    }

    public void initBean() {
        List<Class<?>> classes = scanner.getClasses(pkgName);
        if (CollectionUtils.isNotEmpty(classes)) {
            for (Class<?> clazz : classes) {
                if (clazz.isAnnotationPresent(Bean.class)) {
                    Bean beanAnno = clazz.getAnnotation(Bean.class);
                    String beanFullName = clazz.getName();

                    //如果指定了bean的name则修改默认名称
                    if (StringUtils.isNotBlank(beanAnno.value())) {
                        int index = beanFullName.lastIndexOf(Constants.FILE_DOT);
                        beanFullName = beanFullName.substring(0, index) + Constants.FILE_DOT + beanAnno.value();
                    }

                    AssertUtil.assertTrue(!classMap.containsKey(beanFullName), "类名重复");

                    try {
                        classMap.put(beanFullName, clazz.newInstance());
                    } catch (Exception e) {
                        throw new RuntimeException("初始化类失败", e);
                    }

                }
            }
        }
    }

    public Map<String, Object> getClassMap() {
        return classMap;
    }

}
