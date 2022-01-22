# 第二部分 - 使用流进行函数式数据处理

## 第七章 并行数据处理与性能

### 7.1 并行流

#### 7.1.1  如何配置并行流使用的线程池
- 默认值 : `Runtime.getRuntime().availableProcessors()` , 注意 : 该方法虽然看起来是处理器，但它实际上返回的是可用核的数量，包括超线程生成的虚拟核。
- 配置 : 除非有非常充足的理由,否则没有必要修改它,而且该配置会对代码中所有的并行流产生影响`System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","12");`


#### 7.1.2 高效使用并行流 - 何时应该使用并行流
- 确认该场景使用并行流是否能够真的提高性能,通过基准测试
- 注意装箱拆箱操作 : 自动装箱拆箱操作会大大降低性能.Java 8 中有原始类型流（IntStream、LongStream 和 DoubleStream）来避免这种操作，但凡有可能都应该用这些流。
- 有些操作本身在并行流上的性能就比顺序差.特别是`limit`和`findFirst`等依赖于元素顺序的操作,他们在并行流上执行的代价非常大.例如 : `findany`就比`findFirst`性能好.
- 考虑流的操作流水线的总计算成本.设N 是要处理的元素的总数,Q 是一个元素通过流水线的大致处理成本,则 N*Q 就是这个对成本的一个粗略的定性估计.Q 值较高就意味着使用并行流时性能好的可能性比较大.
- 对于比较小的数据量,不建议使用并行流.并行处理少数几个元素的好处还抵不上并行化造成的额外开销.
- 考虑流背后的数据结构是否易于分解.例如 : ArrayList 的拆分相率比 LinkedList高很多.前者拆分不需要遍历,后者需要. 除此之外,使用 range 工厂方法创建的原始数据类型流也可以快速分解.
- 流自身的特点以及流水线中的中间操作修改流的方式,都可能会改变分解过程的性能.例如，一个 SIZED 流可以分成大小相等的两部分，这样每个部分都可以比较高效地并行处理，但筛选操作可能丢弃的元素个数无法预测，从而导致流本身的大小未知。
- 还要考虑终端操作中合并步骤的代价是大是小（例如 Collector 中的 combiner 方法）。如果这一步代价很大，那么组合每个子流产生的部分结果所付出的代价就可能会超出通过并行流得到的性能提升.

#### 7.1.3 流的数据源和可分解性

|       源        | 可分解性 |
| :-------------: | :------: |
|    ArrayList    |   极佳   |
|   LinkedList    |    差    |
| IntStream.range |   极佳   |
| Stream.itreate  |    差    |
|     HashSet     |    好    |
|     TreeSet     |    好    |


### 7.2 分支/合并框架 - ForkJoinPool

#### 7.2.1 使用 RecursiveTask
- 样例
```java
public class ForkJoinSumCalculator extends RecursiveTask<Long> {

	private final long[] numbers;
	private final int start;
	private final int end;
	public static final long THRESHOLD = 10_000;

	public ForkJoinSumCalculator(long[] numbers) {
		this(numbers, 0, numbers.length);
	}

	public ForkJoinSumCalculator(long[] numbers, int start, int end) {
		this.numbers = numbers;
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		int length = end - start;
		if (length <= THRESHOLD) {
			return computeSequentially();
		}
		ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
		leftTask.fork();
		ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);
		Long rightResult = rightTask.compute();
		Long leftResult = leftTask.join();
		return leftResult + rightResult;

	}

	private long computeSequentially() {
		long sum = 0;
		for (int i = start; i < end; i++) {
			sum += numbers[i];
		}
		return sum;
	}

}
```

- 使用
```java
public class ForkJoinSumCalculatorTest {

	@Test
	public void test() {
		int n = 12_000_000;
		long result = calc(n);
		System.out.println(result);
	}

	private long calc(int n) {
		long[] numbers = LongStream.rangeClosed(1, n).toArray();
		ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
		return new ForkJoinPool().invoke(task);
	}

}
```

#### 7.2.2 使用分支/合并框架的最佳做法
- 对于一个任务调用 join 方法会阻塞调用方,直到该任务做出结果. 因此有必要在两个子任务的计算都开始之后在调用.
- 不应该在 RecursiveTask 内部使用 ForkJoinPool 的 invoke 方法,相反,你应该始终直接调用 compute 或者 fork 方法,只有顺序代码才应该使用 invoke 来启动并行计算.
- 对子任务调用 fork 方法可以把它排进 ForkJoinPool。同时对左边和右边的子任务调用它的效率要比直接对其中一个调用 compute 低。因为通过 compute 你可以为其中一个子任务重用同一线程，从而避免在线程池中多分配一个任务造成的开销。
- 一个任务可以分解成多个独立的子任务，才能让性能在并行化时有所提升。所有这些子任务的运行时间都应该比分出新任务所花的时间长。一个惯用方法是把输入/输出放在一个子任务里，计算放在另一个里，这样计算就可以和输入/输出同时进行。此外，在比较同一算法的顺序和并行版本的性能时还有别的因素要考虑。就像任何其他 Java 代码一样，分支/合并框架需要“预热”或者说要执行几遍才会被 JIT 编译器优化。这就是为什么在测量性能之前跑几遍程序很重要。同时还要知道，编译器内置的优化可能会为顺序版本带来一些优势（例如执行死码分析——删去从未被使用的计算）。

