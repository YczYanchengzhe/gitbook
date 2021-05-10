# 开源协议以及Skywalking概述
## 一.监控指标概述

下面选自 Peter Bourgon 的Blog

```text
# metrics
I think that the defining characteristic of metrics is that they are aggregatable: they are the atoms that compose into a single logical gauge, counter, or histogram over a span of time. As examples: the current depth of a queue could be modeled as a gauge, whose updates aggregate with last-writer-win semantics; the number of incoming HTTP requests could be modeled as a counter, whose updates aggregate by simple addition; and the observed duration of a request could be modeled into a histogram, whose updates aggregate into time-buckets and yield statistical summaries.
# logging
I think that the defining characteristic of logging is that it deals with discrete events. As examples: application debug or error messages emitted via a rotated file descriptor through syslog to Elasticsearch (or OK Log, nudge nudge); audit-trail events pushed through Kafka to a data lake like BigTable; or request-specific metadata pulled from a service call and sent to an error tracking service like NewRelic.
# tracing
I think that the single defining characteristic of tracing, then, is that it deals with information that is request-scoped. Any bit of data or metadata that can be bound to lifecycle of a single transactional object in the system. As examples: the duration of an outbound RPC to a remote service; the text of an actual SQL query sent to a database; or the correlation ID of an inbound HTTP request.
```

目前业内的监控核心包含三部分,分别是Logging,Metrics,Tracing,

Logging : 将项目中的debug日志和error日志单独记录,日志之间的关联性不大
		Metrics : 指标的数据,核心在于聚合,比如一段时间内访问某一个服务的qps,这个数据是每一次请求聚合得到的.
		Tracing : 处理请求范围内的信息,核心关注的是一次请求生命周期内的所有元数据.

用三个次来描述他们的关系就是 : 

```

# 离散的日志
Logging : event
# 可聚合的指标
Metrics : Aggregatable
# 一次请求生命周期内的全部信息
Tracing : Request scoped
```

对于这三者的关系及其重复部分可以从下图中看出~

![修正的带注释的维恩图](http://peter.bourgon.org/img/instrumentation/02.png)

## 二. OpenTrace 



## 三. Skywalking相关概念



## 四. Skywalking架构设置



### 参考文献 : 

- [Logging,Metrics 和 Tracing](http://peter.bourgon.org/blog/2017/02/21/metrics-tracing-and-logging.html)
- [openTracing - wusheng](https://wu-sheng.gitbooks.io/opentracing-io/content/)
- [今天我们聊聊 Trace 之 OpenTelemetry And TSW |概览](https://mp.weixin.qq.com/s?__biz=MzAxMTQ2NTA1Mg==&mid=2247487700&idx=1&sn=91dd1840afd849790cac0e118ec6b221&chksm=9b41e8e7ac3661f179e488f7705cb212a99cb262e7aa1570f923a87de22911f937d656ad6792&scene=21#wechat_redirect)