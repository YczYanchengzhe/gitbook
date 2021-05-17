# Java 基础



# 一. 有趣的知识

## 1.transient 

#### (1) transient 作用
- 加上transient的字段不会被序列化

#### (2) 例子
下面例子中使用了lombok,依赖请自行查找~
```java
public class TransientTest implements Serializable {
	private transient String test = "aaa";

	public static void main(String[] args) throws Exception {
		//测试序列化
		User user = new User();
		user.setId("123");
		user.setUserName("aaa");
		System.out.println("序列化user之前 : " + user);
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File("TransientTest")));
		outputStream.writeObject(user);
		outputStream.flush();
		outputStream.close();

		ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File("TransientTest")));
		user = (User) inputStream.readObject();
		System.out.println("序列化user之后 : " + user);

		//测试 Externalizable

		User2 user2 = new User2();
		user2.setId("123");
		user2.setUserName("aaa");
		System.out.println("序列化user2之前 : " + user2);
		outputStream = new ObjectOutputStream(new FileOutputStream(new File("TransientTest")));
		outputStream.writeObject(user2);
		outputStream.flush();
		outputStream.close();

		inputStream = new ObjectInputStream(new FileInputStream(new File("TransientTest")));
		user2 = (User2) inputStream.readObject();
		System.out.println("序列化user2之后 : " + user2);


	}

}

@ToString
@Data
class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userName;
	private static int age = 12;
	private transient String id;

}

@ToString
@Data
class User2 implements Externalizable {

	private static final long serialVersionUID = 1L;
	private String userName;
	private transient String id;

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(id);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		id = (String) in.readObject();
	}
}
```

测试结果 : 

```
序列化user之前 : User(userName=aaa, id=123)
序列化user之后 : User(userName=aaa, id=null)
序列化user2之前 : User2(userName=aaa, id=123)
序列化user2之后 : User2(userName=null, id=123)
```

#### (3) 总结
- 如果实现了Serializable,对于类中加了transient的字段.在序列化时候是不会进行序列化的.
- 如果实现了Externalizable ,自己去定义序列化反序列化,那么只会按照自定义的序列化方式来执行.与transient关键字无关.


## 2.双大括号初始化
#### (1) 介绍
Java的一种语法糖 , 可以使用双大括号进行初始化.实际原理是匿名内部类+实例化代码块
#### (2) 例子
```java
Set<Integer> set = new HashSet<Integer>() {{
    add(1);
    add(2);
    add(3);
}};
```

## 3.Guava 工具包


## 4. 代码规范
> 详情见参考资料4-6

## 5. 未关闭流引起的资源泄露
https://droidyue.com/blog/2019/06/09/will-unclosed-stream-objects-cause-memory-leaks/


## 6.jdk 1.8 的时间处理
https://www.jianshu.com/p/2949db9c3df5

## 7.动态代理
https://blog.csdn.net/fanrenxiang/article/details/81939357

## 8.jdk1.8 流处理
https://blog.csdn.net/l18848956739/article/details/86504409

## 9. java 自动装箱 ,拆箱引起的**不明确问题
https://www.codenong.com/22234497/

## 10. 断点续传
https://www.cnblogs.com/phpstudy2015-6/p/6821478.html#_label5
https://www.jianshu.com/p/2b82db0a5181



## 11. final return 执行顺序
#### 从运行时的堆栈关系来解释try-catch 原理
https://blog.csdn.net/roholi/article/details/7358265

## 12. List<Object> , List<? extend Object>

https://blog.csdn.net/qq_32117641/article/details/88692100?utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-1.control&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-1.control

https://blog.csdn.net/qq_37964379/article/details/88528997


# 参考资料

- [1] [Java transient关键字使用小记](https://www.cnblogs.com/lanxuezaipiao/p/3369962.html)
- [2] [Java中的双大括号初始化](https://blog.csdn.net/Yaokai_AssultMaster/article/details/52188735)
- [3] [guava工具包](http://ifeve.com/google-guava/)
- [4] [Google 编码规范](https://google.github.io/styleguide/javaguide.html)
- [5] [Alibaba 编码规范](https://github.com/alibaba/p3cl)
- [6] [VIP 规范](https://vipshop.github.io/vjtools/#/standard/)

