# MyBatis

- MyBatis : https://github.com/mybatis/mybatis-3
- mybatis spring : https://github.com/mybatis/spring
- mybatis spring starter : https://github.com/mybatis/spring-boot-starter
- mybatis generator : http://mybatis.org/generator/
- mybatis pagehelper : https://pagehelper.github.io/

## 选择
- 对于简单的 JDBC 操作,可以使用 JPA 的方式,不用担心 SQL 和映射的关系.
- 如果我们的 DBA 对我们的 SQL 有比较高的要求,每条 SQL 都要经过 DBA 的审核,可以使用 MyBatis
- SQL 本身比较复杂,需要多表关联,聚合操作等,可以使用 MyBatis

## 相关使用
**配置**

- mybatis.mapper-locations = `classpath*:**.xml`
- mybatis.type-aliases-package = 类型别名的包前缀
- mybatis.type-handlers-package = TypeHandler扫描包名
- mybatis.configuration.map-underscore-to-camel-case = true (下划线转换为驼峰规则)

**定义与扫描**

- @MapperScan 配置扫描的位置
- @Mapper 定义接口
- 映射的定义 -- XML 与注解支持同时配置

- @Options : 使用生成的 key , 并把生成的 key 回填给实体


- insert , update , delete 返回的都是受更新影响的条数

# MyBatis generator

- 注意这里我使用的是 `2.1.2.RELEASE`的 spring, 如果使用最新版本2.4.* 的会出现生成失败的问题

```xml
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.0.0</version>
        </dependency>

        <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>1.3.7</version>
        </dependency>
```

```java
	private void initData() throws Exception {
		List<String> warning = new ArrayList<>();
		ConfigurationParser configurationParser = new ConfigurationParser(warning);
		Configuration configuration = configurationParser.parseConfiguration(this.getClass().getResourceAsStream("/generatorConfig.xml"));
		DefaultShellCallback defaultShellCallback = new DefaultShellCallback(true);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, defaultShellCallback, warning);
		myBatisGenerator.generate(null);
	}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>

    <!--    连接数据库jar包的路径 , jdbc 需要-->
    <!--    <classPathEntry location="/mysql-connector-java/5.1.48/mysql-connector-java-5.1.48.jar"/>-->

    <context id="H2Tables" targetRuntime="MyBatis3">
        <!--        配置是有顺序的 : plugin -> connection -> model -->
        <!--        生成带有fluent风格的model代码-->
        <plugin type="org.mybatis.generator.plugins.FluentBuilderMethodsPlugin" />
        <!--        为生成的Java模型创建一个toString方法-->
        <plugin type="org.mybatis.generator.plugins.ToStringPlugin" />
        <!--        生成的Java模型类添加序列化接口，并生成serialVersionUID字段-->
        <plugin type="org.mybatis.generator.plugins.SerializablePlugin" />
        <!--        可以生成一个新的selectByExample方法，这个方法可以接受一个RowBounds参数，主要用来实现分页-->
        <plugin type="org.mybatis.generator.plugins.RowBoundsPlugin" />
        <!--        连接配置-->
        <jdbcConnection driverClass="org.h2.Driver"
                        connectionURL="jdbc:h2:mem:testdb"
                        userId="sa"
                        password="">
        </jdbcConnection>
        <!-- 生成实体类地址 -->
        <javaModelGenerator targetPackage="com.mybatis.generator.model"
                            targetProject="./src/main/java">
            <property name="enableSubPackages" value="true" />
            <property name="trimStrings" value="true" />
        </javaModelGenerator>
        <!-- 生成map.xml文件 -->
        <sqlMapGenerator targetPackage="mapper"
                         targetProject="./src/main/resources">
            <property name="enableSubPackages" value="true" />
        </sqlMapGenerator>
        <!-- 生成map.xml对应client，也就是接口dao -->
        <javaClientGenerator type="MIXEDMAPPER"
                             targetPackage="com.mybatis.generator.mapper"
                             targetProject="./src/main/java">
            <property name="enableSubPackages" value="true" />
        </javaClientGenerator>
        <!-- table可以有多个,每个数据库中的表都可以写一个table，
            tableName表示要匹配的数据库表,也可以在tableName属性中通过使用%通配符来匹配所有数据库表,
            只有匹配的表才会自动生成文件 -->
        <table tableName="t_coffee" domainObjectName="Coffee" >
            <!-- 数据库表主键 -->
            <generatedKey column="id" sqlStatement="CALL IDENTITY()" identity="true" />
            <columnOverride column="price" javaType="org.joda.money.Money" jdbcType="BIGINT"
                            typeHandler="com.mybatis.generator.handler.MoneyTypeHandler"/>
        </table>
    </context>
</generatorConfiguration>
```

- 生成器相关插件 : https://mybatis.org/generator/reference/plugins.html

# MyBatis PageHelper

```yml
#pagehelper
pagehelper:
  offset-as-page-num: true
  reasonable: true
  page-size-zert: true
  support-methods-arguments: true
```

```java
	@Select("select * from t_coffee order by id")
	List<Coffee> findAllWithRowBounds(RowBounds rowBounds);

	@Select("select * from t_coffee order by id")
	List<Coffee> findAllWithParam(@Param("pageNum") int pageNum,
	                              @Param("pageSize") int pageSize);
```

```java
	private void pageHelperTest() {
		coffeeMapper.findAllWithRowBounds(new RowBounds(1, 3))
				.forEach(coffee -> log.info("RowBounds(1,3) {}", coffee));

		coffeeMapper.findAllWithRowBounds(new RowBounds(2, 3))
				.forEach(coffee -> log.info("RowBounds(2,3) {}", coffee));

		// 返回全部
		coffeeMapper.findAllWithRowBounds(new RowBounds(1, 0))
				.forEach(coffee -> log.info("RowBounds(1,0) {}", coffee));

		coffeeMapper.findAllWithParam(1, 3)
				.forEach(coffee -> log.info("param(1,3) {}", coffee));

		List<Coffee> allWithParam = coffeeMapper.findAllWithParam(2, 3);
		PageInfo<Coffee> pageInfo = new PageInfo<>(allWithParam);
		log.info("page info {}", pageInfo);

	}
```







