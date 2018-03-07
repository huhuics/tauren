/**
 * Joice
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test;

import cn.tauren.framework.ioc.annotation.Bean;
import cn.tauren.framework.ioc.annotation.Inject;

/**
 * 
 * @author HuHui
 * @version $Id: BeanTest.java, v 0.1 2017年11月16日 下午12:36:21 HuHui Exp $
 */
@Bean
public class StudentServiceImpl implements StudentService {

    @Inject
    private TeacherService tService;

    @Override
    public String study() {
        String str = "学生：鲁智深" + ", age：32";
        System.out.println(str);
        tService.tech();
        return str;
    }
}
