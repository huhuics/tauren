/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework;

/**
 * 一些常量
 * @author HuHui
 * @version $Id: Constants.java, v 0.1 2017年11月16日 上午11:24:26 HuHui Exp $
 */
public interface Constants {

    /** 默认配置文件名 */
    static final String DEFAULT_CONFIG_NAME    = "tauren.config";

    /************************配置文件字段名称***************************/
    /** 工程扫码包路径字段 */
    static final String SCAN_PACKAGE_FIELD     = "scan.package";

    /** jsp目录字段 */
    static final String JSP_PATH_FIELD         = "jsp.path";

    /** 主页字段 */
    static final String INDEX_FIELD            = "index";

    /** 数据库驱动字段 */
    static final String DB_DRIVER              = "db.driver";

    /** 数据库URL字段 */
    static final String DB_URL                 = "db.url";

    /** 数据库用户名字段 */
    static final String DB_USER                = "db.user";

    /** 数据库密码字段 */
    static final String DB_PASSWORD            = "db.password";

    /** 最大连接数 */
    static final String DB_MAX_TOTAL           = "db.max.total";

    /** 最大空闲数 */
    static final String DB_MAX_IDLE            = "db.max.idle";

    /** 最小空闲数 */
    static final String DB_MIN_IDLE            = "db.min.idle";
    /********end********/

    /************************配置文件字段默认值***************************/
    /** 默认jsp目录值 */
    static final String DEFAULT_JSP_PATH_VALUE = "/WEB-INF/jsp/";

    /** 默认主页值 */
    static final String DEFAULT_INDEX          = "index.jsp";

    /** 默认最大连接数 */
    static final int    DEFAULT_MAX_TOTAL      = 8;

    /** 默认最大空闲数 */
    static final int    DEFAULT_MAX_IDLE       = 8;

    /** 默认最小空闲数 */
    static final int    DEFAULT_MIN_IDLE       = 0;
    /********end********/

    /************************Tauren框架使用常量***************************/
    static final String FILE_DOT               = ".";

    /** 字符编码 */
    static final String UTF8                   = "UTF-8";

    /** jsp文件后缀 */
    static final String SUFFIX_JSP             = ".jsp";

    /** 主页 */
    static final String INDEX                  = ConfigFileReader.getIndex();

    /** jsp目录值 */
    static final String JSP_PATH_VALUE         = ConfigFileReader.getJspPath();
    /********end********/

}
