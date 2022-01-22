# 一. Redis基本功能

## 1.1 下载安装redis
docker pull redis
docker run -itd --name redis-test -p 6379:6379 redis 
docker image inspect redis:latest|grep -i version 、
docker exec -it redis-test /bin/bash

注意 ： 新入门,没有 redis.conf , 使用文件映射

docker run -p 6379:6379 --name redis01 -v /etc/redis/redis.conf:/etc/redis/redis.conf -v /etc/redis/data:/data -d redis redis-server /etc/redis/redis.conf --appendonly yes

## 1.2 性能测试 : 

可以使用自带的命令 :  redis-benchmark
redis-benchmark -n 100000 -c 32 -t SET,GET,INCR,HSET,LPUSH,MSET -q
输出结果： 
SET: 39463.30 requests per second 
GET: 39872.41 requests per second 
INCR: 39603.96 requests per second 
LPUSH: 38819.88 requests per second 
HSET: 39323.63 requests per second 
MSET (10 keys): 37202.38 requests per second

更详细的可以参考：redis-benchmark -n 100000 -c 32


## 1.3 Redis 基本结构


### 1.3.1 字符串(string) - 简单来说包含三种 : int , string , byte[]
字符串类型是redis中最为基础的数据类型,在redis中是二进制安全的 , 可以保存任何类型 ,redis中字符串类型的value最多可以容纳512M
操作 : 
set/get/getset/del/exists/append 
incr/decr/incrby/decrby
注意：
(1)、字符串append：会使用更多的内存  , 假设 , 如果一个字符串呗append,name他会被频繁append,所以会额外申请一部分内存 , 所以建议拼接操作,如果不频繁,可以设置新的值
(2)、整数共享：如何能使用整数，就尽量使用整数,redis会对于多个引用的整数,进行共享使用限制 : 如果限制了redis内存 , 或者过期策略LRU  , 都可能会导致整数共享失效.  
(3)、整数精度问题：redis大概能保证16~，17-18位的大整数就会丢失精确

## 1.3.2 散列 (hash) - map
Redis中的Hash类型可以看成具有String key 和String value的map容器。所以该类型 非常适合于存储对象的信息。如Username、password和age。如果Hash中包含少量 的字段，那么该类型的数据也将仅占用很少的磁盘空间
实际上基本和java中的hashmap方法对应的 : 
hset/hget/hmset/hmget/hgetall/hdel/hincrby
hexists/hlen/hkeys/hvals

## 1.3.3 列表
列表（list）~ java的LinkedList 
在Redis中，List类型是按照插入顺序排序的字符串链表。和数据结构中的普通链表 一 样，我们可以在其头部(Left)和尾部(Right)添加新的元素。在插入时，如果该键并不存 在，Redis将为该键创建一个新的链表。与此相反，如果链表中所有的元素均被移除， 那么该键也将会被从数据库中删除。 lpush/rpush/lrange/lpop/rpop
插入的顺序和查询时显示的顺序相反 , 查询中 , 从上到下是弹出的顺序,从下到上是插入的顺序

## 1.3.4 集合 - java 的set
在redis中，可以将Set类型看作是没有排序的字符集合，和List类型一样，我们也可以 在该类型的数值上执行添加、删除和判断某一元素是否存在等操作。这些操作的时间复杂度为O(1),即常量时间内完成依次操作。 和List类型不同的是，Set集合中不允许出现重复的元素。 sadd/srem/smembers/sismember ~ set.add, remove, contains, 
sdiff/sinter/sunion ~  集合求差集，求交集，求并集

