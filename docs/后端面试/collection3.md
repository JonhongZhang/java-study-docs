HashMap问题：

    考察内容涵盖：数据结构，多线程安全，倒插法，位运算，二进制炒作，扰动函数，hash碰撞；


    说说hashmap的理解：
        hashmap在1.8之前由数组 + 链表，每个数组也叫桶；

        在1.8以及之后，使用的是数组 + 链表 + 红黑树。
        

    hashmap数据插入的原理是：
            jdk1.7是头插法，jdk1.8是尾插法，


简单请求不会有跨域问； 同时简单请求在发送请求头时，不会带Orgin;
get、head 请求不会带Origin，其他的，以及存在跨越问题的请求会带。

1. hashMap的哈希函数是如何设计的？
    hash函数是先拿到key的hashcode, 是一个32位的int值，然后让hashcode的高16位与低16位进行异或操作，
    ```java
        static final int hash(Object key){
            int h;
            return (key == null ) ? 0 : (h = key.hashCode() )^(h >> 16) 
        }
    ```
    扰动函数： 位移16位，即32位的一半，高半区 与 低半区做异或混合原来32的高位与地位，原来高位的原始信息也参与到hash，高位与低位的混合减少哈希的冲突，32位更加的平均；
    jdk1.7 做了4次的扰动，jdk1.8做了1次

    1.8的插入方式优化：不会再出现，因扩容，出现死循环。           1.7位头插法，1.8位尾插法
    1.8的扩容不需要重新进行hash，也能确定元素的位置，效率更高。
        原因： 算法是通过原理---   （原哈希值）& （原数组大小 - 1） = （原哈希值）& （扩容数组大小 - 1）  （扩容前一致）
                                （原哈希值）& （原数组大小 - 1） = （（原哈希值）& （扩容数组大小 - 1）+ 16 ）   （比扩容前大原数组大小 --- 因为扩容是2倍扩容大小）（均为二进制）

    HashMap 是线程安全的吗，当然不是，
    1.7 会产生死循环，数据丢失，数据覆盖；
    1.8 会产生数据覆盖的问题；  当A线程判断index的位置位空后，正好挂起，B线程开始向节点写入数据，A线程恢复后， 开始写入数据，则会覆盖B线程写入的数据；
                            ++size的地方，也会导致多线程同时扩容的问题；
        如何解决线程不安全的问题：java 中 hashTable，Collections.synchronedMap, 以及ConcurrentedHashMap： HashTable 是通过直接在方法上加synchronized关键字锁住整个数组，力度最大，效率不行；Collections.synchronedMap是使用集合工具的内部类，通过传入map封装出一个synchonizedMap对象，内部定义一个对象锁，方法内部通过对象锁实现； 以及ConcurrentedHashMap，使用的是分断锁，降低了锁的力度，提高了并发度。

    1.7 与 1.8 的ConcurrentedHashMap实现方式有哪些不同：    
        1.7 中的ConcurrentedHashMap是通过， ReetrantLock + Segment + HashEntry实现的；通过减少锁力度，来削弱多线程与竞争锁；对于多线程的访问来说，最常用的使用方式是对get与Add方法进行 “同步”，入hashTable、 Vocter、Collections.synchronedMap的synchronized；每当对集合进行操作的时候总是获得集合的锁，  因此在事实上没有两个线程的可以做到真正的并发， 因为任何线程在执行这些方法的时候，总要等前面的线程结束，然后在高并发中，激烈的竞争锁，会影响系统的吞吐量。中的ConcurrentedHashMap是通过，是将整个hashMap分成若干个段（即segment），这些segment都是一个子hashMap，如果需要在ConcurrentedHashMap中添加一个元素，则不需要锁住整个hashMap，是先通过，hashCode获得，添加的元素是应该被存放如何哪个segment中，让后对该段（segment）进行加锁。 定位一个元素，需要进行两次hash；
        1.8 之后取消了分段锁的结果， 取消了segment，使用数组、链表、红黑树的结构；使用cas，加上同步锁的方式保证线程安全，效率接近hashMap。

        cas 是如何保证线程安全的。cas，比较与交换， 无锁； CAS有三个操作数，内存值V，旧的预期值A，要修改的值B。当且仅当，预期值A与内存值V，相同时，将内存值V修改为B,否则什么都不做。
        CAS，无线程频烦调度，线程之间的影响，相对于锁的方式影响小，性能比锁好；
        ABA问题： 假设一个变量A，修改为B，然后又修改为A, 实际上已经改过了，，但CAS可能无法感知，造成不合理的值修改操作；

        JDK, Atomic包中提供了AtomicStampedReference解决ABA问题，该对象提供了一个compareAndSet放法，检测当前引用是否等于预期引用，当前标志是否等于预期标志，如果全部相等，这一原子的方式，将该引用，该标志的值，设置为给定值的一个更新值。

    1.7的hashMap死循环会导致哪些结果。

