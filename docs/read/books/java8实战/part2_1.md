# 第二部分 - 使用流进行函数式数据处理



## 第四章 引入流

### 1.集合与流

- 集合与流之间的差异在于什么时候进行计算
- 集合是一个内存中的数据结构,它包含数据结构中目前所有的值,集合中所有元素都得先计算出来才能添加到集合中.
- 流式在概念上固定的数据结构,其元素是按需计算的.
- 流只能遍历一次 , 操作分为两类 : 中间操作,终端操作
- 集合需要外部迭代,流采用内部迭代 


## 第五章 使用流

### 5.1 筛选
- 谓词筛选filter :   该操作会接受一个谓词（一个返回boolean 的函数）作为参数,并返回一个包括所有符合谓词的元素的流.
- 去重distinct : 该操作返回一个元素各异（根据流所生成元素的 hashCode和 equals 方法实现）的流.

```java
	/**
	 * 按照传入函数的条件执行 filter 操作
	 *
	 * @param inventory
	 * @param condition
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> filter(List<T> inventory, Predicate<T> condition) {
		return inventory.stream().filter(condition).collect(Collectors.toList());
	}

	/**
	 * 对流中元素去重
	 * @param inventory
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> distinct(List<T> inventory){
		return inventory.stream().distinct().collect(Collectors.toList());
	}
```

### 5.2 切片,截短,跳过

#### 5.2.1 切片

- **takeWhile**
- **dropWhile**

```java
	/**
	 * JDK 9+
	 * 遇到第一个符合不匹配的元素后退出 , 返回的是符合条件的元素流
	 *
	 * @param inventory
	 * @param condition
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> takeWhile(List<T> inventory, Predicate<T> condition) {
		return inventory.stream().takeWhile(condition).collect(Collectors.toList());
	}

	/**
	 * JDK 9+
	 * 删除所有匹配的,遇到第一个不匹配的元素后退出
	 *
	 * @param inventory
	 * @param condition
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> dropWhile(List<T> inventory, Predicate<T> condition) {
		return inventory.stream().dropWhile(condition).collect(Collectors.toList());
	}
```

#### 5.2.2 截短 , 跳过

- limit : 返回前 n 个元素
- skip : 跳过前 n 个元素

```java
	/**
	 * 从前往后 , 截短
	 * @param inventory
	 * @param limit
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> limit(List<T> inventory,int limit) {
		return inventory.stream().limit(limit).collect(Collectors.toList());
	}

	/**
	 * 跳过前 n 个元素
	 * @param inventory
	 * @param skip
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> skip(List<T> inventory , int skip) {
		return inventory.stream().skip(skip).collect(Collectors.toList());
	}
```

### 5.3 映射

- map : 对流中的每一个元素进行处理,可以将其映射成一个新的元素
- flatmap : 将流中的每一个值都转换为另一个流,然后把所有流连接起来形成一个新的流

```java
	@Test
	public void demoMap() {
		/*
			返回每个数平方构成的列表
		 */
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
		List<Integer> squares = numbers.stream().map(number -> number * number).collect(Collectors.toList());
		squares.forEach(System.out::println);
	}

	@Test
	public void demoFlatMap() {
		/*
			 给定两个数字列表，如何返回所有的数对呢？
			 例如，给定列表[1, 2, 3]和列表[3, 4]，
			 应该返回[(1, 3), (1, 4), (2, 3), (2, 4), (3, 3), (3, 4)]
		 */
		List<Integer> number1 = Arrays.asList(1, 2, 3);
		List<Integer> number2 = Arrays.asList(3, 4);
		List<int[]> pairs = number1.stream()
				.flatMap(i -> number2.stream().map(j -> new int[]{i, j}))
				.collect(Collectors.toList());
		pairs.forEach(num -> System.out.printf("%s,%s\n", num[0], num[1]));
	}
```

### 5.4 查找和匹配
查看数据集中的某些元素是否匹配一个给定的属性 

- anyMatch : 流中是否有一个元素能够匹配指定谓词
- allMatch : 流中是否所有元素都匹配指定谓词
- noneMatch : 流中是否所有元素都不匹配指定谓词

