1. Netty 要完多深？
    
2. 不能不知道的netty信息？

3. netty 项目应用中的坑？

BIO, NIO, AIO分别时什么？
    BIO同步阻塞IO，NIO是同步非阻塞IO，AIO异步非阻塞IO;
    BIO： 服务器在实现时，一个链接一个线程；链接数小，相对固定
    NIO:  服务器实现模式，一个请求一个线程，客户端发送一个链接，注册到多路复用器上，多路复用器通过轮训链接的所有IO请求，有数据了，就启动一个线程来执行处理；链接数比较多，链接比较短，比较轻的场所，高频；
    AIO:  服务器实现模式，有效的请求，一个线程；  链接数多，链接长，数据量大。Linux支持文件的AIO，不支持网络的AIO；

    Linux 操作系统和驱动程序运行在内核空间，应用程序运行在用户空间；应用程序，无法操作内核空间的数据；

    Select、poll、 epoll 机制： linux 系统中一切皆文件；
    文件描述符：就是用于表述，指向这个文件的引用的概念；内核（kernel）利用文件描述符（file descriptor）来访问文件

    
4. 说说对Netty的理解？
    特点： 是一个高性能，异步事件驱动的IO框架; 本身提供UDP, TCP, Http的协议栈，非常有利于开发自己私有的协议栈； IO模型、线程模型；Netty 解决了jdk NIO epoll中存在cpu空轮训的bug,可能导致cpu100%; Netty 高性能由IO模型决定如何收发数据、线程模型决定如何处理数据； Netty IO 线程 - 聚合了多路复用器selector， 可以同时并发处理成千上万的客户端链接，因底层用的是epoll；因其使用reactor线程模型实现了事件驱动的处理模式，编写高性能的网络服务器，客户端面临的断连，重连，半包读写，零拷贝。 

   1. netty的线程模型是如何实现的？
   2. 客户端面临的断连，重连，半包读写？
   3. 零拷贝？

5. Netty与java NIO有什么不同，为什么不直接使用java NIO类库？
    NIO基本流程：
        NIO Server                                              Reactor Thread                          Iohandler
        1. 打开ServerSocketChannel
        2. 绑定监听地址，InetSocketAddress
                                                                3. 创建Selector 启动线程；
        4. 将ServerSocketChannel 注册到Selector监听
                    op_AccEPT                                   5. Selector 轮循就绪的key
                                                                6. handlerAccept()处理新的客户端接入        
                                                                                                        7. 设置新建客户端链接的Socket参数  
                                                                8. 向Selector注册监听读操作，SelectionKey.OP_READ
                                                                9. handleRead() 异步读请求消息到，ByteBuffer
                                                                                                        10. decode消息
                                                                11. 异步写ByteBuffer 到SocketChannel

     写半包是如何产生的： 如果发送区的缓冲区是满的，将会导致写半包
     如何处理： 注册一个监听器，监听写操作的位数，循环的写，直到整个包的消息都写到缓冲区中；

     1. 创建一个线程组
     2. 设置一下channel
     3. 设置业务处理的handler, 一些option的选项，绑定端口；
    netty能用来处理长消息，的粘包，读半包的问题，封装了许多功能，性能上大大提升，这就是为什么用netty而不直接用java nio。 

6. Netty核心组建有哪些组建？
        ChannelHandler以及其实现类； java 的NIO事件进栈道channel的时候会产生一系列的 事件 由ChannelHandler所对应的API来进行处理，
                                  ChannelHandler 主要有两个类型：  channelInboundHandler (表示数据入栈，指数据冲底层的java nio channel 到netty的channel) channelOutboundHandler （表示数据出栈，指通过netty的channel操作底层java nio channel,因为netty就是封装了java nio）， 我们通常需要自定一个handler,来继承相应的channelhandler的类，他也提供了一个ChannelInboundHandlerAdapter这样的类，我们通过重写相应的方法去实现我们的业务逻辑，比如去处理一些事件消息
        ChannelPipeline:   是一个Handler的集合，可以看作是我们在添加很多handler的时候都是向ChannelPipeline中添加的，它负责处理和拦截Inbound与Outbound事件的操作，是一个贯穿整个netty的链； 
        ChannelHandlerContext: 是事件处理中的上下文对象，ChannelPipeline链中是用来处理每一个节点，每一个节点的ChannelHandlerContext它包含着一个具体的事件处理器，同时ChannelHandlerContext也绑定对应的Pipeline和Channel的信息，方便对ChannelHandler进行调用；
        ChannelFuture: 表示在Channel中异步IO操作的结果，如多线程中的Future模式，它在netty中的所有的IO操作都是异步的，这是指的应用层，IO调用会直接返回，调用者不能立即获得结果，只是先拿到了一个通过ChannelFuture获取到IO操作处理状态的，如果有返回数据的时候才能获取到。；
        EventLoopGroup和NioEventLoopGroup: NioEventLoopGroup是它一个子类，这是一组EventLoop的抽象，Netty为了更好的利用多核的cpu资源，一般会有多个EventLoop同时进行工作，每个EventLoop维护着一个Selector实例，EventLoopGroup提供一个next接口，可用通过这个接口获得按一定规则获取其中一个EventLoop来进行任务的处理，netty服务端编程中，我们一般提供两个 EventLoopGroup, 如： BOSSEventLoop和WorkerEventLoop;

        ServerBootstrap与Bootstrap： ServerBootstrap 是服务端的启动助手，通过它可以完成服务器端的各种配置；
                                    Bootstrap 是netty 中的客户端的启动助手，通过它可以完成对服务端的各种配置；
                                    如： 在服务器端，通常我们需要调用，ServerBootstrap中的group方法，然后将前面创建的BOSSEventLoop和WorkerEventLoop; 放入线程组中
