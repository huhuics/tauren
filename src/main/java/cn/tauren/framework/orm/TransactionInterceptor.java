/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.orm;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import cn.tauren.framework.aop.api.ProxyInterceptor;

/**
 * 事务代理
 * <p>拦截一个类中所有需要被事务代理的方法</p>
 * @author HuHui
 * @version $Id: TransactionInterceptor.java, v 0.1 2017年12月5日 上午10:09:29 HuHui Exp $
 */
public class TransactionInterceptor extends ProxyInterceptor {

    @Override
    protected void before(Class<?> targetClass, Method method, Object[] args) {
        Connection conn = getConnection(targetClass, method, args);
        try {
            if (conn != null && conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void after(Class<?> targetClass, Method method, Object[] args) {
        Connection conn = getConnection(targetClass, method, args);
        try {
            if (conn != null && !conn.getAutoCommit()) {
                conn.commit();
            }
        } catch (SQLException e) {
            throw new RuntimeException(method.getName() + "方法事务提交失败", e);
        } finally {
            try {
                DbUtils.close(conn);
            } catch (SQLException e) {
            }
        }
    }

    @Override
    protected void exception(Class<?> targetClass, Method method, Object[] args, Throwable e) {
        Connection conn = getConnection(targetClass, method, args);
        try {
            if (conn != null && !conn.getAutoCommit()) {
                conn.rollback();
            }
        } catch (SQLException se) {
            throw new RuntimeException(method.getName() + "方法执行失败且事务回滚失败", se);
        } finally {
            try {
                DbUtils.close(conn);
            } catch (SQLException e1) {
            }
        }

        throw new RuntimeException(method.getName() + "方法执行失败", e);
    }

    /**
     * 通过方法参数获取数据库连接
     */
    private Connection getConnection(Class<?> targetClass, Method method, Object[] args) {
        Connection conn = null;
        for (Object arg : args) {
            if (arg instanceof Connection) {
                conn = (Connection) arg;
                break;
            }
        }

        return conn;
    }

}
