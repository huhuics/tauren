/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.orm;

import java.sql.SQLException;

import cn.tauren.framework.ioc.annotation.Bean;
import cn.tauren.framework.orm.BaseDao;
import cn.tauren.framework.orm.annotation.Transaction;

/**
 * 
 * @author HuHui
 * @version $Id: BizPayOrderDao.java, v 0.1 2017年12月4日 下午5:16:04 HuHui Exp $
 */
@Bean
public class BizPayOrderDao extends BaseDao {

    @Transaction
    public int updateWithTran(String sql, Object... params) throws SQLException {
        int cnt = super.update(sql, params);
        return cnt;
    }

    public int updateWithoutTran(String sql, Object... params) throws SQLException {
        int cnt = super.update(sql, params);
        return cnt;
    }

}
