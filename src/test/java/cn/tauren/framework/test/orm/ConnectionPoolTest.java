/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.orm;

import java.sql.Connection;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Assert;
import org.junit.Test;

import cn.tauren.framework.orm.ConnectionFactory;

/**
 * 
 * @author HuHui
 * @version $Id: ConnectionPoolTest.java, v 0.1 2017年12月6日 上午9:49:30 HuHui Exp $
 */
public class ConnectionPoolTest {

    @Test
    public void testBorrow() throws Exception {
        GenericObjectPool<Connection> pool = new GenericObjectPool<Connection>(new ConnectionFactory());
        Connection conn = pool.borrowObject();

        Assert.assertNotNull(conn);
    }

}
