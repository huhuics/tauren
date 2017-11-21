/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.util;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

/**
 * 代理工具类,使用CGLib生成代理类
 * @author HuHui
 * @version $Id: ProxyUtil.java, v 0.1 2017年11月21日 下午8:32:56 HuHui Exp $
 */
public class ProxyUtil {

    @SuppressWarnings("unchecked")
    public static <T> T newProxyInstance(Class<T> clazz, final Object target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(target, args);
            }
        });

        return (T) enhancer.create();
    }

}
