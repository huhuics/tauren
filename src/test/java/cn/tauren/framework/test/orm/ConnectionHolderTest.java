/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.orm;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.Test;

import cn.tauren.framework.orm.ConnectionHolder;

/**
 * 
 * @author HuHui
 * @version $Id: ConnectionHolderTest.java, v 0.1 2017年12月5日 下午8:50:30 HuHui Exp $
 */
public class ConnectionHolderTest {

    @Test
    public void testGet() {
        Connection conn = ConnectionHolder.get();
        Assert.assertNotNull(conn);
    }

}
