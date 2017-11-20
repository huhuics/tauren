/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.ioc;

import org.junit.Assert;
import org.junit.Test;

import cn.tauren.framework.exception.BeanNotOfRequiredTypeException;
import cn.tauren.framework.ioc.DefaultBeanFactory;
import cn.tauren.framework.ioc.DefaultClassScanner;
import cn.tauren.framework.ioc.api.BeanFactory;
import cn.tauren.framework.test.ClassroomService;
import cn.tauren.framework.test.StudentService;

/**
 * 
 * @author HuHui
 * @version $Id: BeanFactoryTest.java, v 0.1 2017年11月16日 下午12:37:23 HuHui Exp $
 */
public class BeanFactoryTest {

    private BeanFactory factory = new DefaultBeanFactory(new DefaultClassScanner("cn.tauren.framework.test"));

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
    public void testGetBeanByNameAndType() {
        ClassroomService bean3 = factory.getBean("classroomService", ClassroomService.class);
        Assert.assertNotNull(bean3);
    }

    @Test(expected = BeanNotOfRequiredTypeException.class)
    public void testGetBeanByNameAndType2() {
        factory.getBean("classroomService", StudentService.class);
    }
}
