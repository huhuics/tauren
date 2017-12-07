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
 * <p>子类DAO还可以通过父类的<code>QueryRunner</code>自行扩展数据库操作方法</p>
 * @author HuHui
 * @version $Id: BaseDao.java, v 0.1 2017年12月4日 下午3:46:24 HuHui Exp $
 */
public abstract class BaseDao {

    protected Connection  connection = ConnectionHolder.get();

    protected QueryRunner runner     = new QueryRunner();

    /**
     * 通用查询方法
     * @param sql     查询的SQL语句
     * @param type    返回对象的类型，可以是对象，也可以是String，或者是基本类型的包装类
     * @param params  SQL参数，如果没有参数传null
     * @return        查询结果集合
     * @throws SQLException
     */
    public <T> List<T> query(String sql, Class<T> type, Object... params) throws SQLException {
        ResultSetHandler<List<T>> rsh = ResultSetHandlerFactory.createQueryHandler(type);
        return runner.query(connection, sql, rsh, params);
    }

    /**
     * 无参的通用查询方法
     * @see query(String, Class<T>, Object...)
     */
    public <T> List<T> query(String sql, Class<T> type) throws SQLException {
        return query(sql, type, (Object[]) null);
    }

    /**
     * 数据插入方法
     * 泛型T表示主键类型
     * @param sql        被执行的SQL语句
     * @param params     SLQ参数，如果没有参数传null
     * @return           插入数据后的主键
     * @throws SQLException 
     */
    public <T> T insert(String sql, Class<T> type, Object... params) throws SQLException {
        ResultSetHandler<T> rsh = ResultSetHandlerFactory.createInsertHandler();
        return runner.insert(connection, sql, rsh, params);
    }

    /**
     * 通用的数据修改方法
     * @param sql       被执行的SQL语句
     * @param params    SQL参数，如果没有参数传null
     * @return          被修改的记录数
     * @throws SQLException
     */
    public int update(String sql, Object... params) throws SQLException {
        return runner.update(connection, sql, params);
    }

    /**
     * 单个参数的数据修改方法
     * @see update(String, Object...)
     */
    public int update(String sql, Object param) throws SQLException {
        return runner.update(connection, sql, param);
    }

    /**
     * 无参的数据修改方法
     * @see update(String, Object...)
     */
    public int update(String sql) throws SQLException {
        return runner.update(connection, sql);
    }

}
