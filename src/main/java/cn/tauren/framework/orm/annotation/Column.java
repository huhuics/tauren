/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示数据库一列
 * @author HuHui
 * @version $Id: Column.java, v 0.1 2017年12月4日 下午3:46:39 HuHui Exp $
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    /** 列名 */
    String value() default "";

    /** 是否忽视该列 */
    boolean ignore() default false;

}