## 1.3.5 有序集合（sorted set）
sortedset和set极为相似，他们都是字符串的集合，都不允许重复的成员出现在一个 set中。他们之间的主要差别是sortedset中每一个成员都会有一个分数与之关联。redis 正是通过分数来为集合的成员进行从小到大的排序。sortedset中分数是可以重复的。
zadd key score member score2 member2... : 将成员以及该成员的分数存放到sortedset中 
zscore key member : 返回指定成员的分数 zcard key : 获取集合中成员数量 
zrem key member [member...] : 移除集合中指定的成员，可以指定多个成员
zrange key start end [withscores] : 获取集合中脚注为start-end的成员，[withscores]参数表明返回的成员 包含其分数 
zrevrange key start stop [withscores] : 按照分数从大到小的顺序返回索引从start到stop之间的所有元素 （包含两端的元素） 
zremrangebyrank key start stop : 按照排名范围删除元素


## 1.4 高级数据结构 对基础数据结构的封装

- Bitmaps：setbit/getbit/bitop/bitcount/bitpos 
bitmaps不是一个真实的数据结构。而是String类型上的一组面向bit操作的集合。由于 strings是二进制安全的blob，并且它们的最大长度是512m，所以bitmaps能最大设置 2^32个不同的bit。 
例子 : 
https://www.cnblogs.com/54chensongxia/p/13794391.html
基数排序 , 计数排序 ? 
roaringbitmap , 通过划分范围来压缩 , 桶排序 ? 

- Hyperloglogs：pfadd/pfcount/pfmerge
 在redis的实现中，使用标准错误小于1％的估计度量结束。这个算法的神奇在于不再需要与需要统计的项相对应的内存，取而代之，使用的内存一直恒定不变。最坏的情况 下只需要12k，就可以计算接近2^64个不同元素的基数。

 - GEO：geoadd/geohash/geopos/geodist/georadius/georadiusbymember 
Redis的GEO特性在 Redis3.2版本中推出，这个功能可以将用户给定的地理位置（经度和纬度）信息储存起来，并对这些信息进行操作。


## 1.5 Redis 到底 是单线程，还是多线程？

作为进程是多线程.
IO线程： 
- redis 6之前（2020年5月），单线程 
- redis 6之后，多线程，NIO模型 ==> 主要的性能提升点 

内存处理线程： - 单线程 ==> 高性能的核心

# 二. Redis六大使用场景
## 2.1 业务数据缓存
- 通用数据缓存，string，int，list，map等。
- 实时热数据，最新500条数据。
- 会话缓存，token缓存等。
## 2.2 业务数据处理
- 非严格一致性要求的数据：评论，点击等。
- 业务数据去重：订单处理的幂等校验等。 
- 业务数据排序：排名，排行榜等。
## 2.3  全局一致计数
- 全局流控计数 
- 秒杀的库存计算
- 抢红包 
- 全局ID生成

## 2.4 高效统计计数
- id去重，记录访问ip等全局bitmap操作 
- UV、PV等访问量==>非严格一致性要求

## 2.5 发布订阅与Stream
- Pub-Sub 模拟队列
 subscribe comments
  publish comments java  
- Redis Stream 是 Redis 5.0 版本新增加的数据结构。 
Redis Stream 主要用于消息队列（MQ，Message Queue）。

https://www.runoob.com/redis/redis-stream.html

## 2.6 分布式锁
关键 : 原子性 , 互斥 , 超时
setnx + expire  , 问题 : setnx + expire 两个操作之间是非原子的
set locak1 1 nx px 30000 : 整合成一条命令

(1) 获取锁--单个原子性操作 
			SET dlock my_random_value NX PX 30000 
		(2) 释放锁-- 使用lua脚本 - 保证原子性+单线程，从而具有事务性

```lua
 if redis.call("get",KEYS[1]) == ARGV[1] then
 return redis.call("del",KEYS[1]) 
else
return 0 end
```
 因为redis是单线程执行的 , 所以在执行lua脚本时候不会有其他线程干扰.保证get和del之间不会有其他干扰.
redis可以保证一个lua脚本中有多个改动操作,在同一个事务中



# 三. Redis的Java客户端

**Jedis** 

官方客户端，类似于JDBC，可以看做是对redis命令的包装。 

基于BIO，线程不安全，需要配置连接池管理连接。

**Lettuce**

目前主流推荐的驱动，基于Netty NIO，API线程安全。

**Redission**

