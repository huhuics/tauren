/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.orm;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import cn.tauren.framework.context.ApplicationContext;
import cn.tauren.framework.context.DefaultApplicationContext;

/**
 * 测试事务
 * @author HuHui
 * @version $Id: TransactionTest.java, v 0.1 2017年12月5日 下午7:04:09 HuHui Exp $
 */
public class TransactionTest {

    private ApplicationContext context = new DefaultApplicationContext();

    @Test
    public void testTrans1() throws SQLException {
        BizPayOrderDao dao = context.getBean(BizPayOrderDao.class);
        Assert.assertNotNull(dao);
        String sql = "UPDATE biz_pay_order SET trade_amount = ? WHERE id = ?";
        Object[] params = { 321, 5 };
        //        dao.updateWithoutTran(sql, params);
        dao.updateWithTran(sql, params);

    }

}
