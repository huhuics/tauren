/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.ioc.api;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 类扫描器接口
 * @author HuHui
 * @version $Id: ClassScanner.java, v 0.1 2017年11月17日 上午10:47:54 HuHui Exp $
 */
public interface ClassScanner {

    /**
     * 不加过滤条件,获取所有类列表
     * @return  类列表
     */
    List<Class<?>> getClasses();

    /**
     * 获取被指定注解注释的类列表
     * @param anno  指定的注解
     * @return      类列表
     */
    List<Class<?>> getClassesByAnnotation(Class<? extends Annotation> anno);

    /**
     * 获取指定接口/父类的实现类/子类
     * @param superClass  指定的父类
     * @return            类列表
     */
    List<Class<?>> getClassesBySuper(Class<?> superClass);

}
