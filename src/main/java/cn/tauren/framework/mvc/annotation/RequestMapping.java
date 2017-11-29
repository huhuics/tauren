/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.mvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.tauren.framework.enums.RequestMethod;
import cn.tauren.framework.enums.ResponseMethod;

/**
 * 作用在Controller的方法上,指定Request访问路径
 * @author HuHui
 * @version $Id: RequestMapping.java, v 0.1 2017年11月27日 上午10:06:12 HuHui Exp $
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    /**
     * request映射路径
     */
    String value();

    /**
     * http请求方式{@link RequestMethod}
     * <p>默认同时支持GET和POST请求方式</p>
     */
    RequestMethod[] requestMethod() default { RequestMethod.GET, RequestMethod.POST };

    /**
     * 返回方式{@link ResponseMethod}
     * <p>默认返回页面</p>
     */
    ResponseMethod responseMethod() default ResponseMethod.HTML;

}