基于Netty NIO，API线程安全。 

亮点：大量丰富的分布式功能特性，比如JUC的线程安全集合和工具的分布式版本，分布式的基本数据类型和锁等。


# 四. Redis与Spring整合 
## 4.1 spring

核心是 RedisTemplate(可以配置基于Jedis，Lettuce，Redisson) .使用方式类似于MongoDBTemplate，JDBCTemplate或JPA ,封装了基本redis命令操作.
## 4.2 spring-boot
引入 spring-boot-starter-data-redis 配置 spring redis
在使用springboot的starter时候只能选择一种模式的redis
spring的redis主从模式和集群模式使用的类是 : JedisPool , JedisCluster
## 4.3 Spring Cache
默认使用全局的CacheManager自动集成 

使用ConcurrentHashMap或ehcache时，不需要考虑序列化问题。

 redis的话，需要：

- 默认使用java的对象序列化，对象需要实现Serializable 
- 自定义配置，可以修改为其他序列化方式



## 4.4 例子 : 
```txt
MyBatis项目集成cache示例
1、集成spring boot与mybatis，实现简单单表操作，配置成rest接口 
2、配置ehcache+mybatis集成，实现mybatis二级缓存 
3、配置spring cache+ehcache缓存，实现方法级别缓存 
4、修改spring cache使用redis远程缓存代替ehcache本地缓存 
5、修改spring cache使用jackson json序列化代替java序列化 
6、整个过程中，使用wrk压测rest接口性能，并分析为什么? 
7、尝试调整各种不同的配置和参数，理解cache原理和用法。
```



## 4.5 redis性能和本地缓存性能差距可能原因 : 

- 网络io

- 序列化,反序列化 ,redis默认使用java序列化

- 测试case简单的话 ,直接走mysql时候mysql也有一层缓存

# 五.Redis高级功能
## 5.1 Redis 事务
 - Redis 事务命令： 指的是在redis的一批操作中要么全都成功,要么全都失败
```
开启事务：multi ~ 对应数据库  begin
命令入队 
执行事务：exec ~ commit
撤销事务：discard ~ cancel
```
 - Watch 实现乐观锁 
watch 一个key，发生变化则事务失败

- 数据存储时候的选择
```
对于一组数据 , 可以选择存储json或者多个key-value放在redis里面,但是建议不要使用json,主要原因有
(1) json需要序列化
(2) json非结构化数据 , 数据量小还可以,一旦数据量大了存储会很复杂,相当于是用redis的一个key表示一个表,而拆开则是模拟一行
(3)数据过期 , json无法针对具体数据进行处理
(4) 对于几乎不变的数据可以使用json来存储
```
## 5.2 Redis Lua ~ open resty = nginx + lua jit
- 类似于数据库的存储过程，mongodb的js脚本 
```lua
> 直接执行
eval "return'hello java'" 0 
eval "redis.call('set',KEYS[1],ARGV[1])" 1 lua-key lua-value 
> 预编译
script load script脚本片段 
返回一个SHA-1签名 shastring 
evalsha shastring keynum [key1 key2 key3 ...] [param1 param2 param3 ...]
(1) 因为单线程  ,所以脚本片段是单线程,操作不会被打断,值不会被修改
(2) 每个脚本执行 ,都能保证事务
```

## 5.3 Redis 管道技术

合并操作批量处理，且不阻塞前序命令： (把多条命令,通过回车,换行方式,一次发过去 ,减少多次执行单条命令的网络io开销)
```
% (echo -en "PING\r\n SET pkey redis\r\nGET pkey\r\nINCR visitor\r\nINCR visitor\r\nINCR visitor\r\n"; sleep 1) | nc localhost 6379 
(nc 相当于 telnet)
输出： 
+PONG 
+OK 
$5
redis 
:1
:2
:3
```
**和redis的相关m(批处理)操作区别 : 管道的多个命令是没有关系的 ,而m**相关操作虽然也是命令聚合,但是前后是由关联的.

