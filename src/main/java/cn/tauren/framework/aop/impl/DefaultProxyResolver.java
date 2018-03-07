/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.aop.impl;

import cn.tauren.framework.aop.api.ProxyInterceptor;
import cn.tauren.framework.aop.api.ProxyResolver;
import cn.tauren.framework.exception.AopException;

/**
 * 代理类生成器
 * @author HuHui
 * @version $Id: ProxyResolver.java, v 0.1 2017年11月22日 上午10:25:19 HuHui Exp $
 */
public class DefaultProxyResolver implements ProxyResolver {

    @Override
    public Object newProxyInstance(Object interceptor, Class<?> targetClass, Object target) {
        if (!ProxyInterceptor.class.isInstance(interceptor)) {
            throw new AopException("拦截类类型错误");
        }
        ProxyInterceptor proxyInter = (ProxyInterceptor) interceptor;
        return proxyInter.newProxyInstance(targetClass, target);
    }

}
