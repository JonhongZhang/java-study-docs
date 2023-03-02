
Redis 大 key 问题 & 问题分析 & 解决方案
Redis 大 key 问题 & 问题分析 & 解决方案
Redis





什么是 Redis 大 key
单个key 存储的 value 很大
hash， set，zset，list 结构中存储过多的元素
可能存在 Redis 大 key 的业务场景
1.配送范围特别大的门店
2.促销活动特别多的门店、商家等
3.高频用户下的订单列表


Redis 大 key 的危害
OPS 低也会导致内存占用多、流量大; 比如一次取走100K的数据，当OPS为1000时，就会产生100M/s的流量;

如果为 list,hash 等数据结构，大量的 elements 需要多次遍历，多次系统调用拷贝数据消耗时间;

主动删除、被动过期删除、数据迁移等，由于处理这一个KEY时间长，导致服务端发生阻塞;

如何找出 Redis 大 key
jimdb 管理端，拓扑Tab页，点击实例可以使用 Redis 大 key 扫描功能，该功能底层使用 scan 扫描所有 key，会影响实例性能, 选择在业务低峰进行;

redis 可使用 redis-cli 的 “--bigkeys” 选项查找大Key

如何解决 Redis 大 key 问题
对于需要整取 value 的 大 key, 可以尝试将对象分拆成几个 key-value， 使用 multiGet 获取值，这样分拆的意义在于分拆单次操作的压力，将操作压力平摊到多个实例中，降低对单个实例的IO影响;

对于每次需要取部分 value 的 大 key, 同样可以拆成几个 key-value，也可以将这些存储在一个 hash 中，每个 field 代表具体属性，使用 hget，hmget 来获取部分 value，使用 hset，hmset 来更新部分属性;

对于 value 中存储过多元素的 key, 同样可以将这部分元素拆分;

以 hash为例，正常的流程是：

现在可以固定一个桶数量，比如 1w,
每次存取的时候，先在本地计算field的hash值,
对 1w取模，确定 field落在哪个 key 上:

set，zset，list 做法类似
