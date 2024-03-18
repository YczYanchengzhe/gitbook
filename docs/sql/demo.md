# 样例





## 一. 函数

### 1.1 CONCAT()
CONCAT()函数在连接之前将所有参数转换为字符串类型。如果任何参数为NULL，则CONCAT()函数返回NULL值。
```sql
CONCAT('aaa','bbb')
```

### 1.2 LEFT(str, length)
从左开始截取字符串，length 是截取的长度。

### 1.3 UPPER(str) 与 LOWER(str)
UPPER(str) 将字符串中所有字符转为大写

LOWER(str) 将字符串中所有字符转为小写

### 1.4 SUBSTRING(str, begin, end)
截取字符串，end 不写默认为空。

```sql
-- 从第二个截取到末尾，注意并不是下标，就是第二个。
SUBSTRING(name, 2) 
```

### 1.5 distinct  去重

### 1.6 count() 统计次数

```sql
-- 按照 product 去重 , 按照 product 排序 , 按照 `,` 拼接
group_concat(distinct(product) order by product SEPARATOR ",")

select sell_date ,count(distinct(product)) as num_sold,
group_concat(distinct(product) order by product SEPARATOR ",") as products
from activities
group by sell_date 
order by sell_date;
```

## 二. demo

### 2.1 union 使用
```sql
select employee_id from (
select employee_id from Employees where employee_id not in(select employee_id from Salaries)
union 
select employee_id from Salaries  where employee_id not in(select employee_id from Employees))
t order by employee_id asc;
```

### 2.2 列转行
```sql
select product_id , 'store1' as store  , store1 as price from Products where store1 is not null
union 
select product_id , 'store2'  as store , store2 as price from Products where store2 is not null
union
select product_id , 'store3'  as store , store3 as price from Products where store3 is not null;
```