> 短路求值 : 有些操作不需要处理整个流就能得到结果。例如，假设你需要对一个用 and 连起来的大布尔表达式求值。不管表达式有多长，你只需找到一个表达式为 false，就可以推断整个表达式将返回 false，所以用不着计算整个表达式。这就是短路。上面的三个操作都使用了短路求值



```java
	/**
	 * 流中是否有一个元素能够匹配指定谓词
	 * @param inventory
	 * @param condition
	 * @param <T>
	 * @return
	 */
	public static <T> boolean anyMatch(List<T> inventory, Predicate<T> condition) {
		return inventory.stream().anyMatch(condition);
	}

	/**
	 * 流中是否所有元素都匹配指定谓词
	 * @param inventory
	 * @param condition
	 * @param <T>
	 * @return
	 */
	public static <T> boolean allMatch(List<T> inventory, Predicate<T> condition) {
		return inventory.stream().allMatch(condition);
	}

	/**
	 * 流中是否所有元素都不匹配指定谓词
	 * @param inventory
	 * @param condition
	 * @param <T>
	 * @return
	 */
	public static <T> boolean noneMatch(List<T> inventory, Predicate<T> condition) {
		return inventory.stream().noneMatch(condition);
	}
```

- findAny : 返回流中任意一个元素
- findFirst : 返回流中第一个元素

> 何时使用 **findFirst** 和 **findAny** : 找到第一个元素在并行上限制更多。如果你不关心返回的元素是哪个，请使用 findAny，因为它在使用并行流时限制较少。

```java
	/**
	 * 返回流中任意一个元素
	 * @param inventory
	 * @param <T>
	 * @return
	 */
	public static <T> Optional<T> finaAny(List<T> inventory) {
		return inventory.stream().findAny();
	}

	/**
	 * 返回流中第一个元素
	 * @param inventory
	 * @param <T>
	 * @return
	 */
	public static <T> Optional<T> findFirst(List<T> inventory) {
		return inventory.stream().findFirst();
	}
```

### 5.5 归约

- reduce : 将流归约为一个值

```java
	/**
	 * 对于指定元素指定特定操作 : 不包含初始值
	 *
	 * @param inventory
	 * @param accumulator 操作
	 * @param <T>
	 * @return
	 */
	public static <T> Optional<T> reduce(List<T> inventory, BinaryOperator<T> accumulator) {
		return inventory.stream().reduce(accumulator);
	}

	/**
	 * 对于指定元素指定特定操作 : 包含初始值
	 *
	 * @param inventory
	 * @param accumulator
	 * @param initValue
	 * @param <T>
	 * @return
	 */
	public static <T> T reduce(List<T> inventory, BinaryOperator<T> accumulator, T initValue) {
		return inventory.stream().reduce(initValue, accumulator);
	}


	/**
	 * 计算最大值
	 *
	 * @param inventory
	 * @return
	 */
	public static Optional<Integer> reduceMax(List<Integer> inventory) {
		return inventory.stream().reduce(Integer::max);
	}
```

```java
	@Test
	public void demoReduce() {
		List<Demo1.Apple> apples = new ArrayList<>();
		apples.add(new Demo1.Apple(Demo1.Color.RED, 1));
		apples.add(new Demo1.Apple(Demo1.Color.RED, 2));
		apples.add(new Demo1.Apple(Demo1.Color.RED, 3));
		apples.add(new Demo1.Apple(Demo1.Color.GREEN, 4));
		apples.add(new Demo1.Apple(Demo1.Color.GREEN, 5));
		apples.add(new Demo1.Apple(Demo1.Color.GREEN, 6));
		apples.add(new Demo1.Apple(Demo1.Color.GREEN, 7));

		Optional<Demo1.Apple> reduce = PredicateHelper.reduce(apples, (apple, apple2) -> {
			apple.setId(apple2.getId() + apple.getId());
			return apple;
		});

		System.out.println(reduce.orElse(new Demo1.Apple()));
	}
```

