/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.orm;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import cn.tauren.framework.orm.annotation.Column;
import cn.tauren.framework.util.ClassUtil;

/**
 * <code>ResultSetHandler</code>简单工厂类
 * @author HuHui
 * @version $Id: ResultSetHandlerFactory.java, v 0.1 2017年12月4日 下午4:18:38 HuHui Exp $
 */
public class ResultSetHandlerFactory {

    /**
     * 创建查询ResultSetHandler
     */
    public static <T> ResultSetHandler<List<T>> createQueryHandler(final Class<T> type) {
        ResultSetHandler<List<T>> rsh = new ResultSetHandler<List<T>>() {

            @Override
            @SuppressWarnings("unchecked")
            public List<T> handle(ResultSet rs) throws SQLException {
                List<T> rets = new ArrayList<T>();

                while (rs.next()) {
                    try {
                        if (ClassUtil.isPrimitive(type)) {
                            T object = (T) rs.getObject(1);
                            rets.add(object);
                        } else {
                            Field[] fields = type.getDeclaredFields();
                            if (ArrayUtils.isEmpty(fields)) {
                                break;
                            }

                            Object obj = Class.forName(type.getName()).newInstance();

                            //遍历每个字段
                            for (Field field : fields) {
                                if (isIgnore(field)) {
                                    continue;
                                }
                                field.setAccessible(true);
                                field.set(obj, rs.getObject(getFieldName(field)));
                            }
                            rets.add((T) obj);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("创建实体对象失败", e);
                    }
                }

                return rets;
            }
        };

        return rsh;
    }

    /**
     * 创建插入类型ResultSetHandler
     */
    public static <T> ResultSetHandler<T> createInsertHandler() {
        ResultSetHandler<T> rsh = new ResultSetHandler<T>() {

            @Override
            @SuppressWarnings("unchecked")
            public T handle(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return (T) rs.getObject(1);
                }
                return null;
            }
        };

        return rsh;
    }

    /**
     * 获取字段名
     * <p>如果字段被{@link Column}修饰，则使用用户自定义名称</p>
     * <p>否则返回字段名的下划线表示，例如：userName ==> user_name</p>
     */
    private static String getFieldName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column anno = field.getAnnotation(Column.class);
            if (StringUtils.isNotBlank(anno.value())) {
                return anno.value();
            }
        }

        return ClassUtil.underline(field.getName());
    }

    /**
     * 是否忽略该字段
     */
    private static boolean isIgnore(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column anno = field.getAnnotation(Column.class);
            return anno.ignore();
        }

        return false;
    }

}
