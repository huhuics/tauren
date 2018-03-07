/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.util;

import org.junit.Assert;
import org.junit.Test;

import cn.tauren.framework.context.ApplicationContext;
import cn.tauren.framework.context.DefaultApplicationContext;
import cn.tauren.framework.test.StudentService;

/**
 * 测试配置文件读取
 * @author HuHui
 * @version $Id: ConfigFileReaderTest.java, v 0.1 2017年11月23日 下午4:09:17 HuHui Exp $
 */
public class ConfigFileReaderTest {

    @Test
    public void test() {
        ApplicationContext factory = new DefaultApplicationContext();
        StudentService service = factory.getBean("studentServiceImpl", StudentService.class);
        Assert.assertNotNull(service);
        service.study();
    }

}
