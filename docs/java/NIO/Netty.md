# Netty 学习



## 一. Netty特性

#### 1.1 高性能的协议服务器 : 

- 高吞吐
- 低延迟
- 低开销
- 零拷贝
- 可扩展 : 预制多种编解码功能,通过ChannelHandler对通信框架进行灵活扩展
- 松耦合 : 网络和业务逻辑分离
- 使用方便,可维护性好 : Api相对简单
- 成熟稳定 : 修复了已经发现的所有JDK,NIObug
- 经历了大规模的商业应用考验

#### 1.2 什么是高性能?
- 高并发用户
- 高吞吐量
- 低延迟


## 二.Netty基本概念 : 
#### 2.1 缓冲区 Buffer

Buffer是一个对象 , 包含一些要写入或者要读出的数据.

缓冲区实质上是一个数组,通常他是一个字节数组(ByteBuffer),也可以使用其他种类的数组.但是一个缓冲区不仅仅是一个数组,缓冲区提供了对数据的结构化访问以及维护读写位置的信息.

每一种Java的基本类型,除了Boolean都有对应的一种缓冲区.

#### 2.2 通道 channel

channel是一个通道,可以用它来读取和写入数据..

通道和流的不同之处在于通道是双向的,流只是在一个方向上移动.

channel可以分为两大类 :
- 用于网络读写的SelectableChannel
- 用于文件擦破做的FIleChannel

Netty 封装一个 ChannelFuture 接口。我们可以将回调方法传给 ChannelFuture，在操作完成时自动执行。

#### 2.3 Event && Handler

Netty 基于事件驱动,时间的处理器可以关联到入栈和出栈数据流.


#### 2.4 Encode & Decode
在处理网络IO的时候,需要进行序列化和反序列化,转换为Java对象与字节流.
- 对入站数据进行解码, 基类是 ByteToMessageDecoder。
- 对出站数据进行编码, 基类是 MessageToByteEncoder。

#### 2.5 ChannelPipeline 数据处理管道就是事件处理器链
有顺序、同一 Channel 的出站处理器和入站处理器在同一个列表中。

## 三.TCP 粘包/拆包

#### 3.1 发生的原因 :

MTU: Maxitum Transmission Unit  最大传输单元
MSS: Maxitum Segment Size 最大分段大小

- 应用程序write写入的字节大小大于套接字接口发送缓冲区的大小
- 进行MSS大小的TCP分段
- 以太网帧depayload 大于 MTU进行IP分片

#### 3.2  解决方案 : 只能通过应用协议栈设计解决

- FixedLengthFrameDecoder : 定长协议解码器,消息定长,不够空位补空格对于半包消息,该解码器会缓存半包消息等待下一个包到达后进行拼包,知道读取到一个完整的包.
- LineBasedFrameDecoder : 行分隔符解码器.包尾增加回车换行符进行分割,例如FTP协议
- DelimiterBasedFrameDecoder：分隔符解码器，分隔符可以自己指定
- LengthFieldBasedFrameDecoder：长度编码解码器，将报文划分为报文头/报文体
- JsonObjectDecoder：json 格式解码器，当检测到匹配数量的“{” 、”}”或”[””]”时，则认为是一个完整的 json 对象或者 json 数组
- 消息分为消息头和消息体,消息头包含消息总长度(或者消息体长度),通常采用,消息头第一个字段采用int32来表示消息的总长度.
- 更复杂的应用层设计

#### 3.3 Netty 的变加码器

- 解决TCP粘包拆包 : 

LineBasedFrameDecoder + StringDecoder : 

>LineBasedFrameDecoder 判断是否有\n,\r\n , 如果有,就以位置为结束位置,此时组成一行,如果连续读取到最大长度后仍然没有发现换行符,就会抛出异常,同时忽略掉之前读到的异常码流.(即使没有换行符,Netty也提供了多种支持 TCP粘包/拆包的解码器)
>
>StringDecoder : 将收到的对象转换成字符串,然后继续调用后面的handler


## 四. Event & Handler 


#### 4.1 入站事件：
- 通道激活和停用
- 读操作事件
- 异常事件
- 用户事件


#### 4.2 出站事件：
- 打开连接
- 关闭连接
- 写入数据
- 刷新数据

#### 4.3 事件处理程序接口: 
- ChannelHandler
- ChannelOutboundHandler
- ChannelInboundHandler

#### 4.4 适配器（空实现，需要继承使用）：
- ChannelInboundHandlerAdapter
- ChannelOutboundHandlerAdapter


#### 4.3 Netty 应用组成: 
- 网络事件
- 应用程序逻辑事件
- 事件处理程序

## 五. Netty 优化
- 不要阻塞 EventLoop
- 系统参数优化 : 
	-	ulimit -a /proc/sys/net/ipv4/tcp_fin_timeout : 调低端口释放后的等待时间， 默认为60s， 修改为15~30s
	-	TcpTimedWaitDelay
- 缓冲区优化 : SO_RCVBUF/SO_SNDBUF/SO_BACKLOG/ REUSEXXX
- 心跳周期优化 : 心跳机制与断线重连
- 内存与 ByteBuffer 优化 : DirectBuffer与HeapBuffer
- 其他优化
	- ioRatio : 
	- Watermark : 高低水位
	- TrafficShaping : [流量整形](https://www.jianshu.com/p/bea1b4ea8402)

```java

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(BOSS_THREAD_COUNT);
        EventLoopGroup workGroup = new NioEventLoopGroup(WORK_THREAD_COUNT);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            //当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。如果未设置或所设置的值小于1，Java将使用默认值50。
            bootstrap.option(ChannelOption.SO_BACKLOG, 128)
                    //禁用了Nagle算法 , 去掉演延时发包
                    .option(ChannelOption.TCP_NODELAY, true)
                    //启动心跳 : 当设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文。
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    //允许重复使用本地地址和端口
                    .option(ChannelOption.SO_REUSEADDR, true)
                    //接收缓冲区
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                    //发送缓冲区
                    .option(ChannelOption.SO_SNDBUF, 12 * 1024)
                    //支持多个进程或者线程绑定到同一端口
                    .option(EpollChannelOption.SO_REUSEPORT, true)
                    //对于boos创建出来的workEventGroup也保持心跳
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //使用PooledByteBufAllocator来分配内存 : 共享内存
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpInboundInitializer(this.proxyServer));
            Channel channel = bootstrap.bind(port).sync().channel();
            logger.info("开启netty http inbound 服务 , 监听地址和端口为 http://127.0.0.1 : " + port + "/");
            channel.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

```







# 参考资料

- Netty权威指南
- [tcp连接设置](https://blog.csdn.net/weixin_33825683/article/details/92644122)