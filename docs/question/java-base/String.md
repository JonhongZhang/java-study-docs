### String 的最大长度？
String 提供来一个length方法，返回值为int类型， 而int的取值上线为2^31-1，所以理论上String 的最大值为2^31-1。

### 达到String理论长度需要多大的内存
String内部使用一个char 的数组来维护字符序列的，一个char占用两个字节，，如果String的最大长度是2^31-1的话，那么最大的字符串占用的存储空间约等于4G， 也就是说我们需要有4GB大小的JVM运行内存才能运行。

### JVM 中String 一般都存在哪些区域
字符串在jvm中存储分两种情况， 一种是String对象，存储在JVM堆栈中； 一种是字符串常量，存储在常量池里面

### 什么情况下会存在常量池中
String s = ""； 通过字面量进行申明的

### 常量池中的字符串最大长度是2^31-1吗
不是， 因为常量池对于String 有另外的限制
java中的UTF_8的unicode 字符串在常量池中以 CONSTANT_Utf8类型表示： 
    CONSTANT_Utf8_info {
     u1 tag;
     u2 length;
     u1 bytes[length];
    }
length 在这里是代表字符串的长度
length的类型是u2， u2是无符号的16位整数
也就是说最大长度可以做到2^16-1 即 65535
不过javac 编辑器做了限制，需要length《 65535 所以字符串常量池中的最大长度为 65535 - 1 = 65534