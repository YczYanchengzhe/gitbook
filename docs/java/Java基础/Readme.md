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











# 参考资料

- [1] [Java transient关键字使用小记](https://www.cnblogs.com/lanxuezaipiao/p/3369962.html)