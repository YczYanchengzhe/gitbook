# 兰姆达表达式

## 一 函数 @FunctionalInterface

- Predicate<T> 有参数、条件判断

- Function<T, R> 有参数、有返回值

- Consumer<T> 无返回值

- Supplier<T> 无参数、有返回值



## 二. Stream（流）

### 2.1 简介

Stream是一个来自数据源的元素队列并支持聚合操作.

### 2.2 相关名词介绍

- 元素：特定类型的对象，形成一个队列。 Java 中的 Stream 并不会存储元素，而是按需计算。

- 数据源：流的来源。 可以是集合，数组，I/O channel， 产生器 generator 等。
- 聚合操作 类似 SQL 语句一样的操作， 比如 filter, map, reduce, find, match, sorted 等。

### 2.3 Stream 操作的特征 , 与Collection 操作的区别

- Pipelining：中间操作都会返回流对象本身。 这样多个操作可以串联成一个管道， 如同流式风格(fluent style)。 这样做可以对操作进行优化， 比如延迟执行(laziness)和短路((shortcircuiting)。 

- 内部迭代：以前对集合遍历都是通过 Iterator 或者 For-Each 的方式, 显式的在集合外部进行迭代， 这叫做外部迭代。 Stream 提供了内部迭代的方式， 通过访问者模式(Visitor)实现。



### 2.4 中间操作

#### 2.4.1 选择与过滤

- `filter(Predicate p)` 接收 Lambda ， 从流中排除某些元素。

- `distinct()` 筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素

- `limit(long maxSize)` 截断流，使其元素不超过给定数量。

- `skip(long n)` 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返 回一个空流。

#### 2.4.2 映射

- `map(Function f)` 接收 Lambda ， 将元素转换成其他形式或提取信息;接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。

- `mapToDouble(ToDoubleFunction f)` 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 DoubleStream。

- `mapToInt(ToIntFunction f)` 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 IntStream。

- `mapToLong(ToLongFunction f)` 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 LongStream。

- `flatMap(Function f)` 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。

#### 2.4.3 排序

- `sorted()` 产生一个新流，其中按自然顺序排序

- `sorted(Comparator comp)` 产生一个新流，其中按比较器顺序排序

### 2.5 终止操作

#### 2.5.1 查找与匹配

- `allMatch`——检查是否匹配所有元素

- `anyMatch`——检查是否至少匹配一个元素
- `noneMatch`——检查是否没有匹配的元素
- `findFirst`——返回第一个元素
- `findAny`——返回当前流中的任意元素
- `count`——返回流中元素的总个数
- `max`——返回流中最大值
- `min`——返回流中最小值

#### 2.5.2 归约

- `reduce`, 需要初始值（类比Map-Reduce）

#### 2.5.3 收集 collect

- `toList` List<T> 把流中元素收集到 List

- `toSet` Set<T> 把流中元素收集到 Set

- `toCollection` Collection<T> 把流中元素收集到创建的集合

- `count` 计算流中元素的个数

- `summaryStatistics` 统计最大最小平均值

#### 2.5.4 迭代 forEach

