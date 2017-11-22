/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 
 * @author HuHui
 * @version $Id: JDKProxyTest.java, v 0.1 2017年11月21日 下午7:20:26 HuHui Exp $
 */
public class JDKProxyTest {

    public static void main(String[] args) {

        Class<?>[] clazzArr = { UserService.class };

        final UserService realObj = new UserServiceImpl();

        UserService uService = (UserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), clazzArr, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("日志");
                return method.invoke(realObj, args);
            }
        });

        uService.getId(22);

    }
}
