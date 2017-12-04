package com.sunveee.tauren.test.ioc;

import com.sunveee.tauren.ioc.annotation.Bean;
import com.sunveee.tauren.ioc.annotation.Inject;

@Bean(name = "abc")
public class BeanB {
    @Inject
    private BeanA beanA;

    public void printName() {
        beanA.printName();
    }

}
