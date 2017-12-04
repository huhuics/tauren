package com.sunveee.tauren.test.ioc;

import com.sunveee.tauren.ioc.annotation.Bean;
import com.sunveee.tauren.ioc.annotation.InstanceMethod;

@Bean(name = "MiddleSchool_14")
public class School {
    private String name;

    @InstanceMethod(args = { "No.14 Middle School" })
    public School(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
