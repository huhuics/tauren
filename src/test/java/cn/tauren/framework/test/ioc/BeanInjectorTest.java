/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.ioc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.tauren.framework.ioc.api.BeanFactory;
import cn.tauren.framework.ioc.api.BeanInjector;
import cn.tauren.framework.ioc.api.ClassScanner;
import cn.tauren.framework.ioc.impl.DefaultBeanFactory;
import cn.tauren.framework.ioc.impl.DefaultBeanInjector;
import cn.tauren.framework.ioc.impl.DefaultClassScanner;
import cn.tauren.framework.test.StudentService;
import cn.tauren.framework.test.TeacherService;

/**
 * 
 * @author HuHui
 * @version $Id: BeanInjectorTest.java, v 0.1 2017年11月17日 下午5:17:58 HuHui Exp $
 */
public class BeanInjectorTest {

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
    public void testInject() {
        Assert.assertNotNull(injector);
    }

    @Test
    public void testReference() {
        TeacherService tService = factory.getBean("teacherServiceImpl", TeacherService.class);
        StudentService sService = factory.getBean("studentServiceImpl", StudentService.class);
        tService.tech();
        sService.study();
    }

}
