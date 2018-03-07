/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * 读取配置文件
 * @author HuHui
 * @version $Id: ConfigFileReader.java, v 0.1 2017年11月23日 下午2:40:37 HuHui Exp $
 */
public class ConfigFileReader {

    private static Properties prop;

    static {
        prop = getProperties();
    }

    /**
     * 获取用户自定义配置文件中包扫码路径
     * @param configFile   用户自定义配置文件
     * @return             类扫描路径
     */
    public static String getScanPackage() {
        return prop.getProperty(Constants.SCAN_PACKAGE_FIELD);
    }

    /**
     * 获取JSP文件路径
     * 如果不存在则返回默认路径
     */
    public static String getJspPath() {
        return prop.getProperty(Constants.JSP_PATH_FIELD, Constants.DEFAULT_JSP_PATH_VALUE);
    }

    /********************************获取数据库相关配置*************************************/
    public static String getDbDriver() {
        return prop.getProperty(Constants.DB_DRIVER_FIELD);
    }

    public static String getDbUrl() {
        return prop.getProperty(Constants.DB_URL_FIELD);
    }

    public static String getDbUser() {
        return prop.getProperty(Constants.DB_USER_FIELD);
    }

    public static String getDbPassword() {
        return prop.getProperty(Constants.DB_PASSWORD_FIELD);
    }

    public static int getMaxTotal() {
        return NumberUtils.toInt(prop.getProperty(Constants.DB_MAX_TOTAL_FIELD), Constants.DEFAULT_MAX_TOTAL_VALUE);
    }

    public static int getMaxIdle() {
        return NumberUtils.toInt(prop.getProperty(Constants.DB_MAX_IDLE_FIELD), Constants.DEFAULT_MAX_IDLE_VALUE);
    }

    public static int getMinIdle() {
        return NumberUtils.toInt(prop.getProperty(Constants.DB_MIN_IDLE_FIELD), Constants.DEFAULT_MIN_IDLE_VALUE);
    }

    /***********end***********/

    /**
     * 获取主页
     */
    public static String getIndex() {
        return prop.getProperty(Constants.INDEX_FIELD, Constants.DEFAULT_INDEX_VALUE);
    }

    private static Properties getProperties() {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(Constants.DEFAULT_CONFIG_NAME);
        Properties prop = new Properties();
        try {
            prop.load(is);
        } catch (IOException e) {
            throw new RuntimeException("读取配置文件失败", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return prop;
    }

}
