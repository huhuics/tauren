/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.orm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

/**
 * <p><code>BaseDao</code>被设计用来继承的，子类DAO继承BaseDao后即可使用基本的增、删、改、查方法</p>
 * <p>子类DAO还可以自行扩展数据库操作方法</p>
 * @author HuHui
 * @version $Id: BaseDao.java, v 0.1 2017年12月4日 下午3:46:24 HuHui Exp $
 */
public abstract class BaseDao {

    private Connection    connection;

    protected QueryRunner runner = new QueryRunner();

    public BaseDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * 通用查询接口
     * @param sql     查询的SQL语句
     * @param type    返回对象的类型，可以是对象，也可以是String，或者是基本类型的包装类
     * @param params  SQL参数，如果没有参数传null
     * @return        查询结果集合
     * @throws SQLException
     */
    public <T> List<T> query(String sql, Class<T> type, Object... params) throws SQLException {
        ResultSetHandler<List<T>> rsh = ResultSetHandlerFactory.createHandler(type);

        return runner.query(connection, sql, rsh, params);
    }

}
