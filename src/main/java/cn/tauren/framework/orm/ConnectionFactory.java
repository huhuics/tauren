/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.orm;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import cn.tauren.framework.ConfigFileReader;

/**
 * {@link Connection}工厂类
 * @author HuHui
 * @version $Id: ConnectionFactory.java, v 0.1 2017年12月5日 下午9:24:49 HuHui Exp $
 */
public class ConnectionFactory extends BasePooledObjectFactory<Connection> {

    @Override
    public Connection create() {
        return doCreate();
    }

    @Override
    public PooledObject<Connection> wrap(Connection obj) {
        return new DefaultPooledObject<Connection>(obj);
    }

    @Override
    public void destroyObject(PooledObject<Connection> p) throws Exception {
        Connection conn = p.getObject();
        DbUtils.close(conn);
    }

    private Connection doCreate() {
        String driver = ConfigFileReader.getDbDriver();
        String url = ConfigFileReader.getDbUrl();

        String user = ConfigFileReader.getDbUser();
        String password = ConfigFileReader.getDbPassword();

        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException("创建数据库连接异常", e);
        }

        return conn;
    }

}
