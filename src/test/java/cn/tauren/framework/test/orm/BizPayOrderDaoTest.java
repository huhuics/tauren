/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.dbutils.DbUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author HuHui
 * @version $Id: BizPayOrderDaoTest.java, v 0.1 2017年12月4日 下午5:16:36 HuHui Exp $
 */
public class BizPayOrderDaoTest {

    private Connection     conn;

    private BizPayOrderDao dao;

    @Before
    public void init() throws SQLException, ClassNotFoundException {

        String driver = "com.mysql.jdbc.Driver";

        String url = "jdbc:mysql://168.33.131.164:3306/joice";

        String user = "root";
        String password = "huhui";

        Class.forName(driver);
        conn = DriverManager.getConnection(url, user, password);

        dao = new BizPayOrderDao(conn);
    }

    @Test
    public void testQuery1() throws SQLException {
        Assert.assertNotNull(dao);

        int id = 3;
        String sql = "SELECT * FROM biz_pay_order where id = ?";

        List<BizPayOrder> ret = dao.query(sql, BizPayOrder.class, id);

        Assert.assertTrue(CollectionUtils.isNotEmpty(ret));
        Assert.assertTrue(ret.size() == 1);
        Assert.assertTrue(ret.get(0).getId() == id);
    }

    @Test
    public void testQuery2() throws SQLException {
        Assert.assertNotNull(dao);

        String sql = "SELECT * FROM biz_pay_order";

        List<BizPayOrder> ret = dao.query(sql, BizPayOrder.class, (Object[]) null);

        Assert.assertTrue(CollectionUtils.isNotEmpty(ret));
        Assert.assertTrue(ret.size() == 2);
        System.out.println(JSON.toJSONString(ret));
    }

    @Test
    public void testQuery3() throws SQLException {
        Assert.assertNotNull(dao);

        String sql = "SELECT id FROM biz_pay_order";

        List<Long> ret = dao.query(sql, Long.class, (Object[]) null);

        Assert.assertTrue(CollectionUtils.isNotEmpty(ret));
        Assert.assertTrue(ret.size() == 2);
        System.out.println(JSON.toJSONString(ret));
    }

    @Test
    public void testInsert() throws SQLException {
        Assert.assertNotNull(dao);
        String sql = "INSERT INTO biz_pay_order (buyer_user_id,merchant_id,trade_no,trade_amount,trade_status,scene,goods_detail,gmt_update,gmt_create) VALUES(?,?,?,?,?,?,?,?,?)";
        Object[] params = { "7", "007", "tradeNo13948309283", 123, "trade_success", "bar_code", "mate10 pro 128g", new Date(), new Date() };
        Long key = dao.insert(sql, Long.class, params);
        System.out.println("key = " + key);
        Assert.assertTrue(key != null);
    }

    @Test
    public void testUpdate() throws SQLException {
        Assert.assertNotNull(dao);
        String sql = "UPDATE biz_pay_order SET trade_amount = ? WHERE id = ?";
        Object[] params = { 112233, 5 };
        int cnt = dao.update(sql, params);
        Assert.assertTrue(cnt == 1);
    }

    @Test
    public void testDelete() throws SQLException {
        Assert.assertNotNull(dao);
        String sql = "DELETE FROM biz_pay_order WHERE id = ?";
        int cnt = dao.update(sql, 4);
        Assert.assertTrue(cnt == 1);
    }

    @After
    public void close() throws SQLException {
        DbUtils.close(conn);
    }

}
