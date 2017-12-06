/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.orm;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 保存每个线程的数据库连接
 * @author HuHui
 * @version $Id: ConnectionHolder.java, v 0.1 2017年12月5日 下午8:29:14 HuHui Exp $
 */
public class ConnectionHolder {

    // TODO 硬编码，后续再用连接池代替
    private static final ThreadLocal<Connection> holder = new ThreadLocal<Connection>() {

                                                            @Override
                                                            protected Connection initialValue() {
                                                                String driver = "com.mysql.jdbc.Driver";

                                                                String url = "jdbc:mysql://168.33.131.164:3306/joice";

                                                                String user = "root";
                                                                String password = "huhui";

                                                                Connection conn = null;
                                                                try {
                                                                    Class.forName(driver);
                                                                    conn = DriverManager.getConnection(url, user, password);
                                                                } catch (Exception e) {
                                                                    throw new RuntimeException("创建数据库连接异常", e);
                                                                }

                                                                return conn;
                                                            }

                                                        };

    public static Connection get() {
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }

}
