# 第二部分 - 使用流进行函数式数据处理

## 第六章 用流收集数据

### 6.1 Collectors 

- Collectors 类的静态工厂方法

| 工厂方法          | 返回类型              | 用于                                                         | 使用示例                                                     |
| ----------------- | --------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| toList            | List\<T>              | 把流中所有项目收集到一个 List                                | List\<Dish> dishes = menuStream.collect(toList());           |
| toSet             | Set\<T>               | 把流中所有项目收集到一个 Set,删除重复项                      | Set\<Dish> dishes = menuStream.collect(toSet());             |
| toCollection      | Collection\<T>        | 把流中所有项目收集到给定的供应源创建的集合                   | Collection\<Dish> dishes = menuStream.collect(toCollection(), ArrayList::new); |
| counting          | Long                  | 计算流中元素的个数                                           | long howManyDishes = menuStream.collect(counting());         |
| summingInt        | Integer               | 对流中项目的一个整数属性求和                                 | int totalCalories = menuStream.collect(summingInt(Dish::getCalories)); |
| averagingInt      | Double                | 计算流中项目 Integer 属性的平均值                            | double avgCalories = menuStream.collect(averagingInt(Dish::getCalories)); |
| summarizingInt    | IntSummaryStatistics  | 收集关于流中项目 Integer 属性的统计值,例如最大,最小,总和与平均值 | IntSummaryStatistics menuStatistics = menuStream.collect(summarizingInt(Dish::getCalories)); |
| joining           | String                | 连接对流中每个项目调用 toString 方法所生成的字符串           | String shortMenu = menuStream.map(Dish::getName).collect(joining(", ")); |
| maxBy             | Optional\<T>          | 一个包裹了流中按照给定比较器选出的最大元素的 Optional,或如果流为空则为 Optional.empty() | Optional\<Dish> fattest =  menuStream.collect(maxBy(comparingInt(Dish::getCalories))); |
| minBy             | Optional\<T>          | 一个包裹了流中按照给定比较器选出的最小元素的 Optional,或如果流为空则为 Optional.empty() | Optional\<Dish> fattest =  menuStream.collect(minBy(comparingInt(Dish::getCalories))) |
| reducing          | 归约操作产生的类型    | 从一个作为累加器的初始值开始,利用 BinaryOperator 与流中的元素逐个结合,从而将流归约为单个值 | int totalCalories =  menuStream.collect(reducing(0,Dish::getCalories,Integer::sum)); |
| collectingAndThen | 转换函数返回的类型    | 包裹另一个收集器,对其结果应用转换函数                        | int howManyDishes = menuStream.collect(collectingAndThen(toList(), List::size)); |
| groupingBy        | Map<K,List\<T>>       | 根据项目的一个属性的值对流中的项目做分组,并将属性值作为结果 Map 的键 | Map<Dish.Type,List\<Dish>> dishesByType = menuStream.collect(groupingBy(Dish::getType)); |
| partitioningBy    | Map<Boolean,List\<T>> | 根据对流中每个项目应用谓词的结果来对项目进行区分             | Map<Boolean,List\<Dish>> vegetarianDishes = menuStream.collect(partitioningBy(Dish::isVegetarian)); |




- Collectors demo

```java
	@Test
	public void collectorsDemo1() {
		List<Demo1.Apple> apples = new ArrayList<>();
		apples.add(new Demo1.Apple(Demo1.Color.RED, 1));
		apples.add(new Demo1.Apple(Demo1.Color.RED, 2));
		apples.add(new Demo1.Apple(Demo1.Color.RED, 3));
		apples.add(new Demo1.Apple(Demo1.Color.GREEN, 4));
		apples.add(new Demo1.Apple(Demo1.Color.GREEN, 5));
		apples.add(new Demo1.Apple(Demo1.Color.GREEN, 6));
		apples.add(new Demo1.Apple(Demo1.Color.GREEN, 7));
		apples.add(new Demo1.Apple(Demo1.Color.BLUE, 8));
		apples.add(new Demo1.Apple(Demo1.Color.BLUE, 9));
		apples.add(new Demo1.Apple(Demo1.Color.BLUE, 10));
		apples.add(new Demo1.Apple(Demo1.Color.BLUE, 11));
		apples.add(new Demo1.Apple(Demo1.Color.BLUE, 12));

		Map<Demo1.Color, List<Demo1.Apple>> appleMap1 = apples
				.stream()
				.filter(apple -> apple.getId() < 8)
				// 按照颜色分组
				.collect(Collectors.groupingBy(Demo1.Apple::getColor));

		Map<Demo1.Color, List<Demo1.Apple>> appleMap2 = apples
				.stream()
				// 按照颜色分组,并且保证了空元素不会被删除
				.collect(Collectors.groupingBy(Demo1.Apple::getColor, Collectors.filtering(apple -> apple.getId() < 8, Collectors.toList())));

		// 统计结果 :  可以取到最大,最小,平均等
		IntSummaryStatistics collectResult = apples.stream().collect(Collectors.summarizingInt(Demo1.Apple::getId));
	}
```



### 6.2 Collector


#### 6.2.1 Collector 接口声明的方法

