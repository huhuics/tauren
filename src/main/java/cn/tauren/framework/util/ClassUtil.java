/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.util;

/**
 * 工具类
 * @author HuHui
 * @version $Id: ClassUtil.java, v 0.1 2017年11月16日 下午8:31:54 HuHui Exp $
 */
public final class ClassUtil {

    /**
     * 将类名转换为驼峰命名
     * @param className  原始类名
     * @return
     */
    public static String humpNaming(String className) {
        //截取第一个字符
        String firstChar = className.substring(0, 1);

        String remainChars = className.substring(1, className.length());

        return firstChar.toLowerCase() + remainChars;
    }

}
