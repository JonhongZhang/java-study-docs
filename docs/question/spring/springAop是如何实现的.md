### Spring是如何实现的


### Spring Aop和AspectJ 有哪些区别

    在AOP 的实现中，有ApectJ， SpringAop， aspectwerkz， JBoss4.0
    对AOP进行了实现，其中，AspectJ， 是通过编译器进行实现，SpringAop 是通过动态代理实现
    
    AspectJ 它会在编译期进行增强， 用AspectJ开发的编译器来编译项目。通过AspectJ 的编译器在编译过程中将增强代码，
    编译成字节码织入到切面
    
    而spring Aop则是采用动态代理的方式来实现Aop
    当一个Bean有实现接口时，那么就会采用JDK动态代理来生成该接口的代理对象， 
    如果一个Bean没有实现接口，那么就会采用CGLIB来生成当前类的一个代理对象，代理对象的作用就是代理原本的Bean对象， 代理对象在执行某个方法时，会
    在该方法的基础上增加一些切面逻辑，使得我们可以利用AOP来实现
    如： 登陆校验， 权限控制， 日志记录等一些统一的功能