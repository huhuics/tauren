/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.ioc.api;

/**
 * Bean容器操作接口
 * @author HuHui
 * @version $Id: Container.java, v 0.1 2017年11月28日 上午11:12:41 HuHui Exp $
 */
public interface Container {

    /**
     * 向Bean容器中设置元素
     * @param clazz     类的类型
     * @param name      类的名称(Simple Name)
     * @param instance  类的实例
     */
    void putClass(Class<?> clazz, String name, Object instance);

    /**
     * 判断容器内是否存在名称为name的Bean实例
     * @param name  Bean Simple Name
     * @return      存在返回true
     */
    boolean containsKey(String name);

    /**
     * 移除容器内的Bean实例
     * @param clazz  类的类型
     * @param name   类的名称(Simple Name)
     */
    void remove(Class<?> clazz, String name);

}
