/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.aop.api;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

/**
 * 代理拦截模板类
 * <p>客户端继承此类,并选择覆盖<code>before</code>, <code>after</code>, <code>exception</code方法实现前置和后置增强</p>
 * @author HuHui
 * @version $Id: ProxyInterceptor.java, v 0.1 2017年11月22日 上午9:21:10 HuHui Exp $
 */
public abstract class ProxyInterceptor {

    /**
     * 前置增强
     */
    protected void before() {

    }

    /**
     * 后置增强
     */
    protected void after() {

    }

    /**
     * 异常
     */
    protected void exception(Throwable e) {

    }

    /**
     * 生成代理对象
     * @param targetClass  被代理对象的类型(类或接口)
     * @param target       被代理对象实例
     * @return             代理对象
     */
    @SuppressWarnings("unchecked")
    public <T> T newProxyInstance(Class<T> targetClass, final Object target) {
        return (T) Enhancer.create(targetClass, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                before();
                Object ret = null;
                try {
                    ret = method.invoke(target, args);
                } catch (Exception e) {
                    exception(e);
                }
                after();
                return ret;
            }
        });
    }

}
