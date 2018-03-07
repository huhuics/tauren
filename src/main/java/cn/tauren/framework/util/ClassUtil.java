/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.commons.lang3.ClassUtils;

/**
 * 工具类
 * @author HuHui
 * @version $Id: ClassUtil.java, v 0.1 2017年11月16日 下午8:31:54 HuHui Exp $
 */
public final class ClassUtil {

    /**
     * 将名转换为驼峰命名
     * @param name  原始名称
     * @return
     */
    public static String humpNaming(String name) {
        AssertUtil.assertNotNull(name, "参数为空");
        //截取第一个字符
        String firstChar = name.substring(0, 1);

        String remainChars = name.substring(1, name.length());

        return firstChar.toLowerCase() + remainChars;
    }

    /**
     * 将驼峰命名转换为下划线命名
     * @param name
     * @return
     */
    public static String underline(String name) {
        AssertUtil.assertNotNull(name, "参数为空");
        StringBuilder builder = new StringBuilder();
        char[] charArray = name.toCharArray();
        for (char c : charArray) {
            if (Character.isUpperCase(c)) {
                builder.append('_').append(Character.toLowerCase(c));
            } else {
                builder.append(c);
            }
        }

        return builder.toString();
    }

    /**
     * 判断指定类是否是基本类型
     * 这里定义的基本类型包括以下几种：
     * Boolean, Byte, Character, Short, Integer, Long, Double, Float, String
     * @param clazz 要判断的类型
     * @return      true表示是基本类型
     */
    public static boolean isPrimitive(Class<?> clazz) {
        return ClassUtils.isPrimitiveWrapper(clazz) || ClassUtils.isAssignable(clazz, String.class);
    }

    /**
     * 判断一个类是否是接口或抽象类
     * @param clazz
     * @return
     */
    public static boolean isInterfaceOrAbstract(Class<?> clazz) {
        return clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers());
    }

    /**
     * 调用类的方法
     * @param instance
     * @param method
     * @param args
     * @return
     */
    public static Object invoke(Object instance, Method method, Object[] args) {
        try {
            return method.invoke(instance, args);
        } catch (Exception e) {
            throw new RuntimeException("调用方法异常", e);
        }
    }

}
