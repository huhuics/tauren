package com.sunveee.tauren.test.ioc;

import com.sunveee.tauren.ioc.annotation.Bean;

@Bean
public class BeanA {
    private String name;

    public BeanA() {
        this.name = "beanA";
    }

    public void printName() {
        System.out.println("My name is " + name);
    }

}
