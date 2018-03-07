/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.orm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Assert;
import org.junit.Test;

import cn.tauren.framework.context.ApplicationContext;
import cn.tauren.framework.context.DefaultApplicationContext;
import cn.tauren.framework.orm.ConnectionFactory;

/**
 * 
 * @author HuHui
 * @version $Id: ConnectionPoolTest.java, v 0.1 2017年12月6日 上午9:49:30 HuHui Exp $
 */
public class ConnectionPoolTest {

    private ApplicationContext context = new DefaultApplicationContext();

    @Test
    public void testBorrow() throws Exception {
        GenericObjectPool<Connection> pool = new GenericObjectPool<Connection>(new ConnectionFactory());
        Connection conn = pool.borrowObject();

        Assert.assertNotNull(conn);
    }

    @Test
    public void testConnectionPool() throws Exception {
        final BizPayOrderDao dao = context.getBean(BizPayOrderDao.class);

        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 10; i++) {
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String sql = "UPDATE biz_pay_order SET trade_amount = ? WHERE id = ?";
                    Object[] params = { 123, 5 };

                    for (int i = 0; i < 2; i++) {
                        int ret = 0;
                        try {
                            ret = dao.updateWithTran(sql, params);
                        } catch (SQLException e1) {
                        }
                        Assert.assertTrue(ret == 1);
                        System.out.println(Thread.currentThread().getName() + "当前第" + (i + 1) + "次执行");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            });
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.MINUTES);

    }

}
