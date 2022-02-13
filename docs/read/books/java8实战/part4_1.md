# 第四部分 无所不在的 Java

## 第九章 用 Optional 取代 null

 **null** 带来的种种问题

- 它是错误之源。 : NullPointerException 是目前 Java 程序开发中最典型的异常。

- 它会使你的代码膨胀。 : 它让你的代码充斥着深度嵌套的 null 检查，代码的可读性糟糕透顶。

- 它自身是毫无意义的。 : null 自身没有任何的语义，尤其是，它代表的是在静态类型语言中以一种错误的方式对

缺失变量值的建模。

- 它破坏了 Java 的哲学。 : Java 一直试图避免让程序员意识到指针的存在，唯一的例外是：null 指针。

- 它在 Java 的类型系统上开了个口子 : null 并不属于任何类型，这意味着它可以被赋值给任意引用类型的变量。这会导致问题，

原因是当这个变量被传递到系统中的另一个部分后，你将无法获知这个 null 变量最初的赋值到底是什么类型。

### 9.1 应用 Optional的几种模式

#### 9.1.1 Optional 序列化问题
- Optional 的设计初衷仅仅是要支持能返回 Optional 对象的语法。因此它也并未实现Serializable 接口 ,如果你一定要实现序列化的域模型,可以使用下面的替代方案
```java
@Data
@ToString
public class Order {
	private String customer;
	public Optional<String> getCustomerWithOptional() {
		return Optional.ofNullable(customer);
	}
}
```

#### 9.1.2 方法

| 方法            | 描述                                                         |
| --------------- | ------------------------------------------------------------ |
| empty           | 返回一个空的 `Optional` 实例                                 |
| filter          | 如果该值存在并且满足提供的谓词,就返回包含该值的 `Optional` 对象;否则返回一个空的 `Optional` 对象 |
| flatMap         | 如果值存在,就对该值执行提供的 `mapping` 函数调用,返回一个 `Optional` 类型的值,否则就返回一个空的 `Optional` 对象 |
| get             | 如果值存在,就将该值用 `Optional` 封装返回,否则抛出一个 `NoSuchElement Exception`异常 |
| ifPresent       | 如果值存在,就执行使用该值的方法调用,否则什么也不做           |
| ifPresentOrElse | 如果值存在,就以值作为输入执行对应的方法调用,否则执行另一个不需任何输入的方法 |
| ifPresent       | 如果值存在,就返回 `true`,否则返回`false`                     |
| map             | 如果值存在,就对该值执行提供的 `mapping` 函数调用             |
| of              | 将指定值用 Optional 封装之后返回,如果该值为 `null`,则抛出一个 `NullPointException`异常 |
| ofNullable      | 将指定值用 Optional 封装之后返回,如果该值为 `null`,则返回一个空的`Optional 对象` |
| or              | 如果值存在,就返回同一个 `Optional`对象,否则返回由支持韩束生产队能另一个`Optional`对象 |
| orElse          | 如果有值则将其返回,否则返回一个默认值                        |
| orElseGet       | 如果有值则将其返回,否则返回一个由指定的`Supplier`接口生成的值 |
| orElseThrow     | 如果有值则将其返回,否则抛出一个有指定的`Supplier`接口生成的异常 |
| Stream          | 如果有值,就返回包含该值的一个 stream,否则返回一个空的 stream |

#### 9.1.3 接口使用示例

```java
public class OptionalDemo {


	public static void create() {
		// 声明一个空的 Optional
		Optional<Order> order1 = Optional.empty();
		// 依据一个非空值创建 Optional
		Optional<Order> order2 = Optional.of(new Order());
		// 可以接受 null 值的 optional
		Optional<Order> order3 = Optional.ofNullable(null);
	}

	public static String getWithNull(Order order) {
		if (order == null) {
			return "Empty";
		}
		if (order.getTradeList() == null || order.getTradeList().isEmpty() || order.getTradeList().get(0) == null) {
			return "Empty";
		}
		if (order.getTradeList().get(0).getStock() == null) {
			return "Empty";
		}
		return order.getTradeList().get(0).getStock().getMarket();
	}

	public static String getWithOptionalNull(Order order) {
		return Optional.ofNullable(order)
				.filter(o -> "Big".equals(o.getCustomer()))
				.map(Order::getTradeList)
				.flatMap(trades -> trades.stream().findAny())
				.map(Trade::getStock)
				.map(Stock::getMarket)
				.orElse("Empty");
	}

	/**
	 * 不需要进行 null 判断的字符串拼接
	 *
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static String getWithTwoOptional(String s1, String s2) {
		return Optional.ofNullable(s1)
				.flatMap(s1Tmp ->
						Optional.of(s2).map(s2Tmp -> s1Tmp + s2Tmp))
				.orElse("");
	}

	public static void main(String[] args) {
		System.out.println(getWithNull(null));
		System.out.println(getWithOptionalNull(null));
		System.out.println(getWithTwoOptional(null, "1"));
	}

}
```

```java
public class OptionalUtil {

	public static Object mapUtil(Map<Object, Object> map) {
		return Optional.ofNullable(map.get("aa"));
	}

	public static Optional<Integer> stringToInt(String param) {
		try {
			return Optional.of(Integer.parseInt(param));
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}

	public static OptionalInt stringToOptionalInt(String param) {
		try {
			return OptionalInt.of(Integer.parseInt(param));
		} catch (NumberFormatException e) {
			return OptionalInt.empty();
		}
	}

	public static int readDuring(Properties properties, String name) {
		return Optional.ofNullable(properties.getProperty(name))
				.flatMap(OptionalUtil::stringToInt)
				.filter(i -> i > 0)
				.orElse(0);
	}

	public static int readDuringOld(Properties properties, String name) {
		String value = properties.getProperty(name);
		if (value != null) {
			try {
				int i = Integer.parseInt(value);
				if (i > 0) {
					return i;
				}
			} catch (NumberFormatException ignore) {
			}
		}
		return 0;
	}
}
```


