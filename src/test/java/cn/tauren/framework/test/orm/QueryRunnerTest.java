/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试{@link QueryRunner}
 * @author HuHui
 * @version $Id: QueryRunnerTest.java, v 0.1 2017年12月1日 下午3:49:32 HuHui Exp $
 */
public class QueryRunnerTest {

    private Connection conn;

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
    public void testQueryRunner() throws Exception {

        QueryRunner runner = new QueryRunner();
        String sql = "SELECT * FROM biz_pay_order where id = ?";

        ResultSetHandler<Object[]> rsh = new ResultSetHandler<Object[]>() {
            @Override
            public Object[] handle(ResultSet rs) throws SQLException {
                if (!rs.next()) {
                    return null;
                }

                ResultSetMetaData metaData = rs.getMetaData();
                int cols = metaData.getColumnCount();
                Object[] result = new Object[cols];
                for (int i = 0; i < cols; i++) {
                    result[i] = rs.getObject(i + 1);
                }

                return result;
            }
        };

        Object[] ret = runner.query(conn, sql, rsh, 3);

        for (int i = 0; i < ret.length; i++) {
            System.out.print(ret[i] + "  ");
        }

        DbUtils.close(conn);
    }

}
