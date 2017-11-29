/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.util;

/**
 * Action工具类
 * @author HuHui
 * @version $Id: ActionUtil.java, v 0.1 2017年11月29日 上午9:21:31 HuHui Exp $
 */
public class ActionUtil {

    private static final String SPLIT = ":";

    public static String getActionKey(String method, String uri) {
        StringBuilder sBuilder = new StringBuilder();
        return sBuilder.append(method).append(SPLIT).append(uri).toString();
    }
}
