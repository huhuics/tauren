/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test;

import cn.tauren.framework.ioc.annotation.Bean;

/**
 * 
 * @author HuHui
 * @version $Id: BeanTest.java, v 0.1 2017年11月16日 下午12:36:21 HuHui Exp $
 */
@Bean
public class StudentServiceImpl implements StudentService {

    @Override
    public String display() {
        String str = "学生：鲁智深" + ", age：32";
        System.out.println(str);
        return str;
    }
}
