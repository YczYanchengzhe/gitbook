# 开源协议-OpenTrace
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
#### 1. 为什么需要OpenTracing？
> OpenTracing通过提供平台无关、厂商无关的API，使得开发人员能够方便的添加（或更换）追踪系统的实现。

#### 2. 什么是一个Trace?
在广义上，一个trace代表了一个事务或者流程在（分布式）系统中的执行过程。在OpenTracing标准中，trace是多个span组成的一个有向无环图（DAG），每一个span代表trace中被命名并计时的连续性的执行片段。

#### 3. 相关概念和术语
##### (1) Traces
一个trace代表一个潜在的，分布式的，存在并行数据或并行执行轨迹（潜在的分布式、并行）的系统。一个trace可以认为是多个span的有向无环图（DAG）。
##### (2) Spans
一个span代表系统中具有开始时间和执行时长的逻辑运行单元。span之间通过嵌套或者顺序排列建立逻辑因果关系。
##### (3) Operation Names
每一个span都有一个操作名称，这个名称简单，并具有可读性高。（例如：一个RPC方法的名称，一个函数名，或者一个大型计算过程中的子任务或阶段）
##### (4) Inter-Span References
一个span可以和一个或者多个span间存在因果关系。OpenTracing定义了两种关系：ChildOf 和 FollowsFrom。这两种引用类型代表了子节点和父节点间的直接因果关系。

> **`ChildOf` 引用:** 一个span可能是一个父级span的孩子，即"ChildOf"关系。在"ChildOf"引用关系下，父级span某种程度上取决于子span。

下面这些情况会构成"ChildOf"关系：

- 一个RPC调用的服务端的span，和RPC服务客户端的span构成ChildOf关系
- 一个sql insert操作的span，和ORM的save方法的span构成ChildOf关系
- 很多span可以并行工作（或者分布式工作）都可能是一个父级的span的子项，他会合并所有子span的执行结果，并在指定期限内返回

> **`FollowsFrom` 引用:** 一些父级节点不以任何方式依赖他们子节点的执行结果，这种情况下，我们说这些子span和父span之间是"FollowsFrom"的因果关系。"FollowsFrom"关系可以被分为很多不同的子类型

- mq可以归为此类,因为消息的消费并不依赖消息的生产者.

##### (5) Logs
每个span可以进行多次Logs操作，每一次Logs操作，都需要一个带时间戳的时间名称，以及可选的任意大小的存储结构.其核心在于出现异常或者有需要的时候,可以通过log的方式记录下系统此时的状态,例如堆栈信息等.

##### (6) Tags
每个span可以有多个键值对（key:value）形式的Tags，Tags是没有时间戳的，支持简单的对span进行注解和补充。例如 : 在进行SQL相关操作的时候,数据源,SQL语句都可以作为span补充的tag键值对.

##### (7) SpanContext
每个span必须提供方法访问**SpanContext**。SpanContext代表跨越进程边界，传递到下级span的状态。(例如，包含`<trace_id, span_id, sampled>`元组)，并用于封装**Baggage** (关于Baggage的解释，请参考下文)。
##### (8) Baggage
> 对于Baggage的理解,以RPC为例,如果要进行跨进程的链路串联,那么服务端必须要存在一个串联ID,也就是TraceId,这个串联ID需要满足两点 : 
>
> 1. 全局唯一 : 一般使用UUID作为全局ID,skywalking在UUID基础上还引入了雪花算法,并进行了时间回正处理,加上线程号等生成唯一ID.
> 2. 在进程中传播 : 一般像HTTP可以通过请求头进行传递,RPC可以使用隐式传参的人功能,像DUBBO,SOFA,SCF等都是有支持隐式传参得到功能的.
>
> 当然,一般来说并不是只有TraceId就足够了,我们一般也会带上其他信息,而Baggage就是这些信息的一个抽象概念.对于skywalking来说,采用的名字是DataCarry.

**Baggage**是存储在SpanContext中的一个键值对(SpanContext)集合。它会在一条追踪链路上的所有span内*全局*传输，包含这些span对应的SpanContexts。

> Baggage 和 SpanTag的区别 : 核心区别是 SpanTag不会跨进程传输

##### (9) Inject and Extract

SpanContexts可以通过**Injected**操作向**Carrier**增加，或者通过**Extracted**从**Carrier**中获取，跨进程通讯数据（例如：HTTP头）



#### 4. 平台无关的API语义

> OpenTracing支持了很多不同的平台,每个平台的API，都需要根据上述的核心tracing概念来建模实现,下面就是对于这些的描述，尽量减少语言和平台的影响。

##### (1) Span 接口规范
- **Get the `Span`'s SpanContext** : 通过span获取SpanContext
- **Finish** : 完成已经开始的`Span`
- **Set a key:value tag on the `Span`** : 需要可以设置tag
- **Add a new log event ** : 为`Span`增加一个log事件
- **Set a Baggage item** : 设置一个string:string类型的键值对,用于跨进程传输
- **Get a Baggage item** : 通过key获取Baggage中的元素

##### (2)  SpanContext 接口规范

- **Iterate over all Baggage items**  : 遍历Baggage 的能力
- **Inject** : 向Carrier中增加数据
- **Extract** : 从Carrier中取出数据,进行关联

##### (3) Tracer 接口规范

- **Start a new `Span`** : 创建一个新的Span
- **Inject a `SpanContext`** : 将`SpanContext`注入到`SpanContext`对象中，用于进行跨进程的传输。
- **Extract a `SpanContext`** : 通过"carrier"跨进程获取`SpanContext`信息

##### (4) Global and No-op Tracers

每一个平台的OpenTracing API库必须提供一个no-op Tracer（不具有任何操作的tracer）作为接口的一部分。No-op Tracer的实现必须不会出错，并且不会有任何副作用，包括baggage的传递时，也不会出现任何问题。同样，Tracer的实现也必须提供no-op Span实现；
通过这种方法，监控代码不依赖于Tracer关于Span的返回值，针对no-op实现，不需要修改任何源代码。No-op Tracer的Inject方法永远返回成功，Extract返回的效果，和"carrier"中没有找到SpanContext时返回的结果一样。


### 参考文献 : 

- [Logging,Metrics 和 Tracing](http://peter.bourgon.org/blog/2017/02/21/metrics-tracing-and-logging.html)
- [openTracing - wusheng](https://wu-sheng.gitbooks.io/opentracing-io/content/)
- [今天我们聊聊 Trace 之 OpenTelemetry And TSW |概览](https://mp.weixin.qq.com/s?__biz=MzAxMTQ2NTA1Mg==&mid=2247487700&idx=1&sn=91dd1840afd849790cac0e118ec6b221&chksm=9b41e8e7ac3661f179e488f7705cb212a99cb262e7aa1570f923a87de22911f937d656ad6792&scene=21#wechat_redirect)