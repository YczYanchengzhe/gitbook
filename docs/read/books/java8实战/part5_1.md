# 第五部分 提升 Java 的并发性

## 第十五章 CompletableFuture 及反应式编程背后的概念



#### 15.1 并行 : 使用流还是使用 CompletableFuture?
- 如果进行的是计算密集型的操作,并且没有 I/O,那么推荐使用 Stream 接口,因为实现简单,同事效率可能也是最高的.
- 如果你并行的工作单元还涉及等待 I/O 的操作,那么使用 CompletableFuture 灵活性更好



**注意** : 线程池中的任务即便是处于睡眠状态，也会阻塞其他任务的执行（它们无法停止已经分配了线程的任务，因为这些任务的调度是由操作系统管理的）。

```java
public class CfcCombine {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		int x = 1234;
		CompletableFuture<Integer> a = new CompletableFuture<>();
		CompletableFuture<Integer> b = new CompletableFuture<>();
		CompletableFuture<Integer> c = a.thenCombine(b, Integer::sum);

		executorService.submit(() -> a.complete(f(x)));
		executorService.submit(() -> b.complete(g(x)));

		System.out.println(c.get());
		executorService.shutdown();
	}

	public static int f(int x) {
		return x;
	}

	public static int g(int x) {
		return x;
	}
}
```

# 第六部分 函数式编程以及 Java 未来的演进

## 第十九章 函数式编程技巧

**柯里化的理论定义 :**

- 柯里化①是一种将具备两个参数（比如，x 和 y）的函数 f 转化为使用一个参数的函数 g，并且这个函数的返回值也是一个函数，它会作为新函数的一个参数。后者的返回值和初始函数的返回值相同，即 f(x,y) = (g(x))(y)。
- 当然，这个定义是一种概述。你可以将一个使用了六个参数的函数柯里化成一个接受第 2、 4、6 参数，并返回一个接受 5 号参数的函数，这个函数又返回一个接受剩下的第 1 号和第 3号参数的函数。
- 当一个函数使用的所有参数仅有部分（少于函数的完整参数列表）被传递时，通常我们说这个函数是部分求值（partially applied）的。

**破坏式更新和函数式更新的比较**

函数式编程解决破坏式更新的方法是禁止使用待有副作用的方法.如果你需要使用表示计算结果的数据结构,那么请创建他的一个副本而不要直接修改现存的数据结构.



## Java 重复注解
- 将注解标记为 @Repeatable
- 提供一个注解的容器

```java
@Repeatable(Autos.class)
public @interface Auto {
	String name();
}

public @interface Autos {
	Auto[] value();
}

@Auto(name = "/")
@Auto(name = "/2")
public class Demo {
}
```