# Java 基础



# 一. 相关错误
## 1. junit-vintage 错误
#### (1) 错误原因 : 
`junit-vintage-engine` 是 JUnit 4 中使用的测试引擎。
`junit-jupiter-engine` 是 JUnit 5 中使用的测试引擎。
#### (2) 修复
如果你的 Spring 项目使用的新的 Spring Boot 版本的话，你应该默认使用了 JUnit 5 的引擎，因此为了兼容性，你需要在 spring-boot-starter-test 这个 POM 引用的时候将 JUnit 4 的引擎去除掉。
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```

# 二. 相关知识
自动化测试框架 : Jenknis + testng+ JBehave + serenity+Java

# 参考资料

- [1] [ JUnit 5 测试 Spring 引擎的时候提示 junit-vintage 错误](https://www.cnblogs.com/huyuchengus/p/13784721.html)
