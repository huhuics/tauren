/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test;

import cn.tauren.framework.aop.annotation.Intercept;
import cn.tauren.framework.ioc.annotation.Bean;
import cn.tauren.framework.test.aop.LogProxy;

/**
 * 
 * @author HuHui
 * @version $Id: UserServiceImpl.java, v 0.1 2017年11月21日 下午7:27:48 HuHui Exp $
 */
@Bean
@Intercept(type = LogProxy.class)
public class UserServiceImpl implements UserService {

    @Override
    public String getUser(int id) {
        String str = "user's id is " + id;
        System.out.println(str);
        return str;
    }

}
