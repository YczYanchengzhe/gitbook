# 一. Redis 主从复制 ,
从单机到多节点 : 从节点只读、异步复制。~ 类比 mysql的主从

配置从库,从库会读取主库的RDB 文件,进行初始化,从库只读,新数据增量更新

# 二. Redis Sentinel 主从切换：走向高可用
当主节点down掉时候,从怎么切换为主?
## 2.1 手动处理
模拟主节点宕机 , 
关闭从节点和主节点的关联 , 此时从节点可以写入,在重新为新的节点设置从节点
(1) 从库的数据由于异步同步,可能会和主库数据不一致,那么主库宕机之后,相差的数据无法弥补
(2) 手动操作有一定风险
## 2.2 使用 sentinel  ~  类比mysql的 MHA
可以做到监控主从节点的在线状态，并做切换（基于raft协议）。 
两种启动方式： 
```
> redis-sentinel sentinel.conf (写到sentinel.conf中)
> redis-server redis.conf --sentinel sentinel. (写到 redis.conf中)
conf配置：
sentinel monitor mymaster 127.0.0.1 6379 2 (sentinel  哨兵节点监控mymaster主节点,他的ip,端口,每次需要两个哨兵节点确认)
sentinel down-after-milliseconds mymaster 60000 (1min心跳状态不对,将其下线)
sentinel failover-timeout mymaster 180000 (主库节点变更超时时间)
sentinel parallel-syncs mymaster 1 (并行同步从节点数量,并行同步的节点进行数据同步,其他节点负责对外提供服务)
不需要配置从节点，也不需要配置其他sentinel信息
一个 sentinel 哨兵可以监控多个redis状态
```
redis sentinel原理介绍：http://www.redis.cn/topics/sentinel.html 
redis复制与高可用配置：https://www.cnblogs.com/itzhouq/p

脑裂 : 1主4从 , 当网络出现问题 , CAP中的P , 出现局部网络互通 , 3个一组A,2个一组B ,此时A组选举A1为master,B组选举B1为master,出现多个主节点.
可以通过配置 : 如说1主5从 , 可以配置至少有2从节点存活才可以写入数据.此时,少数派的主不能写入数据,

sentinel在redis中相当于 : 注册中心.



# 三. Redis Cluster：走向分片 ~ 全自动分库分表

Redis Cluster通过一致性hash的方式，将数据分散到多个服务器节点：先设计 16384 个哈希槽，分配到多台redis-server。当需要在 Redis Cluster中存取一个 key时， Redis 客户端先对 key 使用 crc16 算法计算一个数值，然后对 16384 取模，这样每个 key 都会对应一个编号在 0-16383 之间的哈希槽，然后在 此槽对应的节点上操作。
```
 > cluster-enabled yes 
```
注意： 

- 节点间使用gossip通信，规模<1000 

- 默认所有槽位可用，才提供服务 

- 一般会配合主从模式使用 

- 相关文档

```
redis cluster介绍：http://redisdoc.com/topic/cluster-spec.html 
redis cluster原理：https://www.cnblogs.com/williamjie/p/11132211.html 
redis cluster详细配置：https://www.cnblogs.com/renpingsheng/p/9813959.html
为什么redis-cluster使用16384插槽？ : https://github.com/redis/redis/issues/2576
```



# 四. Redission : Redis 的Java分布式组件库-Redission 

- 基于Netty NIO，API线程安全。 

- 亮点：大量丰富的分布式功能特性，比如JUC的线程安全集合和工具的分布式版本,分布式的基本数据类型和锁等。 

- 官网：https://github.com/redisson/redisson

```
示例1：   
  分布式锁，RLock ==> 能实现跨节点的锁状态 
示例2：   
  分布式的Map，RMap ==> 全集群共享的，一个机器改了，其他都会自动同步 
```

  

# 五. 内存网格 -  Hazelcast 
## 5.1 简介

Hazelcast IMGD(in-memory data grid) 是一个标准的内存网格系统,它具有以下的一 些基本特性： 

- 分布式的：数据按照某种策略尽可能均匀的分布在集群的所有节点上。

- 高可用：集群的每个节点都是 active 模式，可以提供业务查询和数据修改事务；部分节点不可用，集群依然可以提供业务服务。 

- 可扩展的：能按照业务需求增加或者减少服务节点。 

- 面向对象的：数据模型是面向对象和非关系型的。在 java 语言应用程序中引入 hazelcast client api是相当简单的。 

- 低延迟：基于内存的，可以使用堆外内存。 
- 文档：https://docs.hazelcast.org/docs/4.1.1/manual/html-single/index.html

## 5.2  部署模式
Client-Server 模式 : 单独部署,使用远端内存
嵌入模式 : 使用jvm的堆内存,减少了网络开销

## 5.3 内存网格 - Hazelcast 数据分区
以 Map 结构说明如下： 

- 数据集默认分为 271 个分区；(分区比redis cluster少) ,可以通过 hazelcast.partition.count 配置修改。 
- 分布式相关配置,默认都是开启的 ,redis默认关闭
- 所有分区均匀分布在集群的所有节点上；
- 每一个分区至少在两个节点上存在 , 每一个server上只会保存一个分区的一个副本.
- 同一个节点不会同时包含一个分区的多个副本(副本总是分散的以保证高可用),通过多副本,数据的冗余进行数据备份。 
  副本配置: 

```xml
<hazelcast> 
	<map name="default"> 
		<backup-count>0</backup-count> 
		<async-backup-count>1</async-backup-count>
 	</map> 
</hazelcast>
```



## 5.4 内存网格 - Hazelcast 集群与高可用
- AP，集群自动管理， 
- 扩容和弹性，分区自动rebalance，业务无感知，

## 5.5 事务支持

- 支持事务操作： 

```java
TransactionContext context = hazelcastInstance.newTransactionContext(options); 	
context.beginTransaction(); 
try {
// do other things 
context.commitTransaction(); 
} catch (Throwable t) {
 	context.rollbackTransaction(); 
} 
```
- 支持两种事务类型： 
  ONE_PHASE: 只有一个提交阶段；在节点宕机等情况下可能导致系统不一致； 
  TWO_PHASE: 在提交前增减一个 prepare 阶段；该阶段检查提价冲突，然后将commit log 拷贝到一个本分 节点；如果本节点宕机，备份节点会完成事务提交动作；



## 5.6 Hazelcast 数据亲密性 

确保业务相关的数据在同一个集群节点上，避免操作多个数据的业务事务在执行中通过网络请求 数据，从而实现更低的事务延迟。

 1. 通过 PartitionAware 接口，可以将相关数据定位在相同的节点上；

```java
 public interface PartitionAware<T> { 
  T getPartitionKey();
   }
```
2. 自定义：PartitioningStrategy
```xml
 <map name="name-of-the-map"> 
		<partition-strategy> 								
com.hazelcast.partition.strategy.StringAndPartitionAwarePartitioningStrategy 
		</partition-strategy>
 </map>
```