/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.aop.api;

/**
 * 代理对象生成接口
 * @author HuHui
 * @version $Id: ProxyResolver.java, v 0.1 2017年11月22日 上午10:21:13 HuHui Exp $
 */
public interface ProxyResolver {

    /**
     * 生成普通代理对象
     * <p>生成的代理对象是对目标类所有方法的代理</p>
     * @param interceptor  AOP拦截器,继承自{@link ProxyInterceptor}
     * @param targetClass  被代理对象的类型
     * @param target       被代理对象实例
     * @return             代理对象
     */
    Object newProxyInstance(Object interceptor, Class<?> targetClass, Object target);

}
