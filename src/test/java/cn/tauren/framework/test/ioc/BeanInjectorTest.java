/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.ioc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.tauren.framework.ioc.api.BeanFactory;
import cn.tauren.framework.ioc.impl.DefaultBeanFactory;
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

    private BeanFactory factory;

    @Before
    public void init() {
        factory = new DefaultBeanFactory("cn.tauren.framework.test");
    }

    @Test
    public void testInject() {
    }

    @Test
    public void testReference() {
        TeacherService tService = factory.getBean("teacherServiceImpl", TeacherService.class);
        StudentService sService = factory.getBean("studentServiceImpl", StudentService.class);
        Assert.assertNotNull(tService);
        Assert.assertNotNull(sService);
    }

    @Test
    public void testProxy() {
        UserService userService = factory.getBean("userServiceImpl", UserServiceImpl.class);
        userService.getId(112233);
    }

}
