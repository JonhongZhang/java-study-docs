### BeanFactory 与FactoryBean的区别
1. BeanFactory是所有Spring bean容器的顶级接口，它为Spring提供了容器定义的一套规范， 并提供类像getBean这样的方法从容器中获取指定的Bean实例
2. BeanFactory在产生Bean的同时，还提供了解决bean之间依赖注入的能力，也就是所谓的DI（控制反转）
3. FactoryBean是一个工厂bean， 是一个接口，动态去生成某一类型的Bean的一个实例，  也就是说我们可以自定义一个bean,并且加载到IOC容器中，
   FactoryBean中有一个getObject()方法，其目的就是实现动态构造bean的一个过程。spring cloud 中OpenFeign就是使用FactoryBean来实现的

### Spring中有两个ID相同的Bean 会报错吗？
    通过xml配置时， 在解析xml, 转化为BeanDefinition的阶段， 当时在两个不同的xml配置文件中可以有两个相同的配置文件bean，IOC在容器加载时，默认会将多个id相同的bean进行覆盖；
    在spring 3.X 之后， @Configuration注解申明一个配置类，然后使用@bean这个注解去实现Bean 的声明，这种方式取代xml配置，
    这时在同一个配置中声明， 两个名字相同的bean, 这时spring IOC 在解析的时候，只会注册第一个声明的Bean的一个实例， 后面重复bean的实例则不会进行注入。
    
### 把bean注入到IOC容器中有哪些方法？
1. xml 方式声明bean的定， spring 容器启动时加载并解析这个xml， 把bean装载到IOC容器中。
2. 通过注解的方式， @CompontScan注解来扫描， 声明来@Controller、@Service、@Response、@Component的类。
3. @Configuration注解声明配置类， 并使用@Bean 注解实现Bean的定义， 这种方式其实是xml配置方式的一种演变，是Spring进入无配置化时代的标志
4. 使用@Import注解， 导入配置类或者普通的Bean
5. 使用FactoryBean工厂Bean, 动态构建一个Bean实例， SpringCloud中的OpenFeign里面的动态代理实例就是通过FactoryBean来实现的。
6. 实现ImportBeanDefinition-register接口，可以动态注入bean实例。 这个在Spring Boot里面的启动注解有用到。
7. 实现ImportSelector接口，动态批量注入配置类或者Bean对象， 这个在Springboot 中的自动装配机制里面有用到。

### spring 中那里有用到设计模式， 用的分别是什么设计模式
   

### 封装starter的好处
   1. 灵活性： 我们可以直接通过添加jar包依赖的方式添加功能，而不需要复制代码
   2. 继承，封装，多态： 一个好的starter, 能够很好的体现出封装，继承，多态的三大特性
   3. starter封装大多数是对公共属性进行的封装。

### spring bean中的作用域
作用域起内核为bean 的生命周期
1. singleton：单例模式，在整个Spring IoC容器中，使用singleton定义的Bean将只有一个实例
2. prototype：原型模式，每次通过容器的getBean方法获取prototype定义的Bean时，都将产生一个新的Bean实例
3. request : 针对每一次http请求都会创建一个新的bean
4. session ： 以session会话为维度，同一个session共享同一个bean实例不同的session产生不同的实例
5. globalSession ： 针对全局session维度共享同一个Bean 实例

### spring 中controller是单例 还是多例
默认单例
只要不在单例中定义的非静态变量，就不会有线程安全问题 （单例是不安全的，会导致属性重复使用）
1. 不要在controller中定义成员变量
2. 万一必须需要，则通过注解@Scope("prototype")，将起设置为多例模式

### java 中sleep 与wait的区别
1. sleep 属于 Thread 类中的， wait()  方法，则属于 object类中的
2. sleep() 方法导致类程序暂停执行指定时间，让出CPU， 但是它的监控状态依然保持，当指定时间到了后又会自动恢复运行状态， 在调用sleep的过程中，线程不会释放对象锁
3. 而当调用wait()方法的时候， 线程会放弃对象锁，进入等待此对象的等待锁定池，只有针对此对象调用notify()方法后， 本线程才会进入对象锁定池准备获取对象锁进入运行状态
4. sleep用Thread调用，在非同步状态下就可以调用， wait用同步监视器调用，必须在同名代码中调用

### synchronized 和 ReentrantLock 的区别


两者的相同点


### 单例bean

### 什么情况下会导致spring 事物失效
1. 方法内的自调用（同一类中方法调用）： spring事物是基于AOP的，只要使用代理对象调用某个方法时， spring 事物才能生效，而在一个方法中调用使用this.xxx()调用方法时，this并不是代理对象，所以会导致事物失效
   1. 把调用方法拆分到另一个bean中
   2. 自己注入自己
   3. AopContext.currentProxy + @EnableAspectJAutoProxy(exposeProxy = true)
2. 方法没有被public修饰 
   1. 在开发过程中，如果@Transactional事务注解添加在不是public修饰的方法上，这个时候，Spring的事务就会失效。
3. 类没有被spring 托管
   1. 如果事务方法所在的类没有加载到Spring IoC容器中，也就是说，事务方法所在的类没有被Spring管理，从而导致Spring无法实现代理，所以，Spring事务也会失效。
4. 不正确的异常捕获
   1. 如果事务方法抛出异常被 catch 处理了，导致 @Transactional 无法回滚而导致事务失效。
5. propagation事务传播行为配置错误
   1. 如果内部方法的事务传播类型为不支持事务的传播类型，那么，内部方法的事务在Spring中会失效。
6. rollbackFor参数设置错误
   1. 如果在@Transactional注解中rollbackFor参数标注了错误的异常类型，那么，Spring事务的回滚就无法识别，导致事务回滚失效。
7. 没有配置事务管理器
   1. 即使在代码中使用了Spring的事务管理的功能，但是在项目中，如果没有配置Spring的事务管理器，Spring的事务也不会生效。
8. 数据库本身不支持事务
   1. Spring事务生效的前提是所连接的数据库要支持事务，如果底层的数据库都不支持事务，那么，Spring的事务肯定会失效。例如，如果使用的数据库为MySQL，并且选用了MyISAM存储引擎，则Spring的事务就会失效。