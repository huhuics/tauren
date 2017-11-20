/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test;

import cn.tauren.framework.ioc.annotation.Bean;

/**
 * 
 * @author HuHui
 * @version $Id: ClassroomService.java, v 0.1 2017年11月20日 下午7:44:42 HuHui Exp $
 */
@Bean
public class ClassroomService {

    public void clean() {
        System.out.println("ClassroomService.clean()");
    }

}
