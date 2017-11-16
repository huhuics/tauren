/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.ioc;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.junit.Assert;
import org.junit.Test;

import cn.tauren.framework.ioc.BeanContainer;
import cn.tauren.framework.ioc.ClassScanner;

/**
 * 
 * @author HuHui
 * @version $Id: BeanInitializerTest.java, v 0.1 2017年11月16日 下午12:37:23 HuHui Exp $
 */
public class BeanInitializerTest {

    private BeanContainer initializer = new BeanContainer(new ClassScanner("cn.tauren.framework.test"));

    @Test
    public void testInitBean() {
        initializer.initBean();
        Map<String, Object> classMap = initializer.getNameContainer();
        Assert.assertTrue(MapUtils.isNotEmpty(classMap));
        System.out.println(classMap);
    }

}
