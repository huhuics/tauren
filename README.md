# Tauren Framework
学习master分支的Tauren项目，自行编写实现类似spring-framework的IoC和AOP功能，以加深对其的理解。

- IoC(Inversion of Control, 控制反转)
IoC是应用于软件工程领域中的，在运行时被装配器对象来绑定耦合对象的一种编程技巧，对象之间耦合关系在编译时通常是未知的，这种实现方式可以将对象之间的关联关系的定义抽象化。而绑定的过程是通过DI(Dependency Injection, 依赖注入)实现的。

- DI(Dependency Injection, 依赖注入)
DI是在编译阶段尚未知所需的功能是来自哪个的类的情况下，将其他对象所依赖的功能对象实例化的模式。这就需要一种机制用来激活相应的组件以提供特定的功能，所以依赖注入是控制反转的基础。

## 设计思路
IoC的输入为需要扫描的包路径*pkgPath*，项目启动时，tauren会依次执行：
1. 初始化bean容器*beanContainer*
2. 扫描*pkgPath*下的所有类，放入*classList*，对所有被`@Bean`修饰的类，创建其实例并放入*beanContainer*
	- 如果该类中有被`@InstanceConstructor`修饰的构造方法，调用该构造方法创建实例，否则调用无参构造方法创建实例，放入*beanContainer*，如果没有无参构造方法，抛出相应的异常
3. 扫描*beanContainer*的所有bean，进行依赖注入
	- 如果该类下有被`@Inject`修饰的属性，查找*beanContainer*，存在则注入该bean，不存在则抛出响应的异常

## 模块设计
- ClassScanner-类扫描器
	- 接收*pkgPath*值，并持有*classList*
	- 初始化*classList*，根据路径扫描`.class`文件并将对应的Class对象放入*classList*
	- 根据类型获取类列表
	- 根据注解获取类列表
- BeanFactory-bean工厂
	- 持有*beanContainer*，ClassScanner实例*classScanner*
	- 初始化*beanContainer*，创建所有被`@Bean`修饰的类的实例
	- 通过名称获取bean
	- 通过类型获取bean列表
- BeanInjector-bean注入器