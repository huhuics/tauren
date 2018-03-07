/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test;

import cn.tauren.framework.ioc.annotation.Bean;

/**
 * 
 * @author HuHui
 * @version $Id: LoginService.java, v 0.1 2017年11月23日 下午7:37:33 HuHui Exp $
 */
@Bean
public class LoginService implements AbstractService {

    /** 
     * @see cn.tauren.framework.test.AbstractService#service()
     */
    @Override
    public void service() {
        System.out.println("service from LoginService");
    }

}
