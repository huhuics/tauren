/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.mvc.api;

import java.lang.reflect.Method;

import cn.tauren.framework.mvc.Action;
import cn.tauren.framework.mvc.annotation.RequestMapping;

/**
 * 封装URI与Action的对应关系
 * @author HuHui
 * @version $Id: ActionResolver.java, v 0.1 2017年11月29日 上午9:01:15 HuHui Exp $
 */
public interface ActionResolver {

    /**
     * 建立URI与Action之间的对应关系
     * @param mapping   {@link RequestMapping}
     * @param instance  Controller类实例
     * @param method    类方法
     */
    void resolve(RequestMapping mapping, Object instance, Method method);

    /**
     * 获取URI对应的Action对象
     * @param actionKey URI
     * @return          Action对象，如果不存在则返回null
     */
    Action mapping(String actionKey);

}
