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
 * 事务注解
 * <p>标注于类，表示该类所有方法执行事务操作</p>
 * <p>标注于方法，表示该方法执行事务操作</p>
 * @author HuHui
 * @version $Id: Transaction.java, v 0.1 2017年12月5日 上午11:31:01 HuHui Exp $
 */
@Documented
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Transaction {

}