7. 说说netty的执行流程？
    ServerBootstrap             EventLoopGroup                  NioServerSocketChannel                  ChannelPipeline                  ChannelHandler
    1. 创建ServerBootstrap实例 
            ｜-2. 设置绑定Reactor线程池-> ｜-----3. 设置绑定服务端Channel-> ｜     
               |                        |---------------4. TCP链路创建时创建ChannelPipeline--------------------->｜
               |--------------------------------------------------------------------------- 5. 添加并设置Channel Handler------------------------->｜
               |----
                    |  6.绑定端口，并启动服务器。
               |<---                    
                                    ｜------
                                    ｜     ｜ 7. Selector 轮询()
                                    ｜<-----   
                                    ｜
                                    ｜---------------------------------8. 网络事件通知（）-----------------------｜
                                                                                                              ｜---9. 执行Netty系统和业务Handler Channel（）
                                                                                                                                --------------> ｜
                                                               
8. Netty 是如何做到高性能的？ Netty的线程模型是怎么样的？
    传输、内存、线程；
    1. 使用异步非阻塞的类库，基于reactor模式实现；主要是解决了传统异步阻塞IO模式下，一个服务器端，没有办法处理大量客户端的问题。Reactor 模式解决了用户数量增长导致资源分配不可控的问题。（线程）
    2. Tcp接收与发送缓冲区的时候，使用直接内存代替堆内存，这样可以避免内存之间的复制，提升IO读取与写入的性能 （运用零拷贝提升了IO读取和写入的性能）；（内存、传输）
    3. 支持通过内存池的方式，去循环利用ByteBuffer，避免了频繁创建于销毁ByteBuffer带来的性能损耗；（内存）
    4. 可配置的IO线程数、TCP参数，为不同的定制化场景，提供不同的定制化调优参数；（传输、线程）
    5. 使用环型数组的缓存区，实现无锁化的并发编程，替代了传统的线程安全容器，或者说是锁的方式；（线程，缓存）
    6. 合理的使用线程安全的容器，原子类，提升系统的并发处理能力；（线程）
    7. 关键资源的处理，使用单线程的串行化的方式，避免了多线程并发访问带来的锁竞争，和额外的cpu资源损耗的问题；（线程）
    8. 通过程序计数器及时的申请释放不再被引用的对象，细粒度的内存管理，降低了GC 的频率，减少频繁GC带来的延时增大，cpu的损耗。（内存）


    netty的线程模型：
        线程模型是指，线程管理的模型；好的线程模型，可以大大减少管理线程的成本；java并发编程。
        reactor线程模型：
                单线程模型 ： 对于小应用可以使用单线程模型，对于高负载，大并发的场景不适合。如果NIO线程处理成千上万的消息，不行；高并发导致相应超时，重发导致重新再次请求，最终导致节点故障。
                多线程模型 ： 最大的区别，有一组NIO线程来处理，监听服务端；一个NIO线程，
                主从多线程模型 
        ----netty线程模型由reactor线程模型改变

9. Netty 的零拷贝是怎么回事？
    "零拷贝" 是指计算机操作过程中CPU不需要为数据在内存之间的拷贝消耗资源。二它通常指计算机在网络上发送文件时，不需要将文件内容拷贝到用户空间 而直接在内核空间中传输到网络的方式。 
    用户态 -> 内核态 （上下文切换）
    DMA: 直接内存存取
    一共4次数据拷贝，4次上下文切换，占用了两次cpu资源。

    零拷贝的情况： 
        用户态 -> 内核态 （上下文切换） ---->  内核从磁盘获得数据达到kernel buffer 页缓存 ------> 内核空间的kernel buffer 页缓存的内存地址， 偏移量，信息描述符，追加到socket buffer； ----->  直接通过DMA 将数据从内存空间，传到协议引擎中。
        没有了cpu 的参与，

    netty中的应用层，零拷贝：
        FileRegion是依赖于java Nio FileChannel.transfer 实现零拷贝功能。
        CompositeByteBuf : 
        composite
        wrapperBuffer: 
        Siice: 
