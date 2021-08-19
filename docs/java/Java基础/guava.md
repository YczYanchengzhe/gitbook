# Guava

## 一. 基本工具
让使用Java语言变得更舒适
- 使用和避免null：null是模棱两可的，会引起令人困惑的错误，有些时候它让人很不舒服。很多Guava工具类用 快速失败拒绝null值，而不是盲目地接受 
- 前置条件: 让方法中的条件检查更简单 
- 常见Object方法: 简化Object方法实现，如hashCode()和toString() 
- 排序: Guava强大的”流畅风格比较器” 
- Throwables：简化了异常和错误的传播与检查

## 二. 集合[Collections]  
Guava对JDK集合的扩展，这是Guava最成熟和为人所知的部分 

- 不可变集合: 用不变的集合进行防御性编程和性能提升。 
- 新集合类型: multisets, multimaps, tables, bidirectional maps等
- 强大的集合工具类: 提供java.util.Collections中没有的集合工具 
- 扩展工具类：让实现和扩展集合类变得更容易，比如创建Collection的装饰器，或实现迭代器

## 三. 缓存[Caches] 
Guava Cache：本地缓存实现，支持多种缓存过期策略 

## 四. 函数式风格[Functional idioms] 
Guava的函数式支持可以显著简化代码，但请谨慎使用它 

## 五.并发[Concurrency] 
强大而简单的抽象，让编写正确的并发代码更简单 
- ListenableFuture：完成后触发回调的Future 
- Service框架：抽象可开启和关闭的服务，帮助你维护服务的状态逻辑

## 六. 字符串处理[Strings] 
非常有用的字符串工具，包括分割、连接、填充等操作 

## 七. 原生类型[Primitives] 
扩展 JDK 未提供的原生类型（如int、char）操作， 包括某些类型的无符号形式 

## 八. 区间[Ranges]
可比较类型的区间API，包括连续和离散类型 

## 九. I/O 
简化I/O尤其是I/O流和文件的操作，针对Java5和6版本 

## 十. 散列[Hash] 
提供比Object.hashCode()更复杂的散列实现，并提供Bloom过滤器的实现

## 十一. 事件总线[EventBus] 
发布-订阅模式的组件通信，进程内模块间解耦 

## 十二. 数学运算[Math] 
优化的、充分测试的数学工具类 

## 十三.反射[Reflection]
Guava 的 Java 反射机制工具类





http://ifeve.com/google-guava/

https://www.cnblogs.com/rickiyang/p/11074159.html



