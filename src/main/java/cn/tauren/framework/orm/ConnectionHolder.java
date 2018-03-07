/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.orm;

import java.sql.Connection;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import cn.tauren.framework.ConfigFileReader;

/**
 * 保存每个线程的数据库连接
 * @author HuHui
 * @version $Id: ConnectionHolder.java, v 0.1 2017年12月5日 下午8:29:14 HuHui Exp $
 */
public class ConnectionHolder {

    private static GenericObjectPool<Connection> pool;

    private static GenericObjectPoolConfig       poolConfig;

    static {
        poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(ConfigFileReader.getMaxTotal());
        poolConfig.setMaxIdle(ConfigFileReader.getMaxIdle());
        poolConfig.setMinIdle(ConfigFileReader.getMinIdle());

        pool = new GenericObjectPool<Connection>(new ConnectionFactory(), poolConfig);
    }

    private static final ThreadLocal<Connection> holder = new ThreadLocal<Connection>();

    public static Connection get() {
        Connection conn = holder.get();
        if (conn == null) {
            try {
                conn = pool.borrowObject();
            } catch (Exception e) {
                throw new RuntimeException("获取数据库连接失败", e);
            }
            holder.set(conn);
        }
        return conn;
    }

    public static void clear() {
        Connection conn = holder.get();
        if (conn != null) {
            pool.returnObject(conn);
        }
        holder.remove();
    }

}
