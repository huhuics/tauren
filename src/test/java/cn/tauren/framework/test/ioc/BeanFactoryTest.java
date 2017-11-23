/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.ioc;

import org.junit.Assert;
import org.junit.Test;

import cn.tauren.framework.exception.BeanException;
import cn.tauren.framework.exception.BeanNotOfRequiredTypeException;
import cn.tauren.framework.ioc.api.BeanFactory;
import cn.tauren.framework.ioc.impl.DefaultBeanFactory;
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

    private BeanFactory factory = new DefaultBeanFactory();

    @Test
    public void testGetBeanByName() {
        Object bean = factory.getBean("classroomService");
        Assert.assertNotNull(bean);
    }

    @Test
    public void testGetBeanByType() {
        Object bean2 = factory.getBean(ClassroomService.class);
        Assert.assertNotNull(bean2);
    }

    @Test
    public void testGetBeanByType2() {
        StudentService bean = factory.getBean(StudentService.class);
        Assert.assertNotNull(bean);
        bean.study();
    }

    @Test
    public void testGetBeanByNameAndType() {
        ClassroomService bean3 = factory.getBean("classroomService", ClassroomService.class);
        Assert.assertNotNull(bean3);
    }

    @Test(expected = BeanNotOfRequiredTypeException.class)
    public void testGetBeanByNameAndType2() {
        factory.getBean("classroomService", StudentService.class);
    }

    /**
     * 测试多接口实现类情况
     */
    @Test
    public void testGetBean() {
        Object bean1 = factory.getBean("userServiceImpl");
        Assert.assertNotNull(bean1);

        Object bean2 = null;
        try {
            bean2 = factory.getBean(UserService.class);
        } catch (BeanException e) {
            System.out.println("获取bean2失败");
        }
        Assert.assertNull(bean2);

        Object bean3 = null;
        try {
            bean3 = factory.getBean("userServiceImpl", UserService.class);
        } catch (BeanException e) {
            System.out.println("获取bean3失败");
        }
        Assert.assertNotNull(bean3);

        Object bean4 = null;
        try {
            bean4 = factory.getBean("userServiceImpl", StudentService.class);
        } catch (BeanException e) {
            System.out.println("获取bean4失败");
        }
        Assert.assertNotNull(bean4);

    }

    @Test
    public void testAbstractClass() {
        AbstractService bean = factory.getBean(AbstractService.class);
        bean.service();
    }
}
