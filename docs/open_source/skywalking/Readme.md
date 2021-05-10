
# 简介 : 
该系列主要介绍skywalking的源码，参考网上一些资料以及一些课程进行总结和分享，如有侵权，请联系删除~

这里我们以skywalking目前最新的开源版本 8.5 进行分析理解

# 目录


### 第一部分 :  什么是APM

##### [1.APM概述](first/APM概述.md) 

#####  [2.OpenTrace](first/opentrace.md) 

#####  [3.环境搭建](first/env.md) 

### 第二部分 : 想要学好skywalking,这些知识要了解

一. 相关知识储备

1. Bytebuddy - 字节码增强神器
2. SPI技术,框架设计的宠儿
3. grpc+protobuffer 高性能的rpc框架
3. Java快速读写ES
4. GraphQL - 按需获取参数
5. Antlr4 强大的语法生成器工具
6. FreeMarker模板语言 

二. Skywlking模块介绍

1. agent模块
2. OAP模块
3. Datacarry模块
4. webapp模块
5. proto模块


### 第五部分: skywalking -agent 原理剖析

1. agent模块介绍
2. agent启动流程
3. 插件原理
4. scf-rpc插件原理(58自研的RPC框架)
5. wf-rpc插件原理(58自研的WEB框架)
6. java-指标获取
7. agent-oap核心交互
8. skywalking工具箱

### 第六部分:skywalking-datacarrier模块详解

1. buffer原理
2. Channels原理
3. DataCarrier 原理
4. IDriver 的实现

### 第七部分: skywalking -oap 原理剖析

1. oap模块介绍
2. Configuration - 配置中心的可插拔设计
3. 集群模块
4. server设计
5. core模块
6. 存储设计
7. stotage模块
8. receive模块
9. jvm指标分析
10. graphql查询模块
11. 报警模块
12. oal脚本语言

### 第八部分: skywalking落地问题总结

