# 工作相关知识整理

> 不积跬步无以至千里 , 不积小流无以成江海!
> 拼搏吧,骚年!
> 参阅所有资料都要抱着他可能是错误的态度来阅读


# RPC 基本原理 
RPC 和 MQ是分布式底层通信基础
RPC : 像调用本地方法一样调用远程方法

二方库 : maven 私服
三方库 :  外部依赖的库,包


# 设计 : 
POJO实体类 , 接口定义

# 代理 : 
 java : aop , 动态代理
 
# 序列化 : 
1. 语言原生的序列化  : RMI ,  Remoting
2. 二进制 , 平台无关 : Hessian , avro , kyro , fst  ,  好处 : 占用少 , 坏处 : 不可读
3. 文本 : json(json格式是不区分1 和1.2 的) , xml(xsd , DTD 数据类型更加复杂)

# 网络
TCP/SSL
HTTP/HTTPS
WebService
Restful

# 查找实现类

# Dubbo 
qdubbo的主要功能  : 高性能 , 轻量级的开源Java服务框架
核心能力 : 
面向接口代理的高性能RPC调用 , 只能容错和负载均衡 , 服务自动注册和发现 , 高度可扩展能力 , 运行期流量调度 , 可视化服务治理与运维.

# Dubbo 主要功能
- 多协议(序列化 , 传输 , RPC)
- 服务注册发现
- 配置 , 元数据管理
扩展功能 : 集群, 高可用 , 管控
- 集群 : 集群负载均衡
- 治理 , 路由
- 控制台 , 管理与监控

成功要点 : 灵活扩展 , 简单易用

# 整体架构 ,参考dubbo官网
api , spi : api 应用程序接口 .  spi : 服务提供程序接口

invoke :  封装服务本身 , 调用真实服务
protocol :  协议
url : 用来表示一个服务 . ip:port:service.... 使用url 拼接了所有需要的信息 

filter : 请求过滤器,响应过滤器

核心模块

extensionLoading 加载之后的spi缓存起来
依赖反转 : serviceloader , eventbus , callback
InjvmProtocol : export : 
InjvmExporter : map<url , exporter> , 找到invoke 不涉及网络
dubboProtocol : openServer创建服务 , createServer : 绑定处理器
Invocation : 具体方法调用信息 ,
扩展参数 : http : 请求头 , 二进制 : 放在协议头中
thrift 在协议前面加了扩展参数,导致和thrift协议不兼容
referenceConfig :  createProxy

相关功能 : 
集群与路由
cluster 
泛化
隐式传参
mock


# Dubbo 特点
- 框架分层设计 , 可以任意组装和扩展
- 异步去拉取服务端节点,网络传输不需要经过注册中心,和服务端是直连的


# Dubbo 源码阅读顺序  : 
dubbo源码 : rpc -> common -> cluster , config , configserve -> filter , remoting , registry
调试dubbo : 
1. provider 看protocol 的export
2. consumer看 referenceConfig
3. provider 知性逻辑看protocol 的handler