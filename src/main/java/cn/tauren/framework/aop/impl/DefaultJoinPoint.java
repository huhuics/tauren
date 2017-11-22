/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.aop.impl;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import cn.tauren.framework.aop.api.JoinPoint;

/**
 * 连接点默认实现类
 * @author HuHui
 * @version $Id: DefaultJoinPoint.java, v 0.1 2017年11月22日 下午2:59:35 HuHui Exp $
 */
public class DefaultJoinPoint implements JoinPoint {

    private Class<?> targetType;

    private Object   target;

    private Method   method;

    private Object[] args;

    private Object   proxyObj;

    public DefaultJoinPoint(Class<?> targetType, Object target, Method method) {
        this.targetType = targetType;
        this.target = target;
        this.method = method;
    }

    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public Object proceed() throws Throwable {
        this.proxyObj = createProxy();
        return method.invoke(target, args);
    }

    private Object createProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetType);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                setMethod(method);
                setArgs(args);
                return method.invoke(target, args);
            }
        });
        return enhancer.create();
    }

}
