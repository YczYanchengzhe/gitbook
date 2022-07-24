# 为什么我的MySQL会“抖”一下？

## 脏页和干净页
当内存数据页跟磁盘数据页内容不一致的时候，我们称这个内存页为“脏页”。内存数据写入到磁盘后，内存和磁盘上的数据页的内容就一致了，称为“干净页”。

## 刷脏页的场景
- 场景一 : InnoDB 的 redo log 写满了。这时候系统会停止所有更新操作，把 checkpoint 往前推进，redo log 留出空间可以继续写。
- 场景二 : 系统内存不足。当需要新的内存页，而内存不够用的时候，就要淘汰一些数据页，空出内存给别的数据页使用。如果淘汰的是“脏页”，就要先将脏页写到磁盘。
- 场景三 : MySQL 认为系统“空闲”的时候 , 向磁盘刷脏页
- 场景四 : MySQL 正常关闭的情况。这时候，MySQL 会把内存的脏页都 flush 到磁盘上

## 可能导致 MySQL 卡顿的场景

### 场景一
出现之后,写入速度会跌为 0

### 场景二
InnoDB 用缓冲池（buffer pool）管理内存，缓冲池中的内存页有三种状态：
- 还没有使用的 : 在内存不足时,如果还有没有使用的,会直接使用
- 干净页 : 内存不足时候,可以直接丢弃
- 脏页 : 需要刷盘 ,此时会有卡顿

## InnoDB 刷脏页的控制策略
### 查看磁盘的随机读写能力
```shell
fio -filename=$filename -direct=1 -iodepth 1 -thread -rw=randrw -ioengine=psync -bs=16k -size=500M -numjobs=10 -runtime=10 -group_reporting -name=mytest 
```

### 脏页比例
**平时要多关注脏页比例，不要让它经常接近 75%。**
```sql
-- 选择数据库 , 8.0.28 mysql server
use performance_schema;
select VARIABLE_VALUE into @a from global_status where VARIABLE_NAME = 'Innodb_buffer_pool_pages_dirty';
select VARIABLE_VALUE into @b from global_status where VARIABLE_NAME = 'Innodb_buffer_pool_pages_total';
select @a/@b;
```

### 连坐机制
在准备刷一个脏页的时候，如果这个数据页旁边的数据页刚好是脏页，就会把这个“邻居”也带着一起刷掉；而且这个把“邻居”拖下水的逻辑还可以继续蔓延，也就是对于每个邻居数据页，如果跟它相邻的数据页也还是脏页的话，也会被放到一起刷。

```sql
-- 在 MySQL 8.0 中，innodb_flush_neighbors 参数的默认值已经是 0 了。
SHOW GLOBAL VARIABLES like 'innodb_flush_neighbors' ;
```