10. Netty 的内存池，对象池，是如何实现的？
    参考linux 系统中的slab分配算法，和Buddy分配算法；
    slab ： 将内存分配成大小不等的内存块，在用户线程请求的时候，根据请求的内存大小，来分配最为贴近的大小内存块，减少内存碎片的同时，避免内存的浪费；
    Buddy分配算法 ： 将内存进行等量分割，回收时进行合并，保证系统中有足够大的连续内存。
    tinySubPagePools 用于分配小于 512字节的内存；
    smallSubPagePools 用于分配大于512字节小于pageSize (8k)的内存；
    对于大于pageSize小于 chunk (16M) 的内存 在PoolChunkList的Chunk中进行分配。
    对于大于Chunk大小的内存在非池化分配。
    chunk 是以完全二叉树的数据结构，
11. 在实际项目中如何使用Netty, 使用过程中遇到过什么问题？

12. netty 是如何解决粘包，和拆包的问题？
    开发者不需要编解码，来处理这个问题，nettyt提供处理方式。
    tcp是以流的方式建立连接，TCP底层不知，数据的缓存；
    拆包： 完整TCP拆分成多个包发送，这叫做拆包，或者多个包拆分后，上一个包的一小部分，和下一个包的一小部分粘在一起。
    粘包： 将多个不同的包封装成一个的数据包，这叫做粘包；
    产生原因： 1. 应用程序些入字节的大小，大于缓冲去的大小，。
            2. 以太网帧的payload 大于MTO （payload 的最大长度 字节是1500），网络上发送的每个数据包-以太网帧的数据部分就是payload。每一帧发送的数据包 大于 1500 字节;
            3. 进行mss大小的tcp分段； mss - 网络中TCP报文负载的最大长度 1460 = mto - 20 -20；
    
    解决方案： 
    1. 每个报文的大小固定
    2. 在数据包尾增加回车换行符进行切割
    3. 将消息分成消息头和消息体。在消息头中包含表示消息总长度的字段，获得消息头就知道消息体的长度。

    netty提供的解决方案：
        LineBasedFrameDecoder 解码器：原理 一次性的遍历 betybuffer中可读的字节，判断是否有换行，或者回车换行，有 就一这个位置为结束的位置，从可读的索引 到 结束位置 区间的字节组成一行，以换行符为结束的标志。同时支持配置当行的最大长度，如果连续读取到最大长度还是没有换行符，则抛出异常，并且清除之前度的字节流
        DelimiterBasedFrameDecoder 分隔符解码器： 可以指定消息结束的分隔符，可以自动完成以分隔为结束的码流，进行解码，同时我们要将分隔符封装到Betybuffer, 不能是其他的。
        FixedLengthFrameDecoder 固定长度解码器： 按指定长度对消息进行解码，如果长度不够就需要进行补位操作，可能导致空间的浪费。无论一次接收多少数据报，按照固定长度进行解码，如果是半包消息，则会缓存这个半包消息， FixedLengthFrameDecoder会缓存半包并等待下一个包达到后进行拼包，直到读取到一个完整的包。
13. reactor线程中的NioEventLoop
    作用、职责：
        作为服务器端的Aecpted线程，来负责客户的端的请求介入；
        作为客户端的client线程，来负责注册监听链接操作为，用来判断异步链接的结果
        作为IO线程监听网络的读操作位，负责socketChannel 里面读取数据
        作为IO线程监听网络的写操作位，写入数据，发送给对方，如果发生写半包，则会自动注册监听写的事件，用于继续发送半包数据，直到数据全部发送完成；
        作为一个定时任务线程，可以执行定时任务，如链接空闲检查，和发送心跳；
        作为线程执行器，执行普通的任务线程；
        NIOEventLoop 从源码上看，其继承NioEventLoop继承SingeThreadEventExecutor, 绑定一个线程
        NioEventLoop设计原理： 串行化的设计避免线程竞争，系统运行当中，频繁的进行上下文切换，减少上下文切换的消耗，多线程并发执行某一个业务流程的时候，开发者还需要对线程安全进行实时考虑。
        NIOEventLoop聚合一个NIOEventLoopSelect， 当一个新的客户端链接进来，从NIOEventLoop线程组中 获取一个NIOEventLoop，当到达数组上线的时候，数组变为0， 类似一个环形数组，轮训的负载均衡。
        socket channel


14. Dubbo中是如何使用Netty的？