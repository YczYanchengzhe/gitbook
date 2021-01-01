# 子查询 与 连接



# 一.子查询

## 1. 子查询

> 依据子查询是否执行多次，将子查询划分为**关联子查询**和**非关联子查询**。
>
> 关联子查询 :  子查询需要执行多次，即采用循环的方式，先从外部查询开始，每次都传入子查询进行查询，然后再将结果反馈给外部，这种嵌套的执行方式就称为关联子查询。 
>
> 非关联子查询 :  子查询从数据表中查询了数据结果，如果这个数据结果只执行一次，然后这个数据结果作为主查询的条件进行执行，那么这样的子查询叫做非关联子查询。 

```sql
-- 哪个球员的身高最高，最高身高是多少  , 非关联子查询
SELECT player_name, height FROM player WHERE height = (SELECT max(height) FROM player)
-- 统计球队的平均身高 , 筛选身高大于这个数值的球员姓名、身高和球队 ID , 关联子查询
SELECT player_name, height, team_id FROM player AS a WHERE height > (SELECT avg(height) FROM player AS b WHERE a.team_id = b.team_id)
```

## 2. EXISTS 子查询
> EXISTS 子查询用来判断条件是否满足，满足的话为 True，不满足为 False。
> NOT EXISTS : 不存在

```sql
-- 查询出场过的球员
SELECT player_id, team_id, player_name FROM player WHERE EXISTS (SELECT player_id FROM player_score WHERE player.player_id = player_score.player_id)
```

## 3.集合比较子查询

|   关键字   | 含义 |
| :----: | :----: |
| In | 判断是否再集合中 |
|   ANY   | 需要与比较操作符一起使用,与子查询返回的任意值作比较 |
| ALL | 需要与比较操作符一起使用,与子查询返回的所有值作比较 |
| SOME | 实际上是ANY的别名 , 作用相同 |

```sql
-- 出场过的球员都有哪些
SELECT player_id, team_id, player_name FROM player WHERE player_id in (SELECT player_id FROM player_score WHERE player.player_id = player_score.player_id)
```

> IN 和 EXISt的区别 : 
>
> 当大表IN小表时效率高；小表EXIST大表时效率高  . 总结来说就是小表驱动大表
>
> 所以哪个表小就用哪个表来驱动，A表小 就用EXIST，B表小 就用IN.
```sql
SELECT * FROM A WHERE cc IN (SELECT cc FROM B)
SELECT * FROM A WHERE EXIST (SELECT cc FROM B WHERE B.cc=A.cc)
```
```java
//当A小于B时，用EXIST。因为EXIST的实现，相当于外表循环，实现的逻辑类似于：
for i in A
    for j in B
        if j.cc == i.cc then ...
//当B小于A时，用IN，因为实现的逻辑类似于：
for i in B
    for j in A
        if j.cc == i.cc then ...
```
```sql
-- 比印第安纳步行者（对应的 team_id 为 1002）中任意一个球员身高高的球员信息
SELECT player_id, player_name, height FROM player WHERE height > ANY (SELECT height FROM player WHERE team_id = 1002)
```

```sql
-- 比印第安纳步行者（对应的 team_id 为 1002）中所有球员身高都高的球员的信息
SELECT player_id, player_name, height FROM player WHERE height > ALL (SELECT height FROM player WHERE team_id = 1002)
```

## 4.将子查询作为计算字段

```sql
-- 查询每个球队的队员数量
SELECT team_name, (SELECT count(*) FROM player WHERE player.team_id = team.team_id) AS player_num FROM team
```

```sql
-- 场均得分大于 20 的球员。场均得分从 player_score 表中获取，同时你需要输出球员的 ID、球员姓名以及所在球队的 ID 信息
select * from player where player.player_id in (select player_id  from player_score GROUP BY player_id having  avg(score)>20)
```



# 连接

## 1.笛卡尔积 (在SQL95中也叫交叉连接)
> 笛卡尔乘积是一个数学运算。假设我有两个集合 X 和 Y，那么 X 和 Y 的笛卡尔积就是 X 和 Y 的所有可能组合，也就是第一个对象来自于 X，第二个对象来自于 Y 的所有可能。
```sql
SELECT * FROM player, team
-- SQL 95
SELECT * FROM player CROSS JOIN team
```

## 2.等值连接 (在SQL95中也叫自然连接) ---- 内连接
>两张表的等值连接就是用两张表中都存在的列进行连接。我们也可以对多张表进行等值连接。

```sql
SELECT player_id, player.team_id, player_name, height, team_name FROM player, team WHERE player.team_id = team.team_id
-- SQL 95
SELECT player_id, team_id, player_name, height, team_name FROM player NATURAL JOIN team 
```

