/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test.aop;

import cn.tauren.framework.aop.api.ProxyInterceptor;
import cn.tauren.framework.ioc.annotation.Bean;

/**
 * 
 * @author HuHui
 * @version $Id: LogProxy.java, v 0.1 2017年11月22日 上午10:07:40 HuHui Exp $
 */
@Bean
public class LogProxy extends ProxyInterceptor {

    @Override
    protected void before() {
        System.out.println("log from before");
    }

    @Override
    protected void after() {
        System.out.println("log from after");
    }

}
