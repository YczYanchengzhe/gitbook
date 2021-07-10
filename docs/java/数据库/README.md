# 工作相关知识整理

> 不积跬步无以至千里 , 不积小流无以成江海!
> 拼搏吧,骚年!
> 参阅所有资料都要抱着他可能是错误的态度来阅读



#### 1. orde by (函数) 部分正序部分倒叙

```sql
-- id < 5 id倒叙 , id > 5 id正序
order by (id<5 , -id , id)  
```

#### 2. MySQL数据转换问题

mysql会隐式转换类型 , 此时会不走索引 , 结果可能是错的 . 

敏感信息不要直接写在sql里面

#### 3. count(1)、count(*)与count(列名)的执行区别

> 阅读高性能MySQL找到答案

#### 4. MySQL —— 为什么需要主键？主键为什么最好是单调递增的？
https://blog.csdn.net/itworld123/article/details/103004407

#### 5. 页分裂
单调增加的趋势对现有的数据块影响最小 , 否则可能需要在两块之间增加数据
https://zhuanlan.zhihu.com/p/98818611

#### 6. 添加索引与锁表
https://www.jianshu.com/p/4abc30dfad01

#### 7. 聚集索引和二级索引
主键的B+树 关联的都是数据 , 其他索引关联的都是主键

#### 8. 索引的最左原则

#### 9. 索引尽量区分度高一些
可以尝试使用这个公式来计算 : DISTINCT(name)/count(*)

#### 10. 需要注意索引冗余

#### 11. 修改表结构的危害
索引重建 , 锁表 , 抢占资源 , 主从延时

建议增加子表 , 不增加字段

#### 12. 
批量插入 , 大批量写入优化 , 一次插入插入多条 , 可以增加执行速度 , 但是对于sql解析引擎 和sql中间件不友好.
查询也可以使用批量的方式

#### 13. Load Data
直接导入数据 , 批量导入最快 , 在导入之前最好不创建索引 , 再创建之后再统一新建索引

#### 14. 
更新操作 , 控制范围 , 否则容易锁混乱

#### 15. 
like 只支持前缀匹配 , 前面加了 % 是用不到索引的

#### 16.
连接 : 小表驱动大表 避免笛卡尔积

#### 17. 索引失效的情景
索引失效 : NULL , not , not in , 函数

#### 18. 
减少使用or , 可以使用 union(注意 union all 区别)

#### 19.
必要时使用 force index 强制查询走某个索引

#### 20. 查询SQL设计
1. 查询数据量和查询次数平衡
2. 避免不必须的大量重复数据传输
3. 避免使用临时文件排序或临时表
4. 分析类需求 , 可以使用汇总表

#### 21. 单机MySQL数据库的几个问题
1. 容量有限 , 难以扩容
2. 读写压力
3. 可用性不足

#### 22. mysql 命令行
create table t2 like t1; # 可以跨库
show create table columns; # 查看表sql语句
\G  : kv 形式显示
create schema test;


#### 23. ClicnkHouse
ClicnkHouse  :  https://clickhouse.tech/docs/zh/#cpu
数据库管理系统 , 在OLAP领域受到业内青睐
优点 : 
完备的DBMS功能 , 关系模型与SQL查询 , 基于 副本+分片 ,实现线性和高可靠 , 列式存储 , 表引擎 , 向量化引擎(单指令操作多条数据,在cpu层面操作多条数据)
缺点 : 不支持事务



#### 24. MySQL启动报：[ERROR] The server quit without updating PID file

https://blog.csdn.net/ydyang1126/article/details/72473828


# 参考资料
