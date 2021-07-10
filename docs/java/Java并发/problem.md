
# 问题

#### HashMap死循环问题

![dump_hashmap_](../../resources/java/java_concurrency/dump_hashmap_.png)

![jstacj_hashmap](../../resources/java/java_concurrency/jstacj_hashmap.png)



在Java8之前的版本中之所以出现死循环是因为在resize的过程中对链表进行了倒序处理；在Java8中不再倒序处理，自然也不会出现死循环。
https://blog.csdn.net/pange1991/article/details/82377980