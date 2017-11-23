# Tauren Framework
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

# 目录
- [一. 简介](#一-简介)
- [二. 约定](#二-约定)
- [三. 实现方式](#三-实现方式)
    - [3.1 IoC(Inversion of Control)](#31-iocinversion-of-control)
    - [3.2 循环依赖问题](#32-循环依赖问题)
- [四. 使用方式](#四-使用方式)
    - [4.1 IoC使用](#41-ioc使用)
   


# 一. 简介
*_Tauren framework_* 是一个轻量级**Java Web**框架，它提供了类似*Spring framework*的*IoC*和*AOP*功能。tauren具有如下特点：

- 基于Servlet 3.0规范，可部署于Tomcat容器，或其它Servlet容器
- 客户端不使用XML配置，完全使用注解进行开发
- 应用基于面向服务编程（SOA），可进行分布式部署
- 灵活性高，易于扩展

# 二. 约定
*Tauren*没有*Spring framework*那么强大，客户端在使用tauren过程中默认满足下列条件：

- 被*tauren*Ioc容器接管的类都有无参的构造方法且构造方法不是*私有*的

- *@Bean*只应用于类，应用于接口或抽象类将无效

# 三. 实现方式
## 3.1 IoC(Inversion of Control)
![](https://github.com/huhuics/Accumulate/blob/master/image/Tauren-IoC.jpg?raw=true)

IoC实现的类图如上图所示。下面分别说明各个接口和类的功能。

- **ClassScanner**

    [ClassScanner](https://github.com/huhuics/tauren/blob/master/src/main/java/cn/tauren/framework/ioc/api/ClassScanner.java)是类扫描器，提供了三种方法，都是根据条件，递归扫描客户端文件夹所有的类。但是*getClassesByAnnotation*和*getClassesBySuper*方法不会返回**接口**和**抽象类**
    
- **BeanFactory**

    [Bean工厂](https://github.com/huhuics/tauren/blob/master/src/main/java/cn/tauren/framework/ioc/api/BeanFactory.java)中首先通过*ClassScanner*获取所有带有 *@Bean* 注解的类，被 *@Bean* 注解的类说明需要被IoC容器接管。通过`Class.newInstance()`方法实例化类，并分别放入*nameContainer*和*typeContainer*这两个Map中。IoC容器在具体实现起来是通过Map来实现的。
    
- **BeanInjector**

    [Bean注入器](https://github.com/huhuics/tauren/blob/master/src/main/java/cn/tauren/framework/ioc/api/BeanInjector.java)完成的工作是遍历*BeanFactory*中实例化的类，再依次遍历各个类的字段，找出被 *@Inject* 注解的字段，默认通过名称的方式注入对象；如果通过名称注入失败，则改用通过类型注入的方式。
    
    如果一个接口有多个实现类，则在使用 *@Bean* 标注时必须填入类的名称，在注入的地方 *@Inject* 也必须使用名称，即这种情况必须使用**名称注入方式**
    
## 3.2 循环依赖问题
### 3.2.1 什么是循环依赖？
循环依赖就是循环引用，两个或多个Bean相互之间持有对方的引用，比如A类引用B类，B类引用C类，C类又引用A类，它们最终反映为一个环。
    
### 3.2.2 Spirng是如何解决循环依赖？
Spring容器循环依赖包括构造器循环依赖和setter循环依赖。
    
**构造器循环依赖**表示通过构造器注入构成循环依赖，此依赖是无法解决的，只能抛出 *BeanCurrentlyInCreationException* 异常表示循环依赖。在创建A类时，构造器需要B类，那将去创建B类，在创建B类时又发现需要C类，那将去创建C类，最终在创建C类又发现需要A类，从而形成一个环，无法创建。
    
Spring容器将每一个正在创建的Bean标识符放在一个“当前创建Bean池”中，Bean标识符在Bean创建过程中一直保存在这个池中（Map保存），因此在创建Bean过程中发现自己已经在这个池中时将抛出 *BeanCurrentlyInCreationException* 异常表示循环依赖；而对于创建完毕的Bean则从池中清除。
    
**setter循环依赖**表示通过setter注入方式构成的循环依赖。对于setter循环依赖，spring是通过先无参构造方法创建一个实例提前把A的引用暴露出来并缓存，并且只能解决单例作用域的Bean循环依赖，而对于`prototype`作用域的Bean，由于Spring不缓存，无法提前暴露一个创建中的Bean，故不能解决。

### 3.2.3 Tauren是如何解决循环依赖

对于构造器循环依赖，tauren同样无法解决，因为这本身就是无解的。对于setter循环依赖，由于tauren只有注解模式，没有xml模式，且在注入字段表示的对象时，该字段所在的类已经被实例化了，因此setter循环依赖在tauren中并不存在。

## 3.2 AOP(Aspect Oriented Programming)
*Tauren*使用*CGLib*来实现代理类的生成

*Tauren*的AOP中暂时不支持对类的批量拦截，目前只做到了对指定类所有方法的拦截。首先定义了拦截模板类[ProxyInterceptor](https://github.com/huhuics/tauren/blob/master/src/main/java/cn/tauren/framework/aop/api/ProxyInterceptor.java)，这个类定了模板方法，有前置增强before()，后置增强after()及异常增强exception()。客户端的拦截器需要继承此类并选择性覆盖这三个方法。*ProxyInterceptor*同时负责代理类的创建。

*BeanFactory*中在实例化Bean时，如果发现某个类被[@Intercept](https://github.com/huhuics/tauren/blob/master/src/main/java/cn/tauren/framework/aop/annotation/Intercept.java)修饰，则说明该类将会被框架拦截并增强，*@Intercept* 指定了增强类的名称或类型，二者不能同时为空。BeanFactory通过名称或者类型找到了目标类的增强类，通过创建代理类实例，取代容器中目标类的实例，这样从BeanFactory中取出的实例就是增强之后的对象。

# 四. 使用方式
## 4.1 IoC使用
```java
@Bean
public class UserService {
    //do something
}
```

为`Login`注入`UserService`
```java
@Bean
public class Login {

    @Inject
    private UserService userService;
    
    //do something
}
```

## 4.2 AOP使用
以用户服务类`UserServiceImpl`为例，需要当这个类中所有方法增加日志功能，先写好`LogProxy`，并继承`ProxyInterceptor`：
```java
@Bean
public class LogProxy extends ProxyInterceptor {
    @Override
    protected void before() {
        System.out.println("log from before");
    }

    @Override
    protected void after() {
        System.out.println("log from after");
    }
}
```

然后在`UserServiceImpl`上打上`@Intercept`注解:
```java
@Bean
@Intercept(type = LogProxy.class)
public class UserServiceImpl implements UserService {
    // do something
}
```

然后正常调用`UserService`中的方法，原方法即得到增强。


*代码持续更新中*


