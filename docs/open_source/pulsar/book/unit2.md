# 第二章 客户端

## 2.1 Topic 归属
#### pulsar 的 topic 是如何与 broker 建立联系的?

pulsar 使用 zk 保存元数据,因为 pulsar 现阶段但集群可以承载几十万 topic,如果在 zk 中维护每个 topic 和 broker 的关系,就要保存上百万的元数据,基于此 pulsar 进行了优化,让保存的元数据下降了两个数量级.

topic 不会字节与 broker 建立联系.在 topic 基础上抽象了一个 Bundle,并使用了一致性哈希算法,Bundle 作为一致性哈希的虚拟节点,Topic 通过名称计算哈希值.通过 Bundle 与 broker 建立联系,zk 中只保存 Bundler 与 broker 的关系.Topic 的归属由 Bundle 动态计算出来.

## 2.2 生产者客户端