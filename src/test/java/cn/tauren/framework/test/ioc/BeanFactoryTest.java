/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.ioc;

import org.junit.Assert;
import org.junit.Test;

import cn.tauren.framework.context.ApplicationContext;
import cn.tauren.framework.context.DefaultApplicationContext;
import cn.tauren.framework.exception.BeanException;
import cn.tauren.framework.exception.BeanNotOfRequiredTypeException;
import cn.tauren.framework.test.AbstractService;
import cn.tauren.framework.test.ClassroomService;
import cn.tauren.framework.test.StudentService;
import cn.tauren.framework.test.UserService;

/**
 * 
 * @author HuHui
 * @version $Id: BeanFactoryTest.java, v 0.1 2017年11月16日 下午12:37:23 HuHui Exp $
 */
public class BeanFactoryTest {

    private ApplicationContext context = new DefaultApplicationContext();

    @Test
    public void testGetBeanByName() {
        Object bean = context.getBean("classroomService");
        Assert.assertNotNull(bean);
    }

    @Test
    public void testGetBeanByType() {
        Object bean2 = context.getBean(ClassroomService.class);
        Assert.assertNotNull(bean2);
    }

    @Test
    public void testGetBeanByType2() {
        StudentService bean = context.getBean(StudentService.class);
        Assert.assertNotNull(bean);
        bean.study();
    }

    @Test
    public void testGetBeanByNameAndType() {
        ClassroomService bean3 = context.getBean("classroomService", ClassroomService.class);
        Assert.assertNotNull(bean3);
    }

    @Test(expected = BeanNotOfRequiredTypeException.class)
    public void testGetBeanByNameAndType2() {
        context.getBean("classroomService", StudentService.class);
    }

    /**
     * 测试多接口实现类情况
     */
    @Test
    public void testGetBean() {
        Object bean1 = context.getBean("userServiceImpl");
        Assert.assertNotNull(bean1);

        Object bean2 = null;
        try {
            bean2 = context.getBean(UserService.class);
        } catch (BeanException e) {
            System.out.println("获取bean2失败");
        }
        Assert.assertNotNull(bean2);

        Object bean3 = null;
        try {
            bean3 = context.getBean("userServiceImpl", UserService.class);
        } catch (BeanException e) {
            System.out.println("获取bean3失败");
        }
        Assert.assertNotNull(bean3);

        Object bean4 = null;
        try {
            bean4 = context.getBean("userServiceImpl", StudentService.class);
        } catch (BeanException e) {
            System.out.println("获取bean4失败");
        }
        Assert.assertNotNull(bean4);

    }

    @Test
    public void testAbstractClass() {
        AbstractService bean = context.getBean(AbstractService.class);
        bean.service();
    }
}
