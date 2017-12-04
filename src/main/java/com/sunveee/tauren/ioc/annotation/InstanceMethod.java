package com.sunveee.tauren.ioc.annotation;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target({ METHOD, CONSTRUCTOR })
public @interface InstanceMethod {
    /**
     * 参数值
     * <p>这里通过注解传入的参数只支持字符串类型，考虑使用某些方式传入更多类型
     * 
     * @return
     */
    String[] args() default {};

}
