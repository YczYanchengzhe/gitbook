# SQL函数



## 一.什么是SQL函数

 SQL 中的函数一般是在数据上执行的，可以很方便地转换和处理数据。一般来说，当我们从数据表中检索出数据之后，就可以进一步对这些数据进行操作，得到更有意义的结果，比如返回指定条件的函数，或者求某个字段的平均值等。 



## 二. 常用的 SQL 函数 

### 1.算术函数 

| 函数名 | 定义 |
| :---: | :---: |
| ABS() | 取绝对值 |
| MOD() | 取余 |
| ROUND() | 四舍五入为指定位数的小数,需要有两个参数,分别为字段名称,小数位数 |
| CEIL() | 向上取整 |
| FLOOR() | 向下取整 |
| Cast() | 类型转换Cast(字段名 as 转换的类型 ) |

```sql
-- 运行结果为 2。
SELECT ABS(-2)
-- 运行结果 2。
SELECT MOD(101,3)，
-- 运行结果 37.3。
SELECT ROUND(37.25,1)，
```

### 2.字符串函数
| 函数名 | 定义 |
| :---: | :---: |
| CONCAT() | 拼接多个字符串 |
| LENGTH() | 计算字段的长度 , 一个汉子三个字节 , 一个数字或者字母一个字节 |
| CHAT_LENGTH() | 计算字段的长度 , 一个汉子,数字,字母都是一个字节 |
| LOWER() | 字符串中的字符转换为小写 |
| UPPER() | 字符串中的字符转换为大写 |
| REPLACE() | 替换函数,参数(要替换的表达式或者字段名,要查找的被替换的字符串,替换成什么字符串) |
| SUBSTRING() | 截取字符串,参数(要截取的表达式或者字段名,开始截取的位置,想要截取的长度) |

```sql
-- 运行结果为 abc123
SELECT CONCAT('abc', 123)
-- 运行结果为 6
SELECT LENGTH('你好')
-- 运行结果为 2
SELECT CHAR_LENGTH('你好')
-- 运行结果为 abc。
SELECT LOWER('ABC')
-- 运行结果为 ABC。
SELECT UPPER('abc')
-- 运行结果为 f123d。
SELECT REPLACE('fabcd', 'abc', 123)
-- 运行结果为 fab。
SELECT SUBSTRING('fabcd', 1,3)
```

### 3.日期函数
> 注意 :  DATE 日期格式必须是 yyyy-mm-dd 的形式  , 中间的分割符号可以不用`-`.


| 函数名 | 定义 |
| :---: | :---: |
| CURRENT_DATE() | 系统当前日期 |
| CERRENT_TIME() | 系统当前时间 , 没有具体日期 |
| CERRENT_TIMESTAMP() | 系统当前时间 , 包括具体日期+时间 |
| DATE() | 返回时间的日期部分 |
| YEAR() | 返回时间的年份部分 |
| MONTH() | 返回时间的月份部分 |
| DAY() | 返回时间的天数部分 |
| HOUR() | 返回时间的小时部分 |
| MINUTE() | 返回时间的分钟部分 |
| SECOND() | 返回时间的秒数部分 |

```sql
-- 运行结果为 2019-04-03。
SELECT CURRENT_DATE()
-- 运行结果为 21:26:34。
SELECT CURRENT_TIME()
-- 运行结果为 2019-04-03 21:26:34。
SELECT CURRENT_TIMESTAMP()
-- 运行结果为 2019。
SELECT EXTRACT(YEAR FROM '2019-04-03')
-- 运行结果为 2019-04-01。
SELECT DATE('2019-04-01 12:00:05')
```

### 4. 转换函数
> 转换函数 : 可以转换数据之间的类型

| 函数名 | 定义 |
| :---: | :---: |
| CAST() | 数据类型转换 ,参数是一个表达式<br>通过AS关键字分割两个参数 , 分别是原始数据和目标数据类型 |
| COALESCE() | 返回第一个非空数值 |

```sql
-- 运行结果会报错 : 小数不能转换为int
SELECT CAST(123.123 AS INT)
-- 运行结果为 123.12。
SELECT CAST(123.123 AS DECIMAL(8,2))
-- 运行结果为 1。
SELECT COALESCE(null,1,2)
```

>  CAST 函数在转换数据类型的时候，不会四舍五入，如果原数值有小数，那么转换为整数类型的时候就会报错。 
>
>  不过可以指定转化的小数类型，在 MySQL 和 SQL Server 中，你可以用DECIMAL(a,b)来指定，其中 a 代表整数部分和小数部分加起来最大的位数，b 代表小数位数 .



### 5.聚集函数

| 函数 | 说明 |
| :----: | :---: |
| COUNT() | 总行数 |
| MAX() | 最大值 |
| MIN() | 最小值 |
| SUM() | 求和 |
| AVG() | 平均值 |

>  COUNT(role_assist)会忽略值为 NULL 的数据行，而 COUNT(*) 只是统计数据行数，不管某个字段是否为 NULL。 
>
>  AVG、MAX、MIN 等聚集函数会自动忽略值为 NULL 的数据行 

### 6.例子

```sql
-- 显示英雄以及他的物攻成长 , 精确到小数后一位
SELECT name, ROUND(attack_growth,1) FROM heros
-- 最大生命值最大的是哪个英雄
select * from heros where hp_max=(select max(hp_max) from heros)
-- 错误示例 : 查出来的 name 和hp_max并不匹配
SELECT NAME , MAX(hp_max) FROM heros
-- 英雄上线日期（对应字段 birthdate）的年份，只显示有上线日期的英雄即可（有些英雄没有上线日期的数据，不需要显示）
select name, EXTRACT(YEAR from birthdate) as birthyear from heros where birthdate is not null;
-- 查询 2016-10-12 日期之后上线的人物
select name from heros where DATE(birthdate)>'2016-10-12'
```

## 三. 使用 SQL 函数可能带来的问题
>我们在使用 SQL 语言的时候，不是直接和这门语言打交道，而是通过它使用不同的数据库软件，即 DBMS。DBMS 之间的差异性很大，远大于同一个语言不同版本之间的差异。实际上，只有很少的函数是被 DBMS 同时支持的。比如，大多数 DBMS 使用（||）或者（+）来做拼接符，而在 MySQL 中的字符串拼接函数为Concat()。大部分 DBMS 会有自己特定的函数，这就意味着采用 SQL 函数的代码可移植性是很差的，因此在使用函数的时候需要特别注意。



## 四. 关于大小写的规范 

- 关键字和函数名称全部大写；

- 数据库名、表名、字段名称全部小写；

- SQL 语句必须以分号结尾。 

```sql
-- 不同的生命最大值的英雄数量是多少。
SELECT COUNT(DISTINCT hp_max) FROM heros;
```



