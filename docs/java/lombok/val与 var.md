# lombok 的 val 与 var

## 一. 含义 

- val 和 var 是lombok 提供的语法糖,可以进行类型推断的功能.
- 您可以将`val`用作局部变量声明的类型,而不是实际编写的类型.执行操作的时候,将从初始化设定项的表达式推断类型.
- 此功能仅适用于局部变量和 `foreach` 循环,不适用于字段.

## 二. 使用

```java
public class ValDemo {

	public static void main(String[] args) {
		/*
			最终展示 : 
			a
			b
			a : b
			b : c
		 */
		listDemo();
		mapDemo();
	}

	private static void listDemo() {
		val example = new ArrayList<String>();
		example.add("a");
		example.add("b");
		for (val s : example) {
			System.out.println(s);
		}
	}

	private static void mapDemo() {
		val example = new HashMap<String, String>();
		example.put("a", "b");
		example.put("b", "c");

		for (val entry : example.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
}
```



## 三. val 和 var 区别

- `var`工作方式与 `val`完全相同，只是局部变量未标记为final。

## 四. 配置
- lombok.var.flagUsage= [ warning| error]（默认值：未设置）. var如果已配置， Lombok 会将任何用法标记为警告或错误。
- lombok.val.flagUsage= [ warning| error]（默认值：未设置）.val如果已配置， Lombok 会将任何用法标记为警告或错误。


## 五. 版本变更

- val 是在 lombok 0.10 中引入 , 1.18.22 中将 val 替换为 final var
- var 在 lombok 1.16.12 中最为实验性功能引入.


## 六. 参考文献

- [1]  [lombok 官方文档](https://projectlombok.org/features/var)

