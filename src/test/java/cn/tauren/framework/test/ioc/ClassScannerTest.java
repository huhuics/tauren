/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.ioc;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import cn.tauren.framework.ioc.DefaultClassScanner;

/**
 * 
 * @author HuHui
 * @version $Id: ClassScannerTest.java, v 0.1 2017年11月16日 上午10:09:51 HuHui Exp $
 */
public class ClassScannerTest {

    private DefaultClassScanner scanner = new DefaultClassScanner("cn.tauren.framework.test");

    @Test
    public void testGetClasses() {
        List<Class<?>> classes = scanner.getClasses();
        Assert.assertTrue(CollectionUtils.isNotEmpty(classes));
    }

}
