# 数据源

## 一. HikariCp 数据源

### HikariCp 为什么这么[快](https://github.com/brettwooldridge/HikariCP/wiki/Down-the-Rabbit-Hole)

1. 字节码级别优化 : 

   很多方法都是通过 JavaAssist 生成的

2. 大量的小优化

- 使用 FastStatementList 替换 ArrayList
- 无锁集合 ConcurrentBag
- 代理类的优化 : 使用 invokestatic 代替 invokevirtual.

### 使用

**Spring Boot 2.X**

- 默认使用 HikariCp
- 配置 spring.datasource.hikari.*

**Spring Boot 1.X**

- 默认使用 tomcat 连接池 ,需要移除 tomcat-jdbc 依赖
- spring.datasource.type=com.zaxxer.hikari.HikariDataSource

**相关[配置](https://github.com/brettwooldridge/HikariCP#rocket-initialization)**


```properties
spring.datasource.hikari.maximumPoolSize=5
spring.datasource.hikari.minimumIdle=5
spring.datasource.hikari.idleTimeout=600000
spring.datasource.hikari.connectionTimeout=30000
spring.datasource.hikari.maxLifetime=1800000
```

## 二. [Druid](https://github.com/alibaba/druid) 数据源


### 实用功能
- 详细的监控
- ExceptionSorter ,针对主流数据库的返回码都支持
- SQL 防注入
- 内置加密配置
- 众多扩展点,方便进行定制

### 使用
- druid-spring-boot-starter
- spring.datasource.druid.*

```properties
# 数据源连接
spring.datasource.url=jdbc:h2:mem:foo
# 用户名
spring.datasource.username=sa
# 密码
spring.datasource.password=

spring.datasource.druid.initial-size=5
spring.datasource.druid.max-active=5
spring.datasource.druid.min-idle=5
# 过滤器
spring.datasource.druid.filter.config.enabled=true
# 支持自定义 filter , conn 是自定义的 , 其他是内置的,详情可以参考官方文档
spring.datasource.druid.filters=config,stat,slf4j,conn

spring.datasource.druid.connection-properties=config.decrypt=true
spring.datasource.druid.test-on-borrow=true
spring.datasource.druid.test-on-return=true
spring.datasource.druid.test-while-idle=true
```


## 三. 连接池选择

- 可靠性
- 性能
- 功能
- 客运卫星
- 可扩展性
- 数据加密 : 密码加密
- 社区活跃度
- 等等



















