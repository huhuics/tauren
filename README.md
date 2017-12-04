# Tauren Framework

学习master分支的Tauren项目，自行编写实现类似spring-framework的IoC和AOP功能，以加深对其的理解。

- IoC(Inversion of Control, 控制反转)

	IoC是应用于软件工程领域中的，在运行时被装配器对象来绑定耦合对象的一种编程技巧，对象之间耦合关系在编译时通常是未知的，这种实现方式可以将对象之间的关联关系的定义抽象化。而绑定的过程是通过DI(Dependency Injection, 依赖注入)实现的。

- DI(Dependency Injection, 依赖注入)

	DI是在编译阶段尚未知所需的功能是来自哪个的类的情况下，将其他对象所依赖的功能对象实例化的模式。这就需要一种机制用来激活相应的组件以提供特定的功能，所以依赖注入是控制反转的基础。

## 1. IoC

### 1.1 设计思路

IoC的输入为需要扫描的包路径*pkgName*，项目启动时，tauren会依次执行:

1. 初始化bean容器*beanContainer*
2. 扫描*pkgName*下的所有类，对所有被`@Bean`修饰的类，创建其实例并放入*beanContainer*
3. 初始化bean注入器*beanInjector* 
4. 扫描*beanContainer*的所有bean，对所有被`@Inject`修饰的属性，查找对应的bean进行注入

### 1.2 模块设计

- BeanFactory-bean工厂

	程序入口，接收唯一的输入*pkgName*，进行bean的创建以及依赖注入
	
	提供接口:
	- 获取所有bean
	- 通过beanName获取对应的bean
	
	持有:
	- *beanContainer*: bean容器，Map，存放所有beanName与bean的映射
	- ClassScanner的实例*classScanner*: 调用其接口获取所有被`@Bean`修饰的类
	- BeanInjector的实例*beanInjector*: 调用其接口进行依赖注入
	
- ClassScanner-类扫描器

	被BeanFactory实例化，接收*pkgName*值，扫描指定包路径下所有类文件并转换为Class对象，并筛选出符合条件的Class
	
	提供接口:
	- 获取所有类
	- 获取被指定注解修饰的类
	
	持有:
	- *classList*: class容器，List，存放指定包路径下所有class

- BeanInjector-bean注入器

	被BeanFactory实例化，提供依赖注入接口
	
	持有:
	- BeanFactory的实例*beanFactory*: 调用其接口获取所有bean以进行依赖注入
	
### 1.3 实现

[TestIoC](https://github.com/Sunxiai51/tauren/blob/wyy/src/test/java/com/sunveee/tauren/test/ioc/TestIoC.java)	
	