## 5.4 Redis 数据备份与恢复
### 5.4.1 RDB ~类比与mysql的 frm
备份 : 执行 save 即可在redis数据目录生成数据文件 dump.rdb ,也可以异步执行 bgsave 

恢复 : 将备份文件 (dump.rdb) 移动到 redis 数据目录并启动服务即可 .

查看文件夹  : CONFIG GET dir 

### 5.4.2  AOF ~ 类比与mysql的 binlog
备份 : 如果 appendonly 配置为 yes，则以 AOF 方式备份 Redis 数据，那么此时 Redis 会按 照配置，在特定的时候执行追加命令，用以备份数据。 
```
appendfilename "appendonly.aof" 
# appendfsync always 
# appendfsync everysec 
# appendfsync no...... 
```
AOF 文件和 Redis 命令是同步频率的，假设配置为 always，其含义为当 Redis 执行 命令的时候，则同时同步到 AOF 文件，这样会使得 Redis 同步刷新 AOF 文件，造成缓慢。而采用 evarysec 则代表每秒同步一次命令到 AOF 文件。

恢复 : 自动加载 , 自动找appendonly.aof 文件,恢复数据

## 5.5 redis性能优化

- 内存优化  ~ 10G/20G 不建议太大
  https://redis.io/topics/memory-optimization
   hash-max-ziplist-value 64 
  zset-max-ziplist-value 64 
- CPU优化 
  不要阻塞  , 特别是在lua脚本中
  谨慎使用范围操作 
  SLOWLOG get 10 //默认10毫秒，默认只保留最后的128条 , 查询慢查询

## 5.6 redis分区 设计规划 
- 容量  :  多个业务系统,共用一个redis,还是应该分开 , 建议分开 ,否则由于某一个业务卡卡住会影响其他业务,如果业务使用频率低可以复用,但是要注意业务隔离,例如前缀隔离
- 分区 : 如果缓存数据变大 , 可以进行分区 , 类似垂直拆分


# 六. 使用的一些经验

## 6.1 性能：
- 线程数与连接数；client~线程数(4-8)与连接数(server最大支持10000,可修改)
- 监控系统读写比(N:1)和缓存命中率(90%+)； 

## 6.2 容量： 
- 做好容量评估，合理使用缓存资源； 监控要注重增量变化,提前处理

## 6.3 资源管理和分配：
- 尽量每个业务集群单独使用自己的Redis，不混用； 
- 控制Redis资源的申请与使用，规范环境和Key的管理（以一线互联网为例）； 
- 监控CPU 100%，优化高延迟的操作。

redis : info命令 , 查看当前服务器状态 内存/lua内存/统计数据stats



# 七. 其他



## 7.1 删除策略

key 到了过期时间并不是立刻从内存中删除,而是采用了不同的删除策略: 定时删除 , 惰性删除 , 定期删除

- 定时删除 : 在设置键的过期时间的同时,创建一个定时器,让定时器执行对键的删除操作.
- 惰性删除 : 查询谋和key时候先判断是否过期,如果过期,则删除,否则返回
- 定期删除 : 默认100ms随机抽一些设置了过期时间的key,检查是否过期,如果过期了就删除
- 使用 : 定期删除 + 惰性删除

## 7.2 淘汰机制

- volatile-lru : 从已经设置过期时间的数据集中挑选最近最少使用的数据淘汰
- volatile-ttl : 从已经设置过期时间的数据集中挑选将要过期的数据淘汰
- Volatile-random : 从已经设置过期时间的数据集中挑选任意数据淘汰
- Volatile-lfu : 从已经设置过期时间的数据集中挑选使用频率最低的数据淘汰
- allkey-lru : 当内存不足以容纳写入新数据时候,移除最少使用的数据
- allkey-lfu : 从数据集中挑选使用频率最低的数据淘汰
- allkey-random : 从数据集中挑选任意数据淘汰
- No-enviction : 进制驱逐数据,默认策略



## 7.3 redis中查找大的key的方法 : 

https://my.oschina.net/u/4339087/blog/3306368











