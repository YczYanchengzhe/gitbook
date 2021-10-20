# Spring 的异常抽象

## 一. 错误码管理
- sql-error-codes.xml  : 为每一个数据库都定义了一个 bean,用来管理错误码.

定制错误码解析逻辑

## 二. 自定义错误处理

### 2.1 第一种处理方式

- 配置错误码定制处理

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN 2.0//EN" "https://www.springframework.org/dtd/spring-beans-2.0.dtd">

<beans>
    <bean id="H2" class="org.springframework.jdbc.support.SQLErrorCodes">
        <property name="badSqlGrammarCodes">
            <value>42000,42001,42101,42102,42111,42112,42121,42122,42132</value>
        </property>
        <property name="duplicateKeyCodes">
            <value>23001,23505</value>
        </property>
        <property name="dataIntegrityViolationCodes">
            <value>22001,22003,22012,22018,22025,23000,23002,23003,23502,23503,23506,23507,23513</value>
        </property>
        <property name="dataAccessResourceFailureCodes">
            <value>90046,90100,90117,90121,90126</value>
        </property>
        <property name="cannotAcquireLockCodes">
            <value>50200</value>
        </property>
        定制化异常
        <property name="customTranslations">
            <bean class="org.springframework.jdbc.support.CustomSQLErrorCodesTranslation">
                <property name="errorCodes" value="23001,23505"/>
                <property name="exceptionClass" value="com.study.exception.CustomDuplicatedKeyException"/>
            </bean>
        </property>
    </bean>
</beans>
```

- 异常类

```java
public class CustomDuplicatedKeyException extends DuplicateKeyException {

	public CustomDuplicatedKeyException(String msg) {
		super(msg);
		System.out.println("CustomDuplicatedKeyException error : " + msg);
	}

	public CustomDuplicatedKeyException(String msg, Throwable cause) {
		super(msg, cause);
		System.out.println("CustomDuplicatedKeyException error : " + msg);
	}
}
```

- 测试 case

```java
	@Autowired
	private JdbcTemplate jdbcTemplate;


	@Test(expected = CustomDuplicatedKeyException.class)
	public void testThrowingCustomException() {
		jdbcTemplate.execute("INSERT INTO FOO (ID,BAR) VALUES (1,'a')");
		jdbcTemplate.execute("INSERT INTO FOO (ID,BAR) VALUES (1,'b')");
	}
```

### 2.2 第二种处理方式
- 配置
```java
@Component
public class CustomSQLExceptionSubclassTranslator extends SQLExceptionSubclassTranslator {
	@Override
	protected DataAccessException doTranslate(String task, String sql, SQLException ex) {
		if (ex.getErrorCode() == 23001 || 23505 == ex.getErrorCode()) {
			return new CustomDuplicatedKeyException(" sql dup  : " + sql);
		} else {
			return super.doTranslate(task, sql, ex);
		}
	}
}
```

- 测试

```java
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private CustomSQLExceptionSubclassTranslator customSQLExceptionSubclassTranslator;

	@Test(expected = CustomDuplicatedKeyException.class)
	public void testThrowingCustomException2() {
		jdbcTemplate.setExceptionTranslator(customSQLExceptionSubclassTranslator);

		jdbcTemplate.execute("INSERT INTO FOO (ID,BAR) VALUES (1,'a')");
		jdbcTemplate.execute("INSERT INTO FOO (ID,BAR) VALUES (1,'b')");
	}
```



  

  

  