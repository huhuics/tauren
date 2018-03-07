/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.mvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作用在类上,被{@link Controller}修饰的类说明该类会被Tauren的IoC容器接管
 * 并且会被当做是一个Controller
 * @author HuHui
 * @version $Id: Controller.java, v 0.1 2017年11月27日 上午10:00:10 HuHui Exp $
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {

    /**
     * Contrller映射路径
     */
    String value() default "";

}
