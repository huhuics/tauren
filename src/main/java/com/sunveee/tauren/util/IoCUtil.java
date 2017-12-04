package com.sunveee.tauren.util;

/**
 * IoC工具类
 * 
 * @author 51
 * @version $Id: IoCUtil.java, v 0.1 2017年12月1日 下午4:14:51 51 Exp $
 */
public final class IoCUtil {
    /**
     * 获取类的默认beanName
     * <p>默认规则为类名的第一个字符小写
     * 
     * @param clazz
     * @return
     */
    public static String getDefaultBeanNameByClass(Class<?> clazz) {
        AssertUtil.assertNotNull(clazz, "传入类为空");
        String className = clazz.getSimpleName();
        String result = className.substring(0, 1).toLowerCase();
        if (1 < className.length()) {
            result += className.substring(1, className.length());
        }
        return result;
    }

}