#### 7.2.3 工作窃取
分支/合并框架工程使用工作窃取技术来解决不同任务执行时间不同的问题.

在实际应用中,每个线程都为分配给他的任务保存一个双向链式队列,每完成一个任务，就会从队列头上取出下一个任务开始执行.某个线程可能早早完成了分配给它的所有任务，也就是它的队列已经空了，而其他的线程还很忙。这时，这个线程并没有闲下来，而是随机选了一个别的线程，从队列的尾巴上“偷走”一个任务。这个过程一直继续下去，直到所有的任务都执行完毕，所有的队列都清空。

### 7.3 Spliterator

- Spliterator : 定义了并行流如何拆分他要遍历的数据.
- 延迟绑定 : Spliterator可以在在第一次遍历、第一次拆分或第一次查询估计大小时绑定元素的数据源，而不是在创建时就绑定.

#### 7.3.1 接口
```java
public interface Spliterator<T> {
	boolean tryAdvance(Consumer<? super T> action);
	Spliterator<T> trySplit();
	long estimateSize();
	int characteristics();
}
```
#### 7.3.2 方法介绍 : 

- T : 便利的元素类型
- tryAdvance : 类似于普通的迭代器,按照顺序使用 Spliterator 中的元素,并且如果还有其他元素药便利就返回 true
- trySplit : 把一些元素花出去分给第二个 Spliterator,让他们并行处理.
- estimateSize : 估计剩下多少袁旭需要遍历
- characteristics : 表示Spliterator 的**特性**.

#### 7.3.3 特性介绍 :

| 特性       | 含义                                                         |
| ---------- | ------------------------------------------------------------ |
| ORDERED    | 元素有既定的顺序(如 List),因此 Spliterator 在遍历和划分时候也会遵循这一顺序 |
| DISTINCT   | 对于任意一对比安利过的元素 x 和 y,x.equals(y)返回 false,生成的流可以类比 Set |
| SORTED     | 遍历的元素按照一个预定义的顺序排序                           |
| SIZED      | 该 Spliterator 由一个一只大小的源建立(例如 Set),因此,estimateSize()返回的是准确值 |
| NON-NULL   | 保证遍历的元素不会是 null                                    |
| IMMUTABLE  | Spliterator由一个一只大小的源建立,这意味着在遍历时不能添加,删除或者修改任何元素 |
| CONCURRENT | 该 Spliterator 的数据源可以被其他线程同时修改而无需同步      |
| SUBSIZED   | 该 Spliterator 和所有从它拆分出来的 Spliterator 都是 SIZED   |

#### 7.3.4 Demo
```java
public class WordCounterSpliterator implements Spliterator<Character> {
	private final String string;
	private int currentChar = 0;

	public WordCounterSpliterator(String string) {
		this.string = string;
	}

	@Override
	public boolean tryAdvance(Consumer<? super Character> action) {
		// 将当前索引位置传递给 consumer,位置 + 1
		action.accept(string.charAt(currentChar++));
		// 判断是否还有字符要处理
		return currentChar < string.length();
	}

	@Override
	public Spliterator<Character> trySplit() {
		int currentSize = string.length() - currentChar;
		if (currentSize < 10) {
			// 返回 null 表是当前要处理的字符串已经足够小了
			return null;
		}
		// 尝试拆分 , 定位到要解析的字符串中间位置
		for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++) {
			// 找到空格了 , 从下一个位置开始拆分
			if (Character.isWhitespace(string.charAt(splitPos))) {
				// 创建一个新的拆分器来解析 String 从开始到拆分位置的部分
				Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
				// 将 起始位置设置为拆分的位置
				currentChar = splitPos;
				// 由于已经发现了空格吗,并创建了一个新的拆分器,所以退出循环
				return spliterator;
			}
		}
		return null;
	}

	@Override
	public long estimateSize() {
		return string.length() - currentChar;
	}

	@Override
	public int characteristics() {
		return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
	}
}
```


```java
public class WordCounter {
	private final int counter;
	private final boolean lastSpace;

	public WordCounter(int counter, boolean lastSpace) {
		this.counter = counter;
		this.lastSpace = lastSpace;
	}

	public WordCounter accumulate(Character c) {
		if (Character.isWhitespace(c)) {
			return lastSpace ? this : new WordCounter(counter, true);
		} else {
			return lastSpace ? new WordCounter(counter + 1, false) : this;
		}
	}

	public WordCounter combine(WordCounter wordCounter) {
		return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
	}

	public int getCounter() {
		return counter;
	}
}
```

```java
public class WordCounterSpliteratorTest {

	public static final String SENTENCE =
			" Nel mezzo del cammin di nostra vita " +
					"mi ritrovai in una selva oscura" +
					" ché la dritta via era smarrita ";

	private int countWords(Stream<Character> stream) {
		WordCounter wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
		return wordCounter.getCounter();
	}

	@Test
	public void test() {
		Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE);
		Stream<Character> stream = StreamSupport.stream(spliterator, true);
		System.out.println("Found " + countWords(stream) + " words.");
	}

}
```

