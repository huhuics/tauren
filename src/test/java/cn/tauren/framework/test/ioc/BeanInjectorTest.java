/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.ioc;

import org.junit.Before;
import org.junit.Test;

import cn.tauren.framework.ioc.BeanContainer;
import cn.tauren.framework.ioc.BeanInjector;
import cn.tauren.framework.ioc.ClassScanner;
import cn.tauren.framework.ioc.ClassScannerImpl;

/**
 * 
 * @author HuHui
 * @version $Id: BeanInjectorTest.java, v 0.1 2017年11月17日 下午5:17:58 HuHui Exp $
 */
public class BeanInjectorTest {

    private ClassScanner  scanner;

    private BeanContainer container;

    private BeanInjector  injector;

    @Before
    public void init() {
        scanner = new ClassScannerImpl("cn.tauren.framework.test");
        container = new BeanContainer(scanner);
        injector = new BeanInjector(container, scanner);
    }

    @Test
    public void testInject() {
        injector.inject();
    }

}
