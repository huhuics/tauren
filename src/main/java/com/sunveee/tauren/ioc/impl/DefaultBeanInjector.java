package com.sunveee.tauren.ioc.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.sunveee.tauren.ioc.BeanFactory;
import com.sunveee.tauren.ioc.BeanInjector;
import com.sunveee.tauren.ioc.annotation.Inject;
import com.sunveee.tauren.util.AssertUtil;

/**
 * Bean注入器实现
 * 
 * @author 51
 * @version $Id: DefaultBeanInjector.java, v 0.1 2017年12月1日 下午3:22:58 51 Exp $
 */
public class DefaultBeanInjector implements BeanInjector {

    private final BeanFactory beanFactory;

    public DefaultBeanInjector(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void inject() {
        Map<String, Object> beans = beanFactory.getBeans();
        Iterator<String> beansKeyInterator = beans.keySet().iterator();
        while (beansKeyInterator.hasNext()) {
            String _beanName = beansKeyInterator.next();
            Object _bean = beans.get(_beanName);
            Field[] fields = _bean.getClass().getDeclaredFields(); // 反射获取该bean所有属性列表

            // 遍历属性
            for (Field _field : fields) {
                if (_field.isAnnotationPresent(Inject.class)) { // 如果属性被@Inject注解修饰
                    _field.setAccessible(true);
                    try {
                        _field.set(_bean, _getInjectBean(_field, beans)); // 注入
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        throw new RuntimeException("注入bean时发生异常", e);
                    }
                }
            }

        }

    }

    private Object _getInjectBean(Field field, Map<String, Object> beans) {
        Object result;
        String annoBeanName = field.getAnnotation(Inject.class).beanName();

        if (StringUtils.isNotBlank(annoBeanName)) { // @Inject注解配置了beanName，根据其查找对应的bean
            AssertUtil.assertTrue(beans.containsKey(annoBeanName), "根据配置的beanName" + annoBeanName + "未找到对应的bean");
            result = beans.get(annoBeanName);

        } else { // @Inject注解未配置beanName，根据其类型查找对应的bean
            List<Object> injectableBeanList = new ArrayList<Object>(); // 存放符合类型的bean
            Class<?> fieldType = field.getType();

            // 遍历beans
            Iterator<Object> beansInterator = beans.values().iterator();
            while (beansInterator.hasNext()) {
                Object _bean = beansInterator.next();
                // 如果某个bean可注入，则添加至可注入列表
                if (fieldType.isAssignableFrom(_bean.getClass())) {
                    injectableBeanList.add(_bean);
                }
            }
            AssertUtil.assertNotBlank(injectableBeanList, "未找到对应类型" + fieldType.getCanonicalName() + "的bean");
            AssertUtil.assertTrue(injectableBeanList.size() == 1, "对应类型" + fieldType.getCanonicalName() + "的bean不唯一,请指定一个具体的bean注入");
            result = injectableBeanList.get(0);
        }

        AssertUtil.assertNotNull(result, "对应的bean为null");
        return result;

    }

}
