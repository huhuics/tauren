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
 * <p>客户端继承此类,并选择实现<code>before</code>和<code>after</code>方法实现前置和后置增强</p>
 * @author HuHui
 * @version $Id: ProxyInterceptor.java, v 0.1 2017年11月22日 上午9:21:10 HuHui Exp $
 */
public abstract class ProxyInterceptor {

    /**
     * 前置增强
     */
    protected abstract void before();

    /**
     * 后置增强
     */
    protected abstract void after();

    /**
     * 生成代理对象
     * @param targetClass  被代理对象的类型(类或接口)
     * @param target       被代理对象实例
     * @return             代理对象
     */
    public Object newProxyInstance(Class<?> targetClass, final Object target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                before();
                Object ret = method.invoke(target, args);
                after();
                return ret;
            }
        });

        return enhancer.create();
    }

}
