/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.aop.api;

import java.lang.reflect.Method;

/**
 * 连接点接口
 * @author HuHui
 * @version $Id: JoinPoint.java, v 0.1 2017年11月22日 下午2:48:31 HuHui Exp $
 */
public interface JoinPoint {

    /**
     * 返回目标类,即被代理的对象
     */
    Object getTarget();

    /**
     * 获取目标类的执行方法
     */
    Method getMethod();

    /**
     * 获取目标类执行方法的参数
     */
    Object[] getArgs();

    /**
     * 执行目标类的方法
     */
    Object proceed() throws Throwable;

}
