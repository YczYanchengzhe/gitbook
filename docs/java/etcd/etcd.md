# ETCD 

## 一. ETCD简介
- [项目地址](https://github.com/etcd-io/etcd)

#### 1.ETCD是什么?

按照官方文档的解释 : etcd是用于分布式系统最关键数据的分布式可靠键值存储.

#### 2.ETCD优点

> 简单：定义明确，面向用户的API（gRPC）
安全：具有可选客户端证书身份验证的自动TLS
快速：基准10,000次写入/秒
可靠：使用Raft正确分发

## 二. 如何部署ETCD

以三个节点为例 : 
```shell
172.29.200.111
nohup ./etcd --name etcd1 --initial-advertise-peer-urls http://172.29.200.111:2380 --listen-peer-urls http://172.29.200.111:2380 --listen-client-urls http://172.29.200.111:2379,http://127.0.0.1:2379 --advertise-client-urls http://172.29.200.111:2379 --initial-cluster-token etcd-cluster-1 --initial-cluster etcd1=http://172.29.200.111:2380,etcd2=http://172.29.200.109:2380,etcd3=http://172.29.200.108:2380 --initial-cluster-state new > ../log/etcd.log&

172.29.200.109
nohup ./etcd --name etcd2 --initial-advertise-peer-urls http://172.29.200.109:2380 --listen-peer-urls http://172.29.200.109:2380 --listen-client-urls http://172.29.200.109:2379,http://127.0.0.1:2379 --advertise-client-urls http://172.29.200.109:2379 --initial-cluster-token etcd-cluster-1 --initial-cluster etcd1=http://172.29.200.111:2380,etcd2=http://172.29.200.109:2380,etcd3=http://172.29.200.108:2380 --initial-cluster-state new > ../log/etcd.log&

172.29.200.108
nohup ./etcd --name etcd3 --initial-advertise-peer-urls http://172.29.200.108:2380 --listen-peer-urls http://172.29.200.108:2380 --listen-client-urls http://172.29.200.108:2379,http://127.0.0.1:2379 --advertise-client-urls http://172.29.200.108:2379 --initial-cluster-token etcd-cluster-1 --initial-cluster etcd1=http://172.29.200.111:2380,etcd2=http://172.29.200.109:2380,etcd3=http://172.29.200.108:2380 --initial-cluster-state new > ../log/etcd.log&
```

## 三.Java如何使用ETCD

```xml
        <dependency>
            <groupId>org.mousio</groupId>
            <artifactId>etcd4j</artifactId>
            <version>${etcd4j.version}</version>
        </dependency>
```

```java
	/**
	 * 根据etcd链接创建客户端
	 * @param connectConfig
	 * @return
	 */
	public EtcdClient getEtcdClient(String connectConfig) {
		List<URI> uris = parse(connectConfig);
		return new EtcdClient(uris.toArray(new URI[]{}));
	}
	/**
	 * 进行ip：port ： parse
	 *
	 * @param config 
	 * @return
	 */
	public static List<URI> parse(String config) {
		List<URI> uris = new ArrayList<>();
		String[] ipPorts = config.split(",");
		for (String address : ipPorts) {
			String ip = address.split(":")[0];
			String port = address.split(":")[1];
			uris.add(URI.create(new StringBuilder("http://").append(ip)
					.append(":")
					.append(port)
					.toString()));
		}
		return uris;
	}
	@Test
	public void testClient() throws Exception {
		MyEtcdClient myEtcdClient = new MyEtcdClient();
        //填写ETCD集群的hostPort
		EtcdClient client = myEtcdClient.getEtcdClient("127.0.0.1:2187");
		String sendKey = "aaa";
		String sendValue = "aaa";

		response = client.put(sendKey, sendValue).send().get();

		response = client.get("aaa").send().get();

		String returnValue = response.getNode().getValue();
		client.delete(sendKey);

		Assert.assertEquals(sendValue, returnValue);
	}
```

## 四.ETCD 权限校验

添加权限检验流程 , 使用用户名+密码的方式,参考版本 :  `etcd-v3.2.1-linux-amd64.tar`

```shell
# 添加root用户,必须先添加root用户
./etcdctl user add root:123456
# 查看当前所有用户，确认是否添加成功
./etcdctl user list  
# 查看当前角色信息，确认是否有root角色，正常添加root用户之后会自动添加root角色
./etcdctl user role
# 开启身份验证
./etcdctl auth enable
# 关闭身份验证 因为此时因为已经开启权限验证 ，所以需要输入用户名密码才可以关闭
./etcdctl --username  root:rootpwd  auth disable 
# 需要注意 ，此时虽然开启了身份验证，但是没有身份验证的用户还是可以访问的，因为etcd会在开启之后默认创建一个角色，角色名字guest，
# 在没有用户密码链接时候使用的是该角色，默认具有所有权限，所以需要回收权限
##-------------
# 线上机器升级 ： 之前默认走的guest角色，需要切换使用用户名密码指定另一个角色，例如root ，注意，此时连接不要使用错误的用户名和密码，否则会连接失败。
##-------------
# 回收guest的权限
./etcdctl --username root:rootpwd role revoke guest --path '/*' --rw
# 如果出现问题，重新赋予guest权限，保证可以访问
./etcdctl --username root:rootpwd role grant guest --path '/*' --rw
# 回收权限之后 ， 默认的无用户名密码的情况下就无法访问了
```



## 五.待整理
https://alexstocks.github.io/html/etcd.html
https://www.cnblogs.com/laoqing/p/8967549.html