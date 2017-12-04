package com.sunveee.tauren.test.ioc;

import com.sunveee.tauren.ioc.annotation.Bean;
import com.sunveee.tauren.ioc.annotation.Inject;
import com.sunveee.tauren.ioc.annotation.InstanceMethod;

@Bean
public class Student {
    private String name;

    @Inject(beanName = "MiddleSchool_14")
    private School school;

    public Student() {
    }

    private Student(String name) {
        this.name = name;
    }

    @InstanceMethod(args = { "LiLei" })
    public static Student getInstance(String name) {
        return new Student(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

}
