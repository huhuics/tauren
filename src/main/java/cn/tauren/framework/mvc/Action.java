/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.mvc;

import java.lang.reflect.Method;

import cn.tauren.framework.enums.ResponseMethod;

/**
 * Controller中每个被映射的方法都视为一个Action
 * @author HuHui
 * @version $Id: Action.java, v 0.1 2017年11月28日 下午12:43:44 HuHui Exp $
 */
public class Action {

    /** Controller实例 */
    private Object         instance;

    /** 方法 */
    private Method         method;

    /** 返回类型 */
    private ResponseMethod responseMethod;

    public Action() {
    }

    public Action(Object instance, Method method, ResponseMethod responseMethod) {
        this.instance = instance;
        this.method = method;
        this.responseMethod = responseMethod;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public ResponseMethod getResponseMethod() {
        return responseMethod;
    }

    public void setResponseMethod(ResponseMethod responseMethod) {
        this.responseMethod = responseMethod;
    }

}
