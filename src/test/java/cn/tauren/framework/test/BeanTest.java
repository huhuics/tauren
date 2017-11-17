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
public class BeanTest implements BeanParaTest {

    public BeanTest() {
        System.out.println("BeanTest的构造方法");
    }

}
