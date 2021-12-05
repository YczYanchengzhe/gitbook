# Pulsar 项目学习准备

- 参考官方文档 :  [链接](https://pulsar.apache.org/docs/zh-CN/next/standalone-docker/)
- 运行环境 : Mac - m1 - docker 单机模式

# 一. Docker 运行 - 测试
## Docker 环境部署单机模式 pulsar
```shell
# 拉取镜像
docker pull apachepulsar/pulsar:2.8.1
# 启动
docker run -it \
  -p 6650:6650 \
  -p 8080:8080 \
  --mount source=pulsardata,target=/pulsar/data \
  --mount source=pulsarconf,target=/pulsar/conf \
  apachepulsar/pulsar:2.8.1 \
  bin/pulsar standalone
```

## 本地启动客户端发送消息

#### 导入依赖包
```xml
				<pulsar.version>2.8.1</pulsar.version>
        <dependency>
            <groupId>org.apache.pulsar</groupId>
            <artifactId>pulsar-client</artifactId>
            <version>${pulsar.version}</version>
        </dependency>
```

#### 测试 Demo
```java
public class Demo {

	@SuppressWarnings("all")
	public static void main(String[] args) throws PulsarClientException {
		PulsarClient client = PulsarClient.builder()
				.serviceUrl("pulsar://localhost:6650")
				.build();

		new Thread(()->{
			while (true) {
				try {
					Thread.sleep(1000);
					send(client);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(()->{
			try {
				recv(client);
			} catch (PulsarClientException e) {
				e.printStackTrace();
			}
		}).start();

	}

	@SuppressWarnings("rawtypes")
	private static void recv(PulsarClient client) throws PulsarClientException {
		Consumer consumer = client.newConsumer()
				.topic("my-topic")
				.subscriptionName("my-subscription")
				.subscribe();
		while (true) {
			// Wait for a message
			Message msg = consumer.receive();

			try {
				// Do something with the message
				System.out.println("Message received: " + new String(msg.getData()));

				// Acknowledge the message so that it can be deleted by the message broker
				consumer.acknowledge(msg);
			} catch (Exception e) {
				// Message failed to process, redeliver later
				consumer.negativeAcknowledge(msg);
			}
		}
	}

	private static void send(PulsarClient client) throws PulsarClientException {
		Producer<byte[]> producer = client.newProducer()
				.topic("my-topic")
				.create();

		// You can then send messages to the broker and topic you specified:
		producer.send("My message 123".getBytes());
		producer.close();
	}
}
```

#### 测试输出日志
```log
Message received: My message 123
Message received: My message 123
Message received: My message 123
Message received: My message 123
Message received: My message 123
```


# 二. 项目打包
#### 镜像仓库配置  -aliyun
```xml
       <mirror>
            <id>nexus-aliyun</id>
            <mirrorOf>central</mirrorOf>
            <name>Nexus aliyun</name>
            <url>http://maven.aliyun.com/nexus/content/groups/public</url>
        </mirror>
```
#### 打包脚本
```shell
# 拉取项目
git clone git@github.com:apache/pulsar.git
# 本地编译打包 : 这里因为使用的是 mac 的m1 芯片在打包 pb 时候需要指定系统
mvn install -DskipTests -Dos.detected.classifier=osx-x86_64
# 最小化构建 : 这会跳过大多数外部连接器和分层存储处理程序
mvn install -Pcore-modules,-main
```

# 三. 模块拆分
## 3.1 客户端相关
- Pulsar Client :: API  : 用于客户端访问
- Pulsar Client Admin :: API : 管理员管理工具,可管理多个 Broker
- Pulsar Client Java : java 客户端
- Pulsar Client Admin Original : 
- Pulsar WebSocket : websocket 支持
- Pulsar Client Tools
- Pulsar Client Tools Test
- Pulsar Test Client


## 3.2 公共包
- Pulsar Common : 公共包
- Pulsar Metadata : 元数据抽象
- Managed Ledger : 消息流的抽象
- Pulsar ZooKeeper Utils : 访问 zk 的工具
- pulsar-config-validation : 配置验证


## 3.3 事务相关
- Pulsar Transaction :: Parent : 事务相关
- Pulsar Transaction :: Common : 事务相关
- Pulsar Transaction :: Coordinator


## 3.4 加密相关
- Apache Pulsar :: Bouncy Castle :: Parent : 加密相关
- Apache Pulsar :: Bouncy Castle :: BC : 加密工具
- Apache Pulsar :: Bouncy Castle :: BC-FIPS
- pulsar-client-messagecrypto-bc : 加密相关
- pulsar-client-auth-sasl
- pulsar-broker-auth-sasl
- Pulsar Bouncy Castle FIPS Test


## 3.5 Function 相关
- Pulsar Functions :: Parent : pulsar function 相关功能,场景如基于内容的路由
- Pulsar Functions :: Proto : pulsar function 相关功能,场景如基于内容的路由
- Pulsar Functions :: API : 
- Pulsar Functions :: Utils
- Pulsar Functions :: Secrets
- Pulsar Functions :: Instance
- Pulsar Functions :: Runtime
- Pulsar Functions :: API Examples
- Pulsar Functions :: Worker 
- Pulsar Functions :: Local Runner original
- Pulsar Functions :: Runtime All

## 3.6 package management 相关
- Apache Pulsar :: Package Management : 版本管理模块,并将简化对 Function、Sink 和 Source 的升级回滚过程
- Apache Pulsar :: Package Management :: Core : 版本管理模块,并将简化对 Function、Sink 和 Source 的升级回滚过程
- Apache Pulsar :: Package Management :: BookKeeper Storage
- Apache Pulsar :: Package Management :: Filesystem Storage

  
## 3.7 io 相关
- Pulsar IO :: Parent : pulsar 连接器
- Pulsar IO :: IO Common
- Pulsar IO :: IO
- Pulsar IO :: Batch Discovery Triggerers
- Pulsar IO :: Cassandra
- Pulsar IO :: Twitter
- Pulsar IO :: Batch Data Generator
- Pulsar IO :: Data Generator


## 3.8 broker 相关

- Pulsar Broker : broker负责处理从生产者发出消息、向消费者派发消息、在集群间复制数据等
- pulsar-broker-common : broker 公共包

## 3.9 proxy 相关
- Pulsar Proxy : 代理服务起到了反向代理的作用，它在所有的 broker 前面创建了一个网关。


## 3.10 Distribution相关
- Pulsar :: Distribution : 路由
- Pulsar :: Distribution :: Server

# 四. 功能点梳理