> 在进行等值连接的时候，可以使用表的别名，这样会让 SQL 语句更简洁
> 如果我们使用了表的别名，在查询字段中就只能使用别名进行代替，不能使用原有的表名
```sql
-- 正确的
SELECT player_id, a.team_id, player_name, height, team_name FROM player AS a, team AS b WHERE a.team_id = b.team_id
-- 错误的
SELECT player_id, player.team_id, player_name, height, team_name FROM player AS a, team AS b WHERE a.team_id = b.team_id
```

## 3. 非等值连接   ---- 内连接
>当我们进行多表查询的时候，如果连接多个表的条件是等号时，就是等值连接，其他的运算符连接就是非等值查询。
```sql
-- 每个球员的身高的级别
SELECT p.player_name, p.height, h.height_level
FROM player AS p, height_grades AS h
WHERE p.height BETWEEN h.height_lowest AND h.height_highest
```

## 4.自连接  ---- 内连接

```sql
-- 查看比布雷克·格里芬高的球员都有谁，以及他们的对应身高\
SELECT b.player_name, b.height FROM player as a , player as b WHERE a.player_name = '布雷克-格里芬' and a.height < b.height
```

## 5.外连接
> 外连接 : 查询某一方不满足条件的记录.两张表的外连接，会有一张是主表，另一张是从表。如果是多张表的外连接，那么第一张表是主表，即显示全部的行，而第剩下的表则显示对应连接的信息。

>左外连接，就是指左边的表是主表，需要显示左边表的全部行，而右侧的表是从表，（+）表示哪个是从表。 
>
>LEFT JOIN 或 LEFT OUTER JOIN

```sql
SELECT * FROM player LEFT JOIN team on player.team_id = team.team_id
```

> 右外连接，指的就是右边的表是主表，需要显示右边表的全部行，而左侧的表是从表。
> RIGHT JOIN 或 RIGHT OUTER JOIN

```sql
SELECT * FROM player RIGHT JOIN team on player.team_id = team.team_id
```
> 全外连接：全外连接的结果 = 左右表匹配的数据 + 左表没有匹配到的数据 + 右表没有匹配到的数据。 
> FULL JOIN 或 FULL OUTER JOIN
> Mysql 不支持全外连接

## 6. ON连接
> ON 连接用来指定我们想要的连接条件

```sql
-- SQL92
SELECT p.player_name, p.height, h.height_level
FROM player AS p, height_grades AS h
WHERE p.height BETWEEN h.height_lowest AND h.height_highest
-- SQL99
SELECT p.player_name, p.height, h.height_level
FROM player as p JOIN height_grades as h
ON height BETWEEN h.height_lowest AND h.height_highest
-- 查询不同身高级别（对应 height_grades 表）对应的球员数量（对应 player 表）。
select height_level ,count(*) from player,height_grades where player.height between height_grades.height_level and height_grades.height_highest group by height_level ; 
select height_level ,count(*) from player join height_grades on player.height between height_grades.height_level and height_grades.height_highest  group by height_level ; 
  

```

## 7. USING 连接
> 当我们进行连接的时候，可以用 USING 指定数据表里的同名字段进行等值连接

```sql
SELECT player_id, team_id, player_name, height, team_name FROM player JOIN team USING(team_id)
-- 含义相同
SELECT player_id, player.team_id, player_name, height, team_name FROM player JOIN team ON player.team_id = team.team_id
```
>USING 连接与自然连接 NATURAL JOIN 不同的是，USING 指定了具体的相同的字段名称，你需要在 USING 的括号 () 中填入要指定的同名字段。同时使用 JOIN USING 可以简化 JOIN ON 的等值连接.


# 不同 DBMS 中使用连接需要注意的地方
## 1.不是所有的 DBMS 都支持全外连接
虽然 SQL99 标准提供了全外连接，但不是所有的 DBMS 都支持。不仅 MySQL 不支持，Access、SQLite、MariaDB 等数据库软件也不支持。不过在 Oracle、DB2、SQL Server 中是支持的。
## 2.Oracle 没有表别名 AS
在 Oracle 中是不存在 AS 的，使用表别名的时候，直接在表名后面写上表别名即可，比如 player p，而不是 player AS p。
## 3.SQLite 的外连接只有左连接

# 连接性能注意
## 1. 控制连接表的数量
多表连接就相当于嵌套 for 循环一样，非常消耗资源，会让 SQL 查询性能下降得很严重，因此不要连接不必要的表。在许多 DBMS 中，也都会有最大连接表的限制。
## 2.在连接时不要忘记 WHERE 语句
多表连接的目的不是为了做笛卡尔积，而是筛选符合条件的数据行，因此在多表连接的时候不要忘记了 WHERE 语句，这样可以过滤掉不必要的数据行返回。
## 3. 使用自连接而不是子查询
一般情况建议你使用自连接，因为在许多 DBMS 的处理过程中，对于自连接的处理速度要比子查询快得多 . 
子查询实际上是通过未知表进行查询后的条件判断，而自连接是通过已知的自身数据表进行条件判断，因此在大部分 DBMS 中都对自连接处理进行了优化。

