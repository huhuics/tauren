/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.tauren.framework.test;

/**
 * 
 * @author HuHui
 * @version $Id: Tiger.java, v 0.1 2017年11月21日 上午11:23:56 HuHui Exp $
 */
public class Tiger {

    private Cat cat = new Cat();

    public Tiger() {
        System.out.println("Tiger construction");
    }

    public static void main(String[] args) {
        Cat cat = new Cat();
        Tiger tiger = new Tiger();
    }

}
