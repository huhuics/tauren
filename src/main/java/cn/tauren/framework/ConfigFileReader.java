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

    /**
     * 获取用户自定义配置文件中包扫码路径
     * @param configFile   用户自定义包
     * @return             类扫描路径
     */
    public static String getScanPackage(String configFile) {
        return getProperties(configFile).getProperty(Constants.SCAN_PACKAGE);
    }

    private static Properties getProperties(String configFile) {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile);
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
