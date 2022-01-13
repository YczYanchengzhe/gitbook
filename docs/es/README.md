# ElasticSearch

## 1. 搜索引擎
 lucene 搜索引擎 



## 2. es 丢弃数据

es 问题总结  : es 在对于节点进行扩容时候,新加的节点因为 shard 数量比较少,所以在按天拆索引的场景下,会导致这个新节点分到更多的 shard , 很容易出内存,负载的问题,因为新的一天的数据大多都在往这个节点写入 , es 接收到数据之后,会通过 master 进行路由,之后将数据发送给 data 节点 , data 节点存在一个队列,在收到数据之后 ,如果队列满了,那么数据会被 es 端丢弃 : 

kibana 查看详情 : GET _cat/thread_pool?v     v: 表示增加一个 title

 # 参考资料
 - [1] [Lucene--介绍](https://www.jianshu.com/p/4b1a88d1c1d0)

