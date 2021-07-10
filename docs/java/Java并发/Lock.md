# 锁

## 一. 分布式锁
### 1.1 基本知识
#### 1.1.1 使用场景
分布式环境下保证数据一致性

#### 1.1.2 设计因素
- 互斥性 : 同一时刻只能有一个服务(或应用)访问资源，特殊情况下有读写锁
- 原子性 : 一致性要求保证加锁和解锁的行为是原子性的
- 安全性 : 锁只能被持有该锁的服务(或应用)释放
- 容错性 : 在持有锁的服务崩溃时，锁仍能得到释放避免死锁
- 可重用性 : 同一个客户端获得锁后可递归调用
- 公平性 : 看业务是否需要公平，避免饿死
- 支持阻塞和非阻塞 : 和ReentrantLock 一样支持 lock 和 trylock 以及 tryLock(long timeOut)
- 高可用 : 获取锁和释放锁 要高可用
- 高性能 : 获取锁和释放锁的性能要好
- 持久性 : 锁按业务需要自动续约/自动延期
- 最多一次/最少一次


### 1.2 数据库实现分布式锁

- 基于表唯一索引,将锁定的资源作为唯一索引
```shell
# 使用
- 创建记录时候设置资源
- 使用完成删除资源
# 问题
客户端泵阔后,锁无法释放,不可重入
```
- 基于数据库排它锁
```sql
-- 性能差,不可重入
select for update
```

### 1.3 redis 实现分布式锁 
- 基于set / setnx
```lua
- 获取锁
SET resource_name unique_value NX PX 30000

- 释放锁（lua脚本中，一定要比较value，防止误解锁）
if redis.call("get",KEYS[1]) == ARGV[1] then
	return redis.call("del",KEYS[1])
else
	return 0
end

- 非阻塞，只能通过死循环实现，通过 Redis 的订阅发布模式来实现通知的成本太高
- redis单点，主从切换可能出现锁丢失
```

- 基于redlock / redisson



### 1.4 基于zookeeper的实现
原理 :
- 在zk上注册一个包含资源以及顺序性id的节点
- 每个机器在获取锁时候,把自己注册上去,并注册watch,如果没有比自己序号小的节点那么执行逻辑

> 羊群效应 : 在每一次节点添加和移除都会触发全量的watch,这些重复操作很多都是无用的,实际上每个锁竞争者只要关注序号比自己小的节点是否存在就可以了,不存在,就执行,存在就等待.

可以优化为 : 
- 读请求：向比自己序号小的最后一个写请求节点注册Watcher监听,只要监听自己前面的最后一次写入就可以了.
- 写请求：向比自己序号小的最后一个节点注册Watcher监听,只要保证自己前面没人即可.



### 1.5 基于ETCD实现

参考最下方参考资料

### 1.6 借助分布式锁中间件



# 参考资料

- [5种分布式锁实现的对比](https://blog.csdn.net/qq_16605855/article/details/90257245)

- [利用Zookeeper实现 - 分布式锁](https://mp.weixin.qq.com/s?__biz=MzI1NDU0MTE1NA==&mid=2247483862&idx=1&sn=914d7912c7313123897ef479f0bd7f80&chksm=e9c2eddbdeb564cd58923f395441996332fb9332d8c53fc6368a96a19b6e59297020e6f47537&scene=21&xtrack=1&key=e3977f8a79490c63bdcb6c58fa99576e80eb3c7db7fe393f6039d192362c395566c47cd13a582277c96064472e8271f63879aadb0c8c0de6f81dcc876c06e46d50130699ad56ba62693f29e202a34258&ascene=1&uin=MjI4MTc0ODEwOQ==&devicetype=Windows%207&version=62060719&lang=zh_CN&pass_ticket=HBqvqAg8/E8F0PgXE4QY26oVnramk4zVwRSUT2QD%20BrblglnRt6GwnH4nA%20OLM//#wechat_redirect)

- [分布式锁的原理和实现详解 - etcd](https://segmentfault.com/a/1190000014297365)

