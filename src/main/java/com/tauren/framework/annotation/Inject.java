/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package com.tauren.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>修饰变量,用于注入类</p>
 * <p>默认是按类型注入,如果不成功再按名称注入</p>
 * @author HuHui
 * @version $Id: Inject.java, v 0.1 2017年11月14日 下午7:50:19 HuHui Exp $
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {

    /**
     * 如果指定了name,则按照名称注入,否则优先按类型注入
     */
    String name() default "";

}
