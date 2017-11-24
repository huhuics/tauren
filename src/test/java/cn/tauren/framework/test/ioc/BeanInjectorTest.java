/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.ioc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.tauren.framework.context.ApplicationContext;
import cn.tauren.framework.context.DefaultApplicationContext;
import cn.tauren.framework.test.StudentService;
import cn.tauren.framework.test.TeacherService;
import cn.tauren.framework.test.UserService;
import cn.tauren.framework.test.UserServiceImpl;

/**
 * 
 * @author HuHui
 * @version $Id: BeanInjectorTest.java, v 0.1 2017年11月17日 下午5:17:58 HuHui Exp $
 */
public class BeanInjectorTest {

    private ApplicationContext context;

    @Before
    public void init() {
        context = new DefaultApplicationContext();
    }

    @Test
    public void testInject() {
    }

    @Test
    public void testReference() {
        TeacherService tService = context.getBean("teacherServiceImpl", TeacherService.class);
        StudentService sService = context.getBean("studentServiceImpl", StudentService.class);
        Assert.assertNotNull(tService);
        Assert.assertNotNull(sService);
    }

    @Test
    public void testProxy() {
        UserService userService = context.getBean("userServiceImpl", UserServiceImpl.class);
        userService.getId(112233);
    }

}
