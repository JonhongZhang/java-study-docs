### bean 的生命周期
    


### bean的实例化 
    通过反射执行类的构造方法从而得到一个java对象，而这过程就是Bean的实例化


### bean的初始化
    得到java对象后，会进行依赖注入，依赖注入之后就会进行初始化类， 而bean 的初始化就是
    调用前面创建出来的java对象中的特定方法，比如java对象实现了initializingBean 接口，那么初始化的
    时候就会执行java对象的afterPropertiesSet(),  Spring 只会执行这个方法，并不关心方法做了什么，我们可以
    在这个方法中去对某个属性进行验证，或者直接给某个属性赋值都是可以的，反正Bean的初始化就是执行afterProperties()， 方法或者执行init-method指定的方法，
    比如： 

    初始化之前先进行属性填充
    执行initializBean方法 -----> 该方法中会执行初始化前方法，以及初始化invokeInitMethods(beanName, wrappedBean, mbd)方法进行初始化， 在执行初始化后方法
    通常的初始化方法为invokeInitMethods, 会判断是否实现initializBean接口，如果实现了则进入首先判断是否开启安全管理器，（默认没有），没有之后，执行((InitializingBean) bean).afterPropertiesSet();方法
    该过程结束会，invokeInitMethods方法中还会判断是否有指定初始化方法，并按条件执行。