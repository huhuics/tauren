/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.ioc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cn.tauren.framework.ioc.annotation.Bean;
import cn.tauren.framework.ioc.annotation.Inject;
import cn.tauren.framework.util.AssertUtil;
import cn.tauren.framework.util.ClassUtil;

/**
 * Bean的注入器
 * <p>通过遍历各个类中被{@link Inject}注释的字段,将类的实例注入</p>
 * <p>先按照名称注入,如果失败再按照类型注入</p>
 * @author HuHui
 * @version $Id: BeanInjector.java, v 0.1 2017年11月16日 下午3:43:41 HuHui Exp $
 */
public class BeanInjector {

    /** list中的Obj都是带有{@link Bean}注解 */
    private final List<Object>          objs;

    private final Map<String, Object>   nameObjMap;

    private final Map<Class<?>, Object> typeObjMap;

    private final ClassScanner          scanner;

    public BeanInjector(BeanContainer container, ClassScanner scanner) {
        this.nameObjMap = container.getNameContainer();
        this.typeObjMap = container.getTypeContainer();
        this.objs = new ArrayList<Object>(this.nameObjMap.values());
        this.scanner = scanner;
    }

    public void inject() {
        for (Object obj : objs) {
            Class<?> clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();

            //遍历所有字段
            for (Field field : fields) {
                if (field.isAnnotationPresent(Inject.class)) {
                    field.setAccessible(true);

                    Object fieldVal = getInjectedObject(field);

                    try {
                        field.set(obj, fieldVal);
                    } catch (Exception e) {
                        throw new RuntimeException("注入对象发生异常 ", e);
                    }
                }
            }
        }
    }

    private Object getInjectedObject(Field field) {

        //1.按名称注入
        String className = getClassName(field.getName(), field.getAnnotation(Inject.class));
        Object fieldVal = nameObjMap.get(className);
        if (fieldVal != null) {
            return fieldVal;
        }

        //2.按类型注入
        Class<?> type = field.getType();
        List<Class<?>> classesBySuper = scanner.getClassesBySuper(type);
        if (CollectionUtils.isNotEmpty(classesBySuper)) {
            AssertUtil.assertTrue(classesBySuper.size() <= 1, "该接口有多个实现类,请使用名称注入方式");
            return typeObjMap.get(classesBySuper.get(0));
        }

        return null;
    }

    /**
     * 获取被注入类的name
     */
    private String getClassName(String fieldName, Inject injAnno) {
        String name = injAnno.name();
        if (StringUtils.isNotBlank(name)) {
            fieldName = name;
        }
        return ClassUtil.humpNaming(fieldName);
    }

}
