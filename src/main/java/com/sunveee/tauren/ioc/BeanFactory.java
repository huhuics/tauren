package com.sunveee.tauren.ioc;

import java.util.Map;

/**
 * Bean工厂
 * 
 * @author 51
 * @version $Id: BeanFactory.java, v 0.1 2017年12月1日 上午10:28:15 51 Exp $
 */
public interface BeanFactory {
    Map<String, Object> getBeans();

    Object getBean(String beanName);
}
