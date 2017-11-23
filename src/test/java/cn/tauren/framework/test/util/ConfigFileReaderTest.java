/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.util;

import org.junit.Assert;
import org.junit.Test;

import cn.tauren.framework.ioc.api.BeanFactory;
import cn.tauren.framework.ioc.impl.DefaultBeanFactory;
import cn.tauren.framework.test.StudentService;

/**
 * 测试配置文件读取
 * @author HuHui
 * @version $Id: ConfigFileReaderTest.java, v 0.1 2017年11月23日 下午4:09:17 HuHui Exp $
 */
public class ConfigFileReaderTest {

    @Test
    public void test() {
        BeanFactory factory = new DefaultBeanFactory();
        StudentService service = factory.getBean("studentServiceImpl", StudentService.class);
        Assert.assertNotNull(service);
        service.study();
    }

}
