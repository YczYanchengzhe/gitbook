# 第二部分 SQL数据过滤



## 一. 比较运算符 

**where语句中的比较运算符如下 :** 

|         含义         |  运算符  |
| :------------------: | :------: |
|         等于         |    =     |
|        不等于        | <> 或 != |
|         小于         |    <     |
|   小于等于(不大于)   | <= 或 !> |
|         大于         |    >     |
|   大于等于(不小于)   | >= 或 !< |
| 在指定的两个数值之间 | BETWEEN  |
|        为空值        | IS NULL  |

>  注意  :  不同的 DBMS 支持的运算符可能是不同的，比如 Access 不支持（!=），不等于应该使用（<>）。在 MySQL 中，不支持（!>）（!<）等 

例子 : 

```sql
-- 查询所有最大生命值大于 6000 的英雄：
SELECT name, hp_max FROM heros WHERE hp_max > 6000
-- 查询所有最大生命值在 5399 到 6811 之间的英雄：
SELECT name, hp_max FROM heros WHERE hp_max BETWEEN 5399 AND 6811
SELECT name, hp_max FROM heros WHERE hp_max IS NULL
```


# 二.逻辑运算符

|     含义     | 运算符 |
| :----------: | :----: |
|     并且     |  AND   |
|     或者     |   OR   |
| 在指定范围内 |   IN   |
|      非      |  NOT   |

例子 : 
> 注意 : 当 WHERE 子句中同时存在 OR , AND 和 () 的时候,一般来说 () 优先级最高，其次优先级是 AND，然后是 OR。

```sql
-- 筛选最大生命值大于 6000，最大法力大于 1700 的英雄，然后按照最大生命值和最大法力值之和从高到低进行排序。
SELECT name, hp_max, mp_max FROM heros WHERE hp_max > 6000 AND mp_max > 1700 ORDER BY (hp_max+mp_max) DESC
-- 最大生命值加最大法力值大于 8000 的英雄，或者最大生命值大于 6000 并且最大法力值大于 1700 的英雄。
SELECT name, hp_max, mp_max FROM heros WHERE (hp_max+mp_max) > 8000 OR hp_max > 6000 AND mp_max > 1700 ORDER BY (hp_max+mp_max) DESC;
-- 最大生命值加最大法力值大于8000或者最大生命大于6000,并且最大法力大于1700的英雄
SELECT name, hp_max, mp_max FROM heros WHERE  ((hp_max+mp_max) > 8000 OR hp_max > 6000)  AND mp_max > 1700 ORDER BY (hp_max+mp_max) DESC
```

```sql
-- 查询主要定位或者次要定位是法师或是射手的英雄，同时英雄的上线时间不在 2016-01-01 到 2017-01-01 之间
SELECT name, role_main, role_assist, hp_max, mp_max, birthdate
FROM heros 
WHERE (role_main IN ('法师', '射手') OR role_assist IN ('法师', '射手')) 
AND DATE(birthdate) NOT BETWEEN '2016-01-01' AND '2017-01-01'
ORDER BY (hp_max + mp_max) DESC
```

# 二.通配符
> 匹配任意字符串出现的任意次数，需要使用（%）通配符
>
> 不同的DBMS 对于通配符的定义不同 , 在Access中使用的是(*)而不是(%).另外对于字符串的搜索可能需要区分大小写,具体是否区分大小写需要考虑不同的DBMS以及他们的配置.
>
> 如果想要匹配单个字符 , 就需要使用 `_` 通配符 ,`%` 和`_`的区别在于 `%` 代表0个或者多个 , `_` 只代表一个字符 . 在 Access 中使用`?`来代替`_` .DB2 中是不支持通配符`_`的
```sql
-- 查找英雄名中包含“太”字的英雄都有哪些：
SELECT name FROM heros WHERE name LIKE '%太%'
-- 想要查找英雄名除了第一个字以外，包含‘太’字的英雄有哪些。
SELECT name FROM heros WHERE name LIKE '_%太%'
```

**注意 :** 在实际操作中 , 建议尽量少用通配符 , 因为他需要消耗数据库更长的时间来进行匹配.即使你对`LIKE`检索的字段进行了索引,索引也会失效. **如果想让索引生效 , 那么`LIKE`后面就不能以`%`开头**,这样可以避免全表扫描. 



## 三. 数据分组

#### 1. group by
```sql
-- 按照英雄的主要定位进行分组，并统计每组的英雄数量
SELECT COUNT(*), role_main FROM heros GROUP BY role_main
-- 按照英雄的主要定位、次要定位进行分组，查看这些英雄的数量，并按照这些分组的英雄数量从高到低进行排序
SELECT COUNT(*) as num, role_main, role_assist FROM heros GROUP BY role_main, role_assist ORDER BY num DESC
```
#### 2. HAVING 过滤分组，与 WHERE 的区别
> HAVING 的作用和 WHERE 一样，都是起到过滤的作用，只不过 WHERE 是用于数据行，而 HAVING 则作用于分组.
>
>  HAVING 支持所有 WHERE 的操作，因此所有需要 WHERE 子句实现的功能，你都可以使用 HAVING 对分组进行筛选 



## 四.关键字的顺序

 **在 SELECT 查询中，关键字的顺序是不能颠倒的，它们的顺序是：** 

```sql
SELECT ... FROM ... WHERE ... GROUP BY ... HAVING ... ORDER BY ...
```

##### 例子 : 

```sql
-- 筛选最大生命值与最大法力值之和大于 7000 的英雄，按照攻击范围来进行分组，显示分组的英雄数量，以及分组英雄的最大生命值与法力值之和的平均值、最大值和最小值，并按照分组英雄数从高到低进行排序，其中聚集函数的结果包括小数点后两位。

select name ,count(*) as num, ROUND(max(hp_max + mp_max),2), ROUND(min(hp_max + mp_max),2),ROUND(avg(hp_max + mp_max),2) from heros where (hp_max + mp_max) > 7000 GROUP BY attack_range order by num desc
```



