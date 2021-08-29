# ActiveMQ消息中间件

# 一. 介绍

 - 高可靠的、事务性的消息队列 
 - 当前应用最广泛的开源消息中间件 
 - 项目开始与2005年CodeHaus、2006年成为Apache项目 后来与HornetQ合并，新的消息队列叫：Artemis，目前是ActiveMQ的子项目 .


由于互联网发展,存在大量数据堆积,延时处理的需求 , 于是kafka出现之后,成为了mq的实施标准.于是 activemq想在 5.*基础上做一个6.0,集成kafka新功能,起名叫Apollo ,jboss 的hornmq也发现市场2被kafka抢占 , 于是两方合并了,废弃了apollo,一起做了一个artemic  作为activemq 6.0 所以 : 常说的 activemq 指的是 5.0 版本  , 而artemic  是 6.0版本功能最全的开源消息队列 https://activemq.apache.org/

# 二. 主要功能

1. 多种语言和协议编写客户端。 
语言: Java, C, C++, C#, Ruby, Perl, Python, PHP等 
应用协议: OpenWire,Stomp REST,WS Notification,XMPP,AMQP,MQTT 
OpenWire : 自研的协议 , 由于最初只支持jms,但是jms是java特有的 , 为了跨平台,所以研发了这个和语言无关的协议.
2. 完全支持JMS1.1和J2EE 1.4规范 (持久化,XA消息,事务) 
3. 与Spring很好地集成，也支持常见J2EE服务器 
4. 支持多种传送协议:in-VM,TCP,SSL,NIO,UDP,JGroups,JXTA 
5. 支持通过JDBC和journal提供高速的消息持久化 
6. 实现了高性能的集群模式

Broker简介
https://blog.csdn.net/hqm12345qw/article/details/80679758
ActiveMQ学习笔记（8）----ActiveMQ的消息存储持久化（JDBC和journal）
https://blog.csdn.net/Anfen0/article/details/106914496?utm_medium=distribute.pc_relevant.none-task-blog-baidujs_title-2&spm=1001.2101.3001.4242

# 三. 使用示例

使用场景 ActiveMQ的使用场景：

- 所有需要使用消息队列的地方； 
- 订单处理、消息通知、服务降级等等； 
- 特别地，纯java实现，支持嵌入到应用系统

ActiveMQ 的手动确认 ,实际上确认的是前面的所有未确认消息.

ActiveMQ官网： https://activemq.apache.org 

ActiveMQ集群-网络集群模式详解: https://kimmking.blog.csdn.net/article/details/8440150 

ActiveMQ的集群与高可用: https://kimmking.blog.csdn.net/article/details/13768367
