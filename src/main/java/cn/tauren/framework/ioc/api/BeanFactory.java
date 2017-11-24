/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.ioc.api;

import java.util.Collection;

import cn.tauren.framework.exception.BeanException;
import cn.tauren.framework.exception.BeanNotOfRequiredTypeException;
import cn.tauren.framework.exception.NoSuchBeanException;

/**
 * Bean工厂接口
 * @author HuHui
 * @version $Id: BeanFactory.java, v 0.1 2017年11月20日 下午7:59:25 HuHui Exp $
 */
public interface BeanFactory {

    /**
     * 通过类的名称获取对象实例
     * @param name            类的名称
     * @return                类的实例
     * @throws BeanException  如果该类不存在
     */
    Object getBean(String name) throws BeanException;

    /**
     * 通过类型获取类
     * @param requiredType    期望返回的类型(可以是接口类型)
     * @return                类的实例
     * @throws BeanException 如果该类不存在
     */
    <T> T getBean(Class<T> requiredType) throws BeanException;

    /**
     * 通过类名和类型获取类
     * @param name            类的名称
     * @param requiredType    期望返回的类型(可以是接口类型)
     * @return                类的实例
     * @throws NoSuchBeanException  如果该类不存在
     * @throws BeanNotOfRequiredTypeException  如果传入的类型与实际类型不一致
     */
    <T> T getBean(String name, Class<T> requiredType) throws BeanException;

    /**
     * 获取所有实例集合
     * @return  实例集合
     */
    Collection<Object> getBeans();

}
