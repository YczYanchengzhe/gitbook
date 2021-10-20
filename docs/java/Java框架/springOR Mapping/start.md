# 一. 背景

## 1.1 为什么会有 O/R Mapping

- 对象与关系的范式不匹配

|          |      Object(对象)      |    RDBMS     |
| :------: | :--------------------: | :----------: |
|   粒度   |           类           |      表      |
|   继承   |           有           |     没有     |
|  唯一性  | a == b<br />a.equal(b) |     主键     |
|   关联   |          引用          |     外键     |
| 数据访问 |        逐级访问        | SQL 数量要少 |


## 1.2 Hibernate

- ⼀款开源的对象关系映射（Object / Relational Mapping）框架
- 将开发者从 95% 的常⻅数据持久化⼯作中解放出来
- 屏蔽了底层数据库的各种细节


## 1.3 Java Persistence API (JPA)

**JPA** 为对象关系映射提供了⼀种基于 **POJO** 的持久化模型

- 简化数据持久化代码的开发⼯作
- 为 Java 社区屏蔽不同持久化 API 的差异

# 二.Spring 使用

## 2.1 模块
Spring在保留底层存储特性的同事,提供相对一致的,基于 Spring 的编程模型.

**主要模块 :** 

- Spring Data Commons
- Spring Data JDBC
- Spring Data JPA
- Spring Data Redis
- ......

## 2.2 使用
**Spring 项目使用**

```xml
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.data</groupId>
                <artifactId>spring-data-releasetrain</artifactId>
                <version>Lovelace-SR4</version>
                <type>pom</type>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

**Sptingboot 项目使用**

```xml
<!--        spring data-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
```


## 2.3 相关注解
**实体相关注解** 

- @Entity 
- @MappedSuperclass : 表示当前类是父类,这样不会为它单独生成数据表
- @Table(name)

**主键**

- @ID
  - 	@GenerateValue(strategy, generator)
  - 	@SequenceGenerator(name, sequenceName)

**映射**

- @Column(name, nullable, length, insertable, updatable) :  insertable, updatable 是否可以修改
- @CreationTimestamp,@UpdateTimestamp : 在插入时针对注解的属性对应的日期类型创建默认值
- @JoinTable(name) , @JoinColumn(name) : 多表关联

**关系**

- @OneToOne , @OneToMany , @ManyToOne , @ManyToMany
- @OrderBy 

**枚举**
- @Enumerated : 枚举类的注解

**Repository**

- @NoRepositoryBean : 加了这个注解的实体只是作为一个抽象的父类,不会真的作为可访问的存储库.避免为他创建 bean 实例

## 2.4 根据方法名定义查询
**条件查询**

- find...By.../ read...By... / query...By... / get...By...
- count...By...
- ...OrderBy...[Asc / Desc]
- And / Or / IgnoreCase
- Top / First / Distinct

**分页处理**

- PagingAndSortingRepostory<T, ID>
- Pageable / Sort
- Slice<T> / Page<T>

## 2.5 常见问题
-  问题 : 

```text
org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.bucks.model.CoffeeOrder.items, could not initialize proxy - no Session
```

通过对于执行的方法增加事务来解决 : 

```java
@Transactional
public void test() {
  // doSomeThing
}
```

## 2.6 相关原理

**概述RepostoryBean 是如何创建的?**

- JpaRepositoriesRegistrar : 相关核心类
- 激活了@EnableJpaRepostories
- JpaRepositoryConfigExtension : 配置扩展 ,在这里设置类需要注册为 bean 的类 : JpaRepositoryFactoryBean
- registerRepositoriesIn : 注册 bean 操作
- 继续回到 : JpaRepositoryFactoryBean 再注册完成后会调用该方法afterPropertiesSet
- Lazy.of(this.factory.getRepository) : 懒加载,真正把实例创建出来
- 在this.factory.getRepository 中通过 addAdvice 给其加了拦截器,增加行为

**相关步骤**

**RepositoryBeanDefinitionRegistrarSupport.registerBeanDefinitions**

- 注册 RepositoryBean(类型是 JpaRepositoryFactoryBean)

**RepostoryConfigurationExtensionSupport.getRepositoryConfigurations**

- 取得 Repository 配置

**JpaRepositoryFactory.getTargetRepository**

- 创建了 Repository

**RepositoryFactorySupport.getRepository 添加 Advice**

- DefaultMethodInvokingMethodInterceptor
- QueryExecutorMethodInterceptor

**AdstractJpaQuery.execute 执行具体的查询**

**org.springframework.data.repository.query.parser.Part : 进行语法解析**