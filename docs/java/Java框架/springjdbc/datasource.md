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

- 系统属性配置
```properties
druid.stat.logSlowSql=true
druid.stat.slowSqlMillis=3000
```

- Springboot
```properties
spring.datasource.druid.filter.stat.enable=true
spring.datasource.druid.filter.stat.log-slow-sql=true
spring.datasource.druid.filter.stat.slow-sql-millis=3000
```

### 关于 Druid 使用的 注意事项
- 没有特殊情况,不要再生产环境打开监控的 Servlet ,生产环境集群化部署,存在安全隐患,并且查看并不方便
- 没有链接泄露可能的情况下,不要开启 : removeAbandoned , 对性能有很大影响
- testXxx 相关配置使用注意 , 会影响性能
- 务必配置合理的超时时间.

## 三. 连接池选择

- 可靠性
- 性能
- 功能
- 
- 可运维性
- 可扩展性
- 数据加密 : 密码加密
- 社区活跃度
- 等等



















