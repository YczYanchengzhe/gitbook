# 第三部分 使用流和 Lambda 进行高效编程

## 第八章 Collection API 的增强功能

```java
public class FactoryDemo {
	/**
	 * JDK 9+
	 */
	public static void demo1() {
		{
			List<String> friends = List.of("Raphael", "Olivia", "Thibaut");
		}
		{
			Set<String> friends = Set.of("Raphael", "Olivia", "Thibaut");
			System.out.println(friends);
		}
		{
			Map<String, Integer> ageOfFriends = Map.of("Raphael", 30, "Olivia", 25, "Thibaut", 26);
			System.out.println(ageOfFriends);
		}
		{
			Map<String, Integer> ageOfFriends = Map.ofEntries(entry("Raphael", 30), entry("Olivia", 25), entry("Thibaut", 26));
			System.out.println(ageOfFriends);
		}
	}

	public static void demo2() {
		{
			List<String> friends = Stream.of("Raphael", "Olivia", "Thibaut").collect(Collectors.toList());
			friends.removeIf("Thibaut"::equals);
			System.out.println(friends);
			friends.replaceAll(String::toLowerCase);
			System.out.println(friends);
		}

		{
			Map<String, Integer> ageOfFriends = Map.ofEntries(entry("Raphael", 30), entry("Olivia", 25), entry("Thibaut", 26));
			ageOfFriends.forEach((k, v) -> System.out.println(k + " " + v));
			System.out.println("------------------");
			ageOfFriends.entrySet().stream()
					.sorted(Map.Entry.comparingByValue())
					.forEachOrdered(System.out::println);
		}
	}

	public static void demo3(List<String> lines) {
		Map<String, byte[]> dataToHash = new HashMap<>(8);
		lines.forEach(line -> dataToHash.computeIfAbsent(line, FactoryDemo::calculateDigest));

		dataToHash.remove("a",new byte[0]);
	}

	private static byte[] calculateDigest(String key) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			return messageDigest.digest(key.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new byte[0];
		}
	}


	public static void main(String[] args) {
		demo1();
		demo2();
	}
}
```

## 第九章 重构,测试和调试

### 9.1 为改善代码的可读性和灵活性重构代码
- 重构代码,用 Lambda 表达式取代匿名类
- 用方法引用重构 Lambda 表达式
- 用 Stream API 重构命令式的数据处理

### 9.2 使用 Lambda 重构面向对象的设计模式
- 策略模式
- 模板方法 
- 观察者模式 : 
- 责任链模式 : 
- 工厂模式 : 

### 9.3 Stream 的调试

```java
public class StreamPeekDemo {

	public static void main(String[] args) {
		/*
			借助 peek 查看流中数据
		 */
		List<Integer> collect = IntStream.rangeClosed(0, 100)
				.peek(x -> System.out.println("map before : " + x))
				.map(x -> x + 17)
				.peek(x -> System.out.println("map after : " + x))
				.filter(x -> x % 2 == 0)
				.peek(x -> System.out.println("filter after : " + x))
				.limit(3)
				.peek(x -> System.out.println("milie after : " + x))
				.boxed()
				.collect(toList());
		System.out.println(collect);
	}
}
```

## 第十章 基于 Lambda 的领域特定语言