```java
public interface Collector<T,A,R> {
   Supplier<A> supplier();
   BiConsumer<A, T> accumulator();
   Function<A, R> finisher();
   BinaryOperator<A> combiner();
   Set<Characteristics> characteristics();
}
```

-  建立新的结果容器 : supplier 方法
- 将元素添加到结果容器 : accumulator 方法
- 对结果容器应用最终转换 : finisher方法
- 合并两个结果容器 : combiner 方法
- 返回一个不可变的 Characteristics 集合 : characteristics方法
```text
Characteristics 定义了收集器行为 : 
- UNORDERED : 归约结果不受流中项目的遍历和累计顺序的影响
- CONCURRENT : accumulator 函数可以从多个线程中同时调用,且该收集器可以并行归约流.如果收集器没有标为 UNORDERED ,那它仅在用于无序数据源时才可以并行归约.
- IDENTITY_FINISH : 表示完成其方法返回的函数是一个恒等函数,可以跳过.这种情况下,累加器对象将会直接用作归约过程的最终结果.这也意味着,将累加器 A 不加检查的转换为结果 R 是安全的
```

#### 6.2.2 Demo

- 自定义 Collector

```java
public class PrimeNumberCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

	@Override
	public Supplier<Map<Boolean, List<Integer>>> supplier() {
		return () -> new HashMap<>(2) {{
			put(true, new ArrayList<>());
			put(false, new ArrayList<>());
		}};
	}

	@Override
	public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
		/*
			获取所有质数列表,进行判断,之后将其加入到集合中
		 */
		return (Map<Boolean, List<Integer>> acc, Integer candidate) -> acc.get(isPrime(acc.get(true), candidate))
				.add(candidate);
	}

	@Override
	public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
		return (Map<Boolean, List<Integer>> map1, Map<Boolean, List<Integer>> map2) -> {
			map1.get(true).addAll(map2.get(true));
			map1.get(false).addAll(map2.get(false));
			return map1;
		};
	}

	@Override
	public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
		return Function.identity();
	}

	@Override
	public Set<Characteristics> characteristics() {
		return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
	}

	private boolean isPrime(List<Integer> primes, int candidate) {
		int candidateRoot = (int) Math.sqrt(candidate);
		return primes.stream()
				.takeWhile(i -> i <= candidateRoot)
				.noneMatch(i -> candidate % i == 0);
	}
}
```

- 测试 : 

```java
public class PrimeNumberCollectorTest {

	public Map<Boolean, List<Integer>> partitionPrimes(int n) {
		return IntStream.rangeClosed(2, n).boxed()
				.collect(partitioningBy(this::isPrime));
	}

	public Map<Boolean, List<Integer>> partitionPrimes2(int n) {
		return IntStream.rangeClosed(2, n).boxed()
				.collect(new PrimeNumberCollector());
	}


	public Map<Boolean, List<Integer>> partitionPrimes3(int n) {
		return IntStream.rangeClosed(2, n).boxed()
				.collect(
						// 供应源
						() -> new HashMap<Boolean, List<Integer>>(2) {{
							put(true, new ArrayList<>());
							put(false, new ArrayList<>());
						}},
						// 累加器
						(acc, candidate) -> {
							acc.get(isPrime(acc.get(true), candidate))
									.add(candidate);
						},
						// 组合器
						(map1, map2) -> {
							map1.get(true).addAll(map2.get(true));
							map1.get(false).addAll(map2.get(false));
						});
	}

	private boolean isPrime(int candidate) {
		int candidateRoot = (int) Math.sqrt(candidate);
		return IntStream.rangeClosed(2, candidateRoot).noneMatch(i -> candidate % i == 0);
	}

	private boolean isPrime(List<Integer> primes, int candidate) {
		int candidateRoot = (int) Math.sqrt(candidate);
		return primes.stream()
				.takeWhile(i -> i <= candidateRoot)
				.noneMatch(i -> candidate % i == 0);
	}


	@Test
	public void demo() {
		Map<Boolean, List<Integer>> result1 = partitionPrimes(30);

		Map<Boolean, List<Integer>> result2 = partitionPrimes2(30);

		Map<Boolean, List<Integer>> result3 = partitionPrimes3(30);
		System.out.println();
	}
	@Test
	public void cmp() {
		{
			long fastest = Long.MAX_VALUE;
			for (int i = 0; i < 10; i++) {
				long start = System.nanoTime();
				partitionPrimes(1_000_000);
				long duration = (System.nanoTime() - start) / 1_000_000;
				if (duration < fastest) {
					fastest = duration;
				}
			}
			System.out.println("test partitionPrimes fastest done cost " + fastest);
		}
		{
			long fastest = Long.MAX_VALUE;
			for (int i = 0; i < 10; i++) {
				long start = System.nanoTime();
				partitionPrimes2(1_000_000);
				long duration = (System.nanoTime() - start) / 1_000_000;
				if (duration < fastest) {
					fastest = duration;
				}
			}
			System.out.println("test partitionPrimes2 fastest done cost " + fastest);
		}
	}
}
```

- 优化比较 : 可以看到优化后执行效率有了一定的提升

```text
test partitionPrimes fastest done cost 95
test partitionPrimes2 fastest done cost 86
```

  

### 6.3 总结

- 总结了 Collectors 提供的所有方法
- 自定义了Collector实现更高效的归约操作











