# 存储过程



##  什么是存储过程 ?

> 存储过程就是 SQL 语句的封装 



## 存储过程相关操作
```sql
-- 创建
CREATE PROCEDURE 存储过程名称([参数列表])
BEGIN
    需要执行的语句
END   
-- 删除
DROP PROCEDURE;
-- DELIMITER 修改结束符 : 修改默认 ; 为 // 
DELIMITER //
CREATE PROCEDURE `add_num`(IN n INT)
BEGIN 
    DECLARE i INT; 
    DECLARE sum INT; 
    SET i = 1; 
    SET sum = 0; 
    WHILE i <= n DO SET sum = sum + i;
    SET i = i +1; 
    END WHILE; 
    SELECT sum;
END //
DELIMITER ;
```

## 存储过程的三种参数类型

| 参数类型 | 是否返回 | 作用      |
| -------- | -------- | --------- |
| IN       | 否       | 入参      |
| OUT      | 是       | 出参      |
| INOUT    | 是       | 入参,出参 |



```sql
CREATE PROCEDURE `get_hero_scores`(
       OUT max_max_hp FLOAT,
       OUT min_max_mp FLOAT,
       OUT avg_max_attack FLOAT,  
       s VARCHAR(255)
       )
BEGIN
       SELECT MAX(hp_max), MIN(mp_max), AVG(attack_max) FROM heros WHERE role_main = s INTO max_max_hp, min_max_mp, avg_max_attack;
END


CALL get_hero_scores(@max_max_hp, @min_max_mp, @avg_max_attack, '战士');
SELECT @max_max_hp, @min_max_mp, @avg_max_attack;
```

##  流控制语句 

> BEGIN…END：BEGIN…END 中间包含了多个语句，每个语句都以（;）号为结束符。
>
> DECLARE：DECLARE 用来声明变量，使用的位置在于 BEGIN…END 语句中间，而且需要在其他语句使用之前进行变量的声明。相当于是局部变量.
>
> #SET : 相当于设置了全局变量
>
> SET：赋值语句，用于对变量进行赋值。
>
> SELECT…INTO：把从数据表中查询的结果存放到变量中，也就是为变量赋值。 
>
>  IF…THEN…ENDIF：条件判断语句，我们还可以在 IF…THEN…ENDIF 中使用 ELSE 和 ELSEIF 来进行条件判断。
>
> CASE：CASE 语句用于多条件的分支判断，使用的语法是下面这样的。 

```sql
CASE 
  WHEN expression1 THEN ...
  WHEN expression2 THEN ...
  ...
    ELSE 
    --ELSE语句可以加，也可以不加。加的话代表的所有条件都不满足时采用的方式。
END
```

> LOOP、LEAVE 和 ITERATE：LOOP 是循环语句，使用 LEAVE 可以跳出循环，使用 ITERATE 则可以进入下一次循环。LEAVE ->BREAK , ITERATE ->CONTINUE
>
> REPEAT…UNTIL…END REPEAT：这是一个循环语句，首先会执行一次循环，然后在 UNTIL 中进行表达式的判断，如果满足条件就退出，即 END REPEAT；如果条件不满足，则会就继续执行循环，直到满足退出条件为止。
>
> WHILE…DO…END WHILE：这也是循环语句，和 REPEAT 循环不同的是，这个语句需要先进行条件判断，如果满足条件就进行循环，如果不满足条件就退出循环。 



## 存储过程优缺点
>优点 : 
	一次编译多次使用,提高SQL执行效率
	减少开发工作量
	安全性强
	减少网络传输

>缺点 : 
	可移植性差
	调试困难
	版本管理困难
	不适合高并发场景 (高并发情况下可能需要分库分表,此时存储过程难以维护)










