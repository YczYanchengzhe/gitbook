# 常见问题整理

## 1.JDBC 是如何设置连接的可读属性的?

一般来说实现方式是通过设置连接只读来进行处理的 , 通过阅读 jdbc 源码发现,在设置连接只读之后,会执行一句 sql,gaisql 为 : `set session transaction read only`,对于只读的场景该 sql 是没有问题的,但是如果是读写的场景,那么就会有问题了,会导致执行的 sql 从一个变成 2 个,在并发高的时候,影响系统的吞吐量.


```java
public void setReadOnly(boolean readOnlyFlag) throws SQLException {
        checkClosed();

        setReadOnlyInternal(readOnlyFlag);
    }

    public void setReadOnlyInternal(boolean readOnlyFlag) throws SQLException {
        // note this this is safe even inside a transaction
        if (versionMeetsMinimum(5, 6, 5)) {
            if (!getUseLocalSessionState() || (readOnlyFlag != this.readOnly)) {
                execSQL(null, "set session transaction " + (readOnlyFlag ? "read only" : "read write"), -1, null, DEFAULT_RESULT_SET_TYPE,
                        DEFAULT_RESULT_SET_CONCURRENCY, false, this.database, null, false);
            }
        }

        this.readOnly = readOnlyFlag;
    }
```

