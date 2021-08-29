# 源码阅读

## 一.中间件

|  中间件类型  |              开源项目               |
| :----------: | :---------------------------------: |
| 数据库中间件 |           shardingsphere            |
| 远程调用RPC  |          dubbo ,sofa,grpc           |
|  消息中间件  | Kafka、ActiveMQ、RabbitMQ、RocketMQ |



| 开源项目       | 文档地址                                                    | 项目地址                                               |
| -------------- | ----------------------------------------------------------- | ------------------------------------------------------ |
| shardingsphere | http://shardingsphere.apache.org/index_zh.html              | https://github.com/apache/shardingsphere               |
| dubbo          | https://dubbo.apache.org/zh/                                | https://github.com/apache/dubbo                        |
| sofa           | https://www.sofastack.tech/                                 | https://github.com/sofastack/sofa-rpc                  |
| grpc           | http://doc.oschina.net/grpc?t=57966<br>https://www.grpc.io/ | https://github.com/grpc/grpc                           |
| Kafka          | https://kafka.apache.org/                                   | https://github.com/apache/kafka                        |
| ActiveMQ       | https://activemq.apache.org/                                | https://github.com/apache/activemq                     |
| RabbitMQ       | https://www.rabbitmq.com/                                   | https://github.com/rabbitmq                            |
| RocketMQ       | https://rocketmq.apache.org/                                | https://github.com/apache/rocketmq/tree/master/docs/cn |



## 二.其他开源项目

|          类型          |    开源项目    |
| :--------------------: | :------------: |
|        配置中心        |     apollo     |
|     分布式链路追踪     |   skywalking   |
| 分布式应用程序协调服务 |   zookeeper    |
|         数据库         |     redis      |
|       协议缓冲区       |  protobuffer   |
|   分布式搜索分析引擎   | elastic-search |

| 开源项目       | 文档地址                                                     | 项目地址                                    |
| -------------- | ------------------------------------------------------------ | ------------------------------------------- |
| apollo         | https://ctripcorp.github.io/apollo/#/zh/design/apollo-introduction | https://github.com/ctripcorp/apollo         |
| skywalking     | https://skyapm.github.io/document-cn-translation-of-skywalking/ | https://github.com/apache/skywalking        |
| zookeeper      | https://zookeeper.apache.org/                                | https://github.com/apache/zookeeper         |
| redis          | https://redis.io/                                            | https://github.com/redis/redis              |
| protobuffer    | https://developers.google.com/protocol-buffers               | https://github.com/protocolbuffers/protobuf |
| elastic-search | https://www.elastic.co/cn/elasticsearch/                     | https://github.com/elastic/elasticsearch    |


## 三. 关于开源

- 开源的社区决定了这个项目的发展前景

> 在公司的场景下，开源的使用成本可能比闭源更高，因为人力和时间在组织中都是需要成本的

- 开源是一种组织形式
- 开源意味着更加透明和开放
- 开源意味着有限但是更加明确的责任

>以数据库为例，做得好的数据库是并没有开源的，像oracle 数据库，但是mysql进行了开源，原因可以归结为两点 : 

第一点，oracle是以盈利为目的的，并且有了40多年的发展历史，mysql想要追赶上需要的人力成本以及时间成本太高了，所以将其开源，通过社区大家一起贡献。
		第二点，mysql开源，但是mysql的服务是收费的，虽然代码开放出去了，但是后续的服务是可以通过收费带来一定的经济价值。

> 开源模式 : 

- 个人开源，个人的精力有限，后面很大可能都会死掉，一般存活下来的大佬最后也是创业了将其挂在公司上
- 公司开源，目的是为了抢占行业话语权，拉拢用户，卖商业版本
- 公司开源，卖服务，在产品上只做开源，卖服务

> 是否开源选择

- 软件toB还是toC,用户是企业还是个人
- 是否业内领先，最先进的技术永远不会开源，但是由于技术更新迭代很快，所以开源出来的技术，对于部分人员来说还是很先进的
- 有没有直接变现的渠道，对于公司来说终究还是以盈利为目的的
- 是否符合技术或者政策发展的大方向
- 是否有清晰的开源或者商业化路线
