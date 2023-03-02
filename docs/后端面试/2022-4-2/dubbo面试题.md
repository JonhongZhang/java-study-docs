1. dubbo默认使用哪些协议？
    Dubbo支持dubbo、rmi、hessian、http、webservice、thrift、redis等多种协议，但是Dubbo官网是推荐我们使用Dubbo协议的，默认也是用的dubbo协议。
    dubbo协议有：
            缺省协议，使用基于mina1.1.7+hessian3.2.1的tbremoting交互。
            连接个数：单连接
            连接方式：长连接
            传输方式：NIO异步传输
            序列化：Hessian二进制序列化
            适用范围：传入传出参数数据包较小（建议小于100K），消费者比提供者个数多，单一消费者无法压满提供者，尽量不要用dubbo协议传输大文件或超大字符串。
            适用场景：常规远程服务方法调用

        1、dubbo默认采用dubbo协议，dubbo协议采用单一长连接和NIO异步通讯，适合于小数据量大并发的服务调用，以及服务消费者机器数远大于服务提供者机器数的情况
        2、他不适合传送大数据量的服务，比如传文件，传视频等，除非请求量很低。
        配置如下：

        ```xml
            <dubbo:protocol name="dubbo" port="20880" />
            <dubbo:protocol name=“dubbo” port=“9090” server=“netty” client=“netty” codec=“dubbo”
            serialization=“hessian2” charset=“UTF-8” threadpool=“fixed” threads=“100” queues=“0” iothreads=“9”
            buffer=“8192” accepts=“1000” payload=“8388608” />
        ```

        3、Dubbo协议缺省每服务每提供者每消费者使用单一长连接，如果数据量较大，可以使用多个连接。
        ```xml
        <dubbo:protocol name="dubbo" connections="2" />
        ```

        4、为防止被大量连接撑挂，可在服务提供方限制大接收连接数，以实现服务提供方自我保护
        ```xml
        <dubbo:protocol name="dubbo" accepts="1000" />
        ```
    rmi协议
        Java标准的远程调用协议。
        连接个数：多连接
        连接方式：短连接
        传输协议：TCP
        传输方式：同步传输
        序列化：Java标准二进制序列化
        适用范围：传入传出参数数据包大小混合，消费者与提供者个数差不多，可传文件。
        适用场景：常规远程服务方法调用，与原生RMI服务互操作

        RMI协议采用JDK标准的java.rmi.*实现，采用阻塞式短连接和JDK标准序列化方式 。
    hessian协议
        基于Hessian的远程调用协议。
        连接个数：多连接
        连接方式：短连接
        传输协议：HTTP
        传输方式：同步传输
        序列化：表单序列化
        适用范围：传入传出参数数据包大小混合，提供者比消费者个数多，可用浏览器查看，可用表单或URL传入参数，暂不支持传文件。
        适用场景：需同时给应用程序和浏览器JS使用的服务。
        1、Hessian协议用于集成Hessian的服务，Hessian底层采用Http通讯，采用Servlet暴露服务，Dubbo缺省内嵌Jetty作为服务器实现。
        2、Hessian是Caucho开源的一个RPC框架：http://hessian.caucho.com，其通讯效率高于WebService和Java自带的序列化。
    http协议
        基于http表单的远程调用协议。参见：[HTTP协议使用说明]
        连接个数：多连接
        连接方式：短连接
        传输协议：HTTP
        传输方式：同步传输
        序列化：表单序列化
        适用范围：传入传出参数数据包大小混合，提供者比消费者个数多，可用浏览器查看，可用表单或URL传入参数，暂不支持传文件。
        适用场景：需同时给应用程序和浏览器JS使用的服务。
    Webservice协议
        基于WebService的远程调用协议。 
        连接个数：多连接 
        连接方式：短连接 
        传输协议：HTTP 
        传输方式：同步传输 
        序列化：SOAP文本序列化 
        适用场景：系统集成，跨语言调用


 2. 序列化
    序列化是将一个对象变成一个二进制流就是序列化， 反序列化是将二进制流转换成对象。
    为什么要序列化？
            1. 减小内存空间和网络传输的带宽
            2. 分布式的可扩展性
            3. 通用性，接口可共用
        Dubbo序列化支持java、compactedjava、nativejava、fastjson、dubbo、fst、hessian2、kryo，其中默认hessian2。其中java、compactedjava、nativejava属于原生java的序列化。

        dubbo序列化：阿里尚未开发成熟的高效java序列化实现，阿里不建议在生产环境使用它。

        hessian2序列化：hessian是一种跨语言的高效二进制序列化方式。但这里实际不是原生的hessian2序列化，而是阿里修改过的，它是dubbo RPC默认启用的序列化方式。
        json序列化：目前有两种实现，一种是采用的阿里的fastjson库，另一种是采用dubbo中自己实现的简单json库，但其实现都不是特别成熟，而且json这种文本序列化性能一般不如上面两种二进制序列化。
        java序列化：主要是采用JDK自带的Java序列化实现，性能很不理想。

        dubbo序列化主要由Serialization(序列化策略)、DataInput(反序列化，二进制->对象)、DataOutput（序列化，对象->二进制流） 来进行数据的序列化与反序列化。其关系类图为：
        https://www.cnblogs.com/jameszheng/p/10271341.html

