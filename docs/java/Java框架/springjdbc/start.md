# Spring framework

## 一. 创建项目

https://start.spring.io/ 

## 二. 常见问题
### 2.1 依赖问题
场景 : 如果不希望自己指定 parent,或者已经有了 parent,那么怎么使用 spring 来管理依赖那?

```xml
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

### 2.2 使用actuator相关信息配置

[参考文档](https://segmentfault.com/a/1190000015309478)


### 2.3 单数据源相关配置
- 配置
```properties
# 控制 actuator 的端点信息
management.endpoints.web.exposure.include=*
# 控制台彩色输出
spring.output.ansi.enabled=ALWAYS

# 数据源连接
spring.datasource.url=jdbc:h2:mem:testdb
# 用户名
spring.datasource.username=sa
# 密码
spring.datasource.password=
# 驱动类 com.mysql.jdbc.Driver 一般不需要填写, spring 会根据数据库类型自动帮我们选择
#spring.datasource.driver-class-name=

# 对于内嵌数据库,初始化相关操作
# 初始化模式 : embedded|always|never
spring.datasource.initialization-mode=always
# 确定初始化配置文件
# 建表
#spring.datasource.schema=schema.sql
# 初始化数据
#spring.datasource.data=data.sql
# 数据库类型 hsqldb | h2 | oracle | mysql | postgresql
spring.datasource.platform=h2
```
- 使用
```java
@Autowired
private JdbcTemplate jdbcTemplate;

private void showData() {
    jdbcTemplate.queryForList("SELECT * FROM FOO")
			.forEach(row -> log.info(row.toString()));
}
```

### 2.4 多数据源配置

- 不同的数据源配置要分开
- 关注每次使用的数据源
  - 当存在多个 dataSource 时候系统如何判断
  - 对应的设施(事务,ORM)如何选择 DataSource

### 2.5 springvoot 的 ApplicationRunner 和 CommandLineRunner
- 参数不同
- 执行顺序不同 : 在 order 一样的时候 ,先执行 ApplicationRunner
- 指定 Order 的情况下 order 越小的先执行


```java
@Component
@Slf4j
@Order(value = 1)
public class BeanA implements ApplicationRunner {
	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("BeanA start");
	}
}
@Component
@Slf4j
@Order(value = 2)
public class BeanB implements CommandLineRunner {
	@Override
	public void run(String... args) throws Exception {
		log.info("BeanB start");
	}
}

@Component
@Slf4j
@Order(value = 2)
public class BeanC implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("BeanC start");
	}
}

```

```txt
BeanA start
BeanC start
BeanB start
```
