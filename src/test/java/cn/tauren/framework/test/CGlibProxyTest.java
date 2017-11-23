/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import org.junit.Test;

import cn.tauren.framework.util.ProxyUtil;

/**
 * 测试CGLib的代理方式
 * @author HuHui
 * @version $Id: CGlibProxyTest.java, v 0.1 2017年11月21日 下午8:09:23 HuHui Exp $
 */
public class CGlibProxyTest {

    @Test
    public void testInvocationhandlerWithClass() {
        final UserService uService = new UserServiceImpl();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserServiceImpl.class);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("cglib proxy class");
                return method.invoke(uService, args);
            }
        });

        final UserService proxy1 = (UserService) enhancer.create();
        proxy1.getId(112);

        //嵌套代理
        UserService reProxy = (UserService) Enhancer.create(UserService.class, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("cglib reproxy");
                return method.invoke(proxy1, args);
            }
        });

        reProxy.getId(222);

    }

    @Test
    public void testInvocationhandlerWithInterface() {
        final UserService uService = new UserServiceImpl();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserService.class);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("cglib proxy interface");
                return method.invoke(uService, args);
            }
        });

        UserService proxy = (UserService) enhancer.create();
        proxy.getId(211);
    }

    @Test
    public void testProxyUtil() {
        UserService proxy = ProxyUtil.newProxyInstance(UserService.class, new UserServiceImpl());
        proxy.getId(111);

        UserServiceImpl proxy1 = ProxyUtil.newProxyInstance(UserServiceImpl.class, new UserServiceImpl());
        proxy1.getId(222);
    }

    @Test
    public void testEnhancerCreate1() {
        final UserService uService = new UserServiceImpl();
        Class<?>[] clazzArr = { UserService.class, StudentService.class };
        StudentService proxy = (StudentService) Enhancer.create(UserServiceImpl.class, clazzArr, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(uService, args);
            }
        });

        proxy.study();

    }

    @Test
    public void testEnhancerCreate2() {
        final UserService uService = new UserServiceImpl();
        UserServiceImpl proxy = (UserServiceImpl) Enhancer.create(UserServiceImpl.class, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(uService, args);
            }
        });

        proxy.study();
        proxy.getId(110);
    }

}
