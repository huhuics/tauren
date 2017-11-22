/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.aop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang3.ObjectUtils.Null;

/**
 * Aop拦截
 * @author HuHui
 * @version $Id: Aspect.java, v 0.1 2017年11月21日 下午9:00:40 HuHui Exp $
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Intercept {

    String name() default "";

    Class<?> type() default Null.class;

}
