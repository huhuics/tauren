/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

    /**
     * 获取主页
     */
    public static String getIndex() {
        return prop.getProperty(Constants.INDEX_FIELD, Constants.DEFAULT_INDEX);
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
