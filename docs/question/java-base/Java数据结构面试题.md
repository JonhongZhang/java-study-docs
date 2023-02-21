### concurrentHashMap
    Q. 为什么concurrentHashMap中的key, value 不能为空
    
    A. 1. 避免二义性

```
1、 HashMap计算key的hash值时调用单独的方法，在该方法中会判断key是否为null，如果是则返回0；而Hashtable中则直接调用key的hashCode()方法，因此如果key为null，则抛出空指针异常。

2、 HashMap将键值对添加进数组时，不会主动判断value是否为null；而Hashtable则首先判断value是否为null。

3、以上原因主要是由于Hashtable继承自Dictionary，而HashMap继承自AbstractMap。

4、虽然ConcurrentHashMap也继承自AbstractMap，但是其也过滤掉了key或value为null的键值对。
```