### 5.6 数值流

- 原始类型的流特化

```java
	@Test
	public void demoBoxed(){
		List<Demo1.Apple> apples = new ArrayList<>();
		apples.add(new Demo1.Apple(Demo1.Color.RED, 1));
		apples.add(new Demo1.Apple(Demo1.Color.RED, 2));
		apples.add(new Demo1.Apple(Demo1.Color.RED, 3));
		apples.add(new Demo1.Apple(Demo1.Color.GREEN, 4));
		apples.add(new Demo1.Apple(Demo1.Color.GREEN, 5));
		apples.add(new Demo1.Apple(Demo1.Color.GREEN, 6));
		apples.add(new Demo1.Apple(Demo1.Color.GREEN, 7));


		IntStream intStream = apples.stream()
				// 拆箱
				.mapToInt(Demo1.Apple::getId);
		// 装箱
		Stream<Integer> stream = intStream.boxed();
		// Optional原始类型特化版本
		OptionalInt maxOptional = intStream.max();
		int max = maxOptional.orElse(0);
	}
```

### 5.7 构建流

- 构建流

```java
	@Test
	public void demoStreamCreate() {
		// 创建流
		Stream<String> stream = Stream.of("Modern", "Java", "In ", "Action");
		stream.map(String::toUpperCase).forEach(System.out::println);
		// 空的流
		Stream<String> emptyStream = Stream.empty();

		/*
			jdk 9+
			基于空元素创建流 : Stream.ofNullable
		 */
		Stream<String> home =
				Stream.of("config", "home", "user")
						.flatMap(key -> Stream.ofNullable(System.getProperty(key)));


		/*
			基于数组创建流
		 */
		int[] numbers = {1, 2, 3, 4, 5};
		int sum = Arrays.stream(numbers).sum();


		/*
			基于文件生成流 ,并统计不重复单词数量
		 */
		long uniqueWords = 0;
		try (Stream<String> lines = Files.lines(Paths.get("data.txt"), Charset.defaultCharset())) {
			uniqueWords = lines
					.flatMap(line -> Arrays.stream(line.split(" ")))
					.distinct()
					.count();
		} catch (IOException ignored) {
		}
		System.out.println(uniqueWords);

		/*
			由函数生成流 : 斐波那契数列
		 */
		Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0], t[1]})
				.limit(10)
				.forEach(t -> System.out.println("(" + t[0] + "," + t[1] + ")"));


		/*
		 * 生成随机数流
		 */
		Stream.generate(Math::random)
				.limit(5)
				.forEach(System.out::println);
	}
```

### 5.8 小结

- 可以使用 `filter、distinct、takeWhile (Java 9)、dropWhile (Java 9)、skip` 和`limit` 对流做筛选和切片。
- 如果你明确地知道数据源是排序的，那么用 takeWhile 和 dropWhile 方法通常比filter 高效得多。
- 你可以使用 map 和 flatMap 提取或转换流中的元素。
- 你可以使用 findFirst 和 findAny 方法查找流中的元素。你可以用 allMatch、noneMatch 和 anyMatch 方法让流匹配给定的谓词。
- 这些方法都利用了短路：找到结果就立即停止计算；没有必要处理整个流。
- 你可以利用 reduce 方法将流中所有的元素迭代合并成一个结果，例如求和或查找最大元素。
- filter 和 map 等操作是无状态的，它们并不存储任何状态。reduce 等操作要存储状态才能计算出一个值。sorted 和 distinct 等操作也要存储状态，因为它们需要把流中的所有元素缓存起来才能返回一个新的流。这种操作称为有状态操作。 
- 流有三种基本的原始类型特化：IntStream、DoubleStream 和 LongStream。它们的操作也有相应的特化。
- 流不仅可以从集合创建，也可从值、数组、文件以及 iterate 与 generate 等特定方法创建。
- 无限流所包含的元素数量是无限的（想象一下所有可能的字符串构成的流）。这种情况是有可能的，因为流中的元素大多数都是即时产生的。使用 limit 方法，你可以由一个无限流创建一个有限流。