3. dubbo是什么？
    dubbo是⼀个分布式服务框架，提供⾼性能和透明化的RPC远程服务调⽤⽅案，以及SOA服务治理方案。说白了其实dubbo就是一个远程调用的分布式框架。

4. dubbo的核心服务是什么？

5. 特性 描述
    透明远程调用 就像调用本地方法一样调用远程方法；只需简单配置，没有任何API侵入；
    负载均衡机制 Client端LB，可在内网替代F5等硬件负载均衡器；
    容错重试机制 服务Mock数据，重试次数、超时机制等；
    自动注册发现 注册中心基于接口名查询服务提 供者的IP地址，并且能够平滑添加或删除服务提供者；
    性能日志监控 Monitor统计服务的调用次调和调用时间的监控中心；
    服务治理中心 路由规则，动态配置，服务降级，访问控制，权重调整，负载均衡，等手动配置。
    自动治理中心 无，比如：熔断限流机制、自动权重调整等；

6. 服务提供者暴露一个服务的详细过程：
    如果你仔细观察dubbo的启动日志你会发现，dubbo的provider启动主要是以下几个过程
        1.首先provider启动时，先把想要提供的服务暴露在本地。

        2.然后再把服务暴露到远程。

        3.启动netty服务，建立长连接。

        4.连接到注册中心zk上。

        5.然后监控zk上的消费服务。

7. 服务消费者消费一个服务的详细过程
    首先ReferenceConfig类的init方法调用Protocol的refer方法生成Invoker实例。接下来把Invoker转为客户端需要的接口。

8. 下面来看本地暴露于远程暴露的区别：
     - 本地暴露是暴露在本机JVM中,调用本地服务不需要网络通信.
     - 远程暴露是将ip,端口等信息暴露给远程客户端,调用远程服务时需要网络通信.


9. 什么情况下适用dubbo协议，什么时候适用rmi协议？
    dubbo支持dubbo、rmi、hessian、http、webservice、thrift、redis等多种协议，但是dubbo协议是官网推荐使用的，dubbo 缺省协议是dubbo协议，采用单一长连接和 NIO 异步通讯，适合于小数据量大并发的服务调用，以及服务消费者机器数远大于服务提供者机器数的情况。反之，Dubbo 缺省协议不适合传送大数据量的服务，比如传文件，传视频等，除非请求量很低。RMI协议采用阻塞式(同步)短连接和 JDK 标准序列化方式。适用范围：传入传出参数数据包大小混合，消费者与提供者个数差不多，可传文件。

10. Dubbo主要的配置项有哪些，作用是什么？
        provider配置
    ```xml
    <!-- 提供方应用信息，用于计算依赖关系 -->
    <dubbo:application name="hello-world-app" />

    <!-- 使用multicast广播注册中心暴露服务地址 -->
    <dubbo:registry address="multicast://224.5.6.7:1234" />

    <!-- 用dubbo协议在20880端口暴露服务 -->
    <dubbo:protocol name="dubbo" port="20880" />

    <!-- 声明需要暴露的服务接口 -->
    <dubbo:service interface="com.alibaba.dubbo.demo.DemoService" ref="demoService" />

    <!-- 和本地bean一样实现服务 -->
    <bean id="demoService" class="com.alibaba.dubbo.demo.provider.DemoServiceImpl" />
    ```

        consumer配置
    ```xml
    <!-- 消费方应用名，用于计算依赖关系，不是匹配条件，不要与提供方一样 -->
    <dubbo:application name="consumer-of-helloworld-app" />

    <!-- 使用multicast广播注册中心暴露发现服务地址 -->
    <dubbo:registry address="multicast://224.5.6.7:1234" />

    <!-- 生成远程服务代理，可以和本地bean一样使用demoService -->
    <dubbo:reference id="demoService" interface="com.alibaba.dubbo.demo.DemoService" />
    ```

11. dubbo有几种容错机制
    什么是容错机制？容错机制指的是某中系统控制在一定范围的一种允许或包容犯错情况的发生，举个简单的例子，我们在电脑上运行一个程序，有时候会出现无响应的情况，然后系统回弹出一个提示框让我们选择，是立即结束还是继续等待，然后根据我们的选择执行对应的操作，这就是“容错”。

   在分布式架构下，网络，硬件，应用都可以发生故障，由于各个服务之间可能存在依赖关系，如果一条链路中的某一个节点出现故障，将会导致雪崩效应。为了减少某一个节点故障的影响范围，所以我们才需要去构建容错服务，来优雅的处理这种中断的响应结果

        1.failsafe 失败安全，可以认为是把错误吞掉（记录日志）

        2.failover(默认)  重试其他服务器；retries(2)重试的次数，默认为2次

        3.failback   失败后自动恢复

        4.forking forks. 设置并行数

        5.Broadcast 广播，任意一台报错，则执行的方法报错，通过cluster方式，配置制定的容错方案

        


