package com.sunveee.tauren.test.ioc;

import com.sunveee.tauren.ioc.BeanFactory;
import com.sunveee.tauren.ioc.impl.DefaultBeanFactory;

public class TestIoC {

    public static void main(String[] args) {
        BeanFactory beanFactory = new DefaultBeanFactory("com.sunveee.tauren.test.ioc");
        Student student = (Student) beanFactory.getBean("student");
        System.out.println(student.getName());
        System.out.println(student.getSchool().getName());

    }

}
