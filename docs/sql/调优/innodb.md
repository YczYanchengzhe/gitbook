# InnoDB

- InnoDb 的三大特点 : [InnoDB的三个关键特性](https://www.cnblogs.com/yuyutianxia/p/3841917.html)

- innodb_file_per_table : OFF 表示的是，表的数据放在系统共享表空间,也就是跟数据字典放在一起；ON 每个 InnoDB 表数据存储在一个以 .ibd 为后缀的文件中。从 MySQL 5.6.6 版本开始，它的默认值就是 ON 了。
- 
