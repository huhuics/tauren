# Tauren Framework
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

# 一.简介
*_Tauren framework_* 是一个轻量级**Java Web**框架，它提供了类似*Spring framework*的*IoC*和*AOP*功能。tauren具有如下特点：

- 基于Servlet 3.0规范，可部署于Tomcat容器，或其它Servlet容器
- 客户端不使用XML配置，完全使用注解进行开发
- 应用基于面向服务编程（SOA），可进行分布式部署
- 灵活性高，易于扩展

# 二.约定
*Tauren*没有*Spring framework*那么强大，客户端在使用tauren过程中默认满足下列条件：

- 被*tauren*Ioc容器接管的类都有无参的构造方法且构造方法不是*私有*的
