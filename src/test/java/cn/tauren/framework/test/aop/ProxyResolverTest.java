/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.aop;

import org.junit.Before;
import org.junit.Test;

import cn.tauren.framework.ioc.api.BeanFactory;
import cn.tauren.framework.ioc.api.BeanInjector;
import cn.tauren.framework.ioc.api.ClassScanner;
import cn.tauren.framework.ioc.impl.DefaultBeanFactory;
import cn.tauren.framework.ioc.impl.DefaultBeanInjector;
import cn.tauren.framework.ioc.impl.DefaultClassScanner;
import cn.tauren.framework.test.UserService;

/**
 * 
 * @author HuHui
 * @version $Id: ProxyResolverTest.java, v 0.1 2017年11月22日 上午11:33:13 HuHui Exp $
 */
public class ProxyResolverTest {

    private ClassScanner scanner;

    private BeanFactory  factory;

    private BeanInjector injector;

    @Before
    public void init() {
        scanner = new DefaultClassScanner("cn.tauren.framework.test");
        factory = new DefaultBeanFactory(scanner);
        injector = new DefaultBeanInjector(factory, scanner);
    }

    @Test
    public void testLogProxy() {
        UserService userService = factory.getBean("userServiceImpl", UserService.class);
        userService.getUser(80477);
    }
}
