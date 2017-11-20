/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.ioc;

import org.junit.Before;
import org.junit.Test;

import cn.tauren.framework.ioc.DefaultBeanFactory;
import cn.tauren.framework.ioc.DefaultBeanInjector;
import cn.tauren.framework.ioc.DefaultClassScanner;
import cn.tauren.framework.ioc.api.ClassScanner;

/**
 * 
 * @author HuHui
 * @version $Id: BeanInjectorTest.java, v 0.1 2017年11月17日 下午5:17:58 HuHui Exp $
 */
public class BeanInjectorTest {

    private ClassScanner        scanner;

    private DefaultBeanFactory       container;

    private DefaultBeanInjector injector;

    @Before
    public void init() {
        scanner = new DefaultClassScanner("cn.tauren.framework.test");
        container = new DefaultBeanFactory(scanner);
        injector = new DefaultBeanInjector(container, scanner);
    }

    @Test
    public void testInject() {
        injector.inject();
    }

}
