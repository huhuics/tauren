/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.tauren.framework.context.ApplicationContext;
import cn.tauren.framework.context.DefaultApplicationContext;

/**
 * 测试事务
 * @author HuHui
 * @version $Id: TransactionTest.java, v 0.1 2017年12月5日 下午7:04:09 HuHui Exp $
 */
public class TransactionTest {

    private Connection         conn;

    private ApplicationContext context = new DefaultApplicationContext();

    @Before
    public void init() throws SQLException, ClassNotFoundException {

        String driver = "com.mysql.jdbc.Driver";

        String url = "jdbc:mysql://168.33.131.164:3306/joice";

        String user = "root";
        String password = "huhui";

        Class.forName(driver);
        conn = DriverManager.getConnection(url, user, password);

    }

    @Test
    public void testTrans1() throws SQLException {
        BizPayOrderDao dao = context.getBean(BizPayOrderDao.class);
        Assert.assertNotNull(dao);
        String sql = "UPDATE biz_pay_order SET trade_amount = ? WHERE id = ?";
        Object[] params = { 12345, 5 };
        dao.update(conn, sql, params);
    }

}
