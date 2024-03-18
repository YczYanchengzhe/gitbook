# 第一章 认识 pulsar



## 1. 总体介绍
Pulsar 服务端的基本结构可以分为三层,分别是代理层,Broker 层和 Bookire 层.

如果没有代理层,那么生产者/消费者会直接和每个生产/消费的 Broker 建立一个连接.

Broker 主要负责整个 Pulsar 的业务逻辑,BookKeeper 只负责数据的存储.

Broker 和 Zookeeper 都会用到 Zookeeper , 主要用其存储原数据,选主,分布式锁的功能.

## 2. pulsar 对于 topic 的管理

通过抽象了一层 bundle , 使用一致性哈希算法,bundle 作为一致性哈希的虚拟节点,topic 通过名称计算发细致,并散列到哈希环中,找到对应的 bundle,然后通过 bundle 和 broker 建立联系.这样 zk 中只保存 bundle 和 broker 之间的关系.topic 归属哪个 broker 是通过一致性哈希动态计算出来的.
