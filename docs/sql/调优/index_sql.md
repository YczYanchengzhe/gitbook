# 如何通过索引让SQL执行更高效

# 一.什么样的字段应该创建索引 
1. **字段的数值有唯一性的限制 ， 比如用户名**
2. **频繁作为 where 查询条件的字段，尤其在数据表大的情况下**
3. **需要经常 group by 和 order by 的列**

- 实际上多个单列索引在多条件查询时只会生效一个索引（MySQL 会选择其中一个限制最严格的作为索引），所以在多条件联合查询的时候最好创建联合索引。

- 在进行 SELECT 查询的时候，执行顺序是先进行 GROUP BY，再对数据进行 ORDER BY 的操作，所以按照这个联合索引的顺序效率是最高的。

4. **update delete 的where 条件列 ， 一般也需要创建索引**
- 如果进行更新的时候，更新的字段是非索引字段，提升的效率会更明显，这是因为非索引字段更新不需要对索引进行维护
- 不过如果索引太多了，在更新数据的时候，可能涉及到索引更新，就会造成负担。

5. **distinct 字段需要创建索引**
- 因为索引会对数据按照某种顺序进行排序，所以在去重的时候也会快很多.

6. **在进行多表 join连接操作时候，创建索引需要注意 ：** 
（1）连接表的数量尽量不要超过 3 张，因为每增加一张表就相当于增加了一次嵌套的循环，数量级增长会非常快，严重影响查询的效率。
（2）对 WHERE 条件创建索引，因为 WHERE 才是对数据条件的过滤。如果在数据量非常大的情况下，没有 WHERE 条件过滤是非常可怕的。
（3）对用于连接的字段创建索引，并且该字段在多张表中的类型必须一致

7. **什么时候不需要创建索引**
（1）where 条件里用不到的字段不需要创建索引。
（2）如果表记录太少，不需要创建索引，例如少于 1000个
（3）字段中如果有大量重复数据，也不用创建索引
（4）频繁更新的字段，不一定要创建索引，因为更新数据时候也会更新索引，会造成负担，影响效率


# 二.什么情况下索引会失效 ： 

1. **如果索引进行了表达式计算**
2. **如果对索引使用了函数，也会造成失效**
3. **在where子句中，如果在or前的条件列进行了索引，而在or后的字段没有进行索引，那么索引会失效**
- 因为 OR 的含义就是两个只要满足一个即可，因此只有一个条件列进行了索引是没有意义的，只要有条件列没有进行索引，就会进行全表扫描，因此索引的条件列也会失效, 而and就不会.
4. **当我们使用 LIKE 进行模糊查询的时候，前面不能是 %**
5. **索引列尽量设置为 NOT NULL 约束 ： 节省测试字段值是否为null的开销**
 - 参考[官方解释](https://dev.mysql.com/doc/refman/8.0/en/data-size.html )  : It makes SQL operations faster, by enabling better use of indexes and eliminating overhead for testing whether each value is NULL.
6. **使用联合索引的时候要注意最左原则**

- SQL 条件语句中的字段顺序并不重要，因为在逻辑查询优化阶段会自动进行查询重写。
- 如果我们遇到了范围条件查询，比如（<）（<=）（>）（>=）和 between 等，那么范围列后的列就无法使用到索引了

# 三. 为什么没有理想的索引

## 1. 索引片和过滤因子

> **索引片**就是 SQL 查询语句在执行中需要扫描的一个索引片段，我们会根据索引片中包含的匹配列的数量不同，将**索引**分成窄索引（比如包含索引列数为 1 或 2）和宽索引（包含的索引列数大于 2）

> 每个非聚集索引保存的数据都会存储主键值，然后通过主键值，来回表查找相应的数据，因此每个索引都相当于包括了主键

## 2. 如何通过宽索引避免回表

> 回表 : 回表指的就是数据库根据索引找到了数据行之后，还需要通过主键再次到数据表中读取数据的情况。

例子 : 

```sql
select name,sex,age from t_user where name ='晓燕;
-- 1. 创建 窄索引 name : 实际索引  id_name , 在根据索引查询完成后还要回表查询其他字段.
-- 2. 创建 宽索引 name_sex_age : 实际索引  id_name_sex_age , 可以在索引中直接获取数据,不用回表
```

## 3. 什么是过滤因子
> 过滤因子，描述了谓词的选择性. 在 WHERE 条件语句中，每个条件都称为一个谓词，谓词的选择性也等于满足这个条件列的记录数除以总记录数的比例。

> 联合过滤因子有更高的过滤能力,但是需要注意,条件列的关联性应该尽量独立,否则如果列与列关联性较高,联合过滤因子的能力就会下降很多.

> 过滤因子决定了索引片的大小（注意这里不是窄索引和宽索引），过滤因子的条件过滤能力越强，满足条件的记录数就越少，SQL 查询需要扫描的索引片也就越小.

## 4. 理想索引设计：三星索引

三星索引标准 : 
- 最小化索引片 : where的等值条件作为索引片的开始列
- 避免排序 : 针对 group by 和 order by 创建索引
- 避免回表 : 将SELECT字段中剩余的列加入到索引中

## 5. 理想索引的权衡
同三范式一样，很多时候我们并没有遵循三范式的设计原则，而是采用了反范式设计。同样，有时候我们并不能需要完全遵循三星索引的原则，原因主要有以下两点：
- 采用三星索引会让索引片变宽，这样每个页能够存储的索引数据就会变少，从而增加了页加载的数量。从另一个角度来看，如果数据量很大，比如有 1000 万行数据，过多索引所需要的磁盘空间可能会成为一个问题，对缓冲池所需空间的压力也会增加。
- 增加了索引维护的成本。当我们添加一条记录的时候，就需要在每一个索引上都添加相应的行,假设添加一行记录的时间成本是 10ms（磁盘随机读取一个页的时间），那么如果我们创建了 10 个索引，添加一条记录的时间就可能变成 0.1s.当然对于数据库来说，数据的更新不一定马上回写到磁盘上，但即使不及时将脏页进行回写，也会造成缓冲池中的空间占用过多，脏页过多的情况。

# 四. 总结 : 如何设计索引

- 一张表的索引不宜过多,否则一条记录的增加和修改，会因为过多的索引造成额外的负担。所以在新建索引的时候,优先考虑在原有的索引片上增加索引.另外可以定期检查索引使用,对于未使用的索引进行删除.
- 在索引片中，我们也需要控制索引列的数量，通常情况下我们将 WHERE 里的条件列添加到索引中，而 SELECT 中的非条件列则不需要添加。
- 单列索引和复合索引的长度也需要控制.在 MySQL InnoDB 中，系统默认单个索引长度最大为 767 bytes，如果单列索引长度超过了这个限制，就会取前缀索引，也就是取前 255 字符。所以在设计表的时候,尽量采用数值类型替代字符类型,避免使用字符类型作为主键.针对字符类型最好只创建前缀索引.

```sql
-- 创建前缀索引 : 
ALTER TABLE table_name ADD KEY(column_name(prefix_length));
```























