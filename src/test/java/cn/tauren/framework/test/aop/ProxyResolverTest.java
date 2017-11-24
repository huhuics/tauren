/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.aop;

import org.junit.Before;
import org.junit.Test;

import cn.tauren.framework.context.ApplicationContext;
import cn.tauren.framework.context.DefaultApplicationContext;
import cn.tauren.framework.test.UserService;

/**
 * 
 * @author HuHui
 * @version $Id: ProxyResolverTest.java, v 0.1 2017年11月22日 上午11:33:13 HuHui Exp $
 */
public class ProxyResolverTest {

    private ApplicationContext factory;

    @Before
    public void init() {
        factory = new DefaultApplicationContext();
    }

    @Test
    public void testLogProxy() {
        UserService userService = factory.getBean("userServiceImpl", UserService.class);
        userService.getId(80477);
        userService.getName("扈三娘");
    }
}
