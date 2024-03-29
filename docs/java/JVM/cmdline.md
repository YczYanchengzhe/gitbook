# JVM 相关命令



## 一.工具简介
#### 1.**jps/jinfo** 
查看 java 进程 

```shell
jps -mlv # 查看进程详情
```

#### 2.**jstat** 
查看 JVM 内部 gc 相关信息 

```markdown
-class 类加载(Class loader)信息统计. 

-compiler JIT 即时编译器相关的统计信息。 

-gc GC 相关的堆内存信息. 用法: jstat -gc -h 10 -t 864 1s 20 

-gccapacity 各个内存池分代空间的容量。 

-gccause 看上次 GC, 本次 GC（如果正在 GC中）的原因, 其他 输出和 -gcutil 选项一致。 

-gcnew 年轻代的统计信息. （New = Young = Eden + S0 + S1） 

-gcnewcapacity 年轻代空间大小统计. 

-gcold 老年代和元数据区的行为统计。 

-gcoldcapacity old 空间大小统计. 

-gcmetacapacity meta 区大小统计. 

-gcutil GC 相关区域的使用率（utilization）统计。 

-printcompilation 打印 JVM 编译统计信息。
```

#### 3.**jmap** 
查看 heap 或类占用空间统计 

```shell
# eap 打印堆内存（/内存池）的配置和使用信息。
jmap -heap pid
# histo 看哪些类占用的空间最多, 直方图
jmap -histo pid
# histo 某个进程 看哪些类占用的空间最多, 前20
jmap -histo:live <pid> | head -20
# ump:format=b,file=xxxx.hprof Dump 堆内存。
jmap -dump:format=b,file=3826.hprof 3826
```

#### 4.**jstack** 
查看线程信息 

```shell
-F 强制执行 thread dump. 可在 Java 进程卡死（hung 住）时使用, 此选项可能需要系统权限。

-m 混合模式(mixed mode),将 Java 帧和 native帧一起输出, 此选项可能需要系统权限。

-l 长列表模式. 将线程相关的 locks 信息一起输出，比如持有的锁，等待的锁。
```



#### 5.**jcmd** 
执行 JVM 相关分析命令（整合命令） 

```shell
# 查看可以查看的一些参数
jcmd [pid] help
# jcmd [pid] [参数]
jcmd pid VM.version
jcmd pid VM.flags
jcmd pid VM.command_line
jcmd pid VM.system_properties
jcmd pid Thread.print
jcmd pid GC.class_histogram
jcmd pid GC.heap_info
```



## 二.详细说明

#### 1.JDK list - 查看jdk列表    . JDK jdk1.8.0_66 (指定某一个JDK版本)

#### 2.jmap - 查看内存对象信息 : jmap -dump:live,format=b,file=heap.bin 

问题1 : JVM内存非常高GC之后也没有下降

可能原因:es用了netty, 默认开启内存缓存池了,导致占用空间特别大

解决方法: Java启动时候增加参数,禁用  -Dio.netty.allocator.type=unpooled

#### 3.jstat - 命令可以查看堆内存各部分的使用量

- jstat [-命令选项] [vmid] [间隔时间/毫秒] [查询次数]

- 以下的统计空间单位,未标明的  都是KB

(1) 类加载统计

```shell
jstat -class <pid>

#Loaded:加载class的数量
#Bytes：所占用空间大小
#Unloaded：未加载数量
#Bytes:未加载占用空间
#Time：时间
```

(2)编译统计

```shell
jstat -compiler <pid>

#Compiled：编译数量。
#Failed：失败数量
#Invalid：不可用数量
#Time：时间
#FailedType：失败类型
#FailedMethod：失败的方法
```

(3)垃圾回收统计

```shell
jstat -gc <pid>

#S0C：第一个幸存区的大小
#S1C：第二个幸存区的大小
#S0U：第一个幸存区的使用大小
#S1U：第二个幸存区的使用大小
#EC：伊甸园区的大小
#EU：伊甸园区的使用大小
#OC：老年代大小
#OU：老年代使用大小
#MC：方法区大小
#MU：方法区使用大小
#CCSC:压缩类空间大小
###CCSU:压缩类空间使用大小
#YGC：年轻代垃圾回收次数
##YGCT：年轻代垃圾回收消耗时间
#FGC：老年代垃圾回收次数
#FGCT：老年代垃圾回收消耗时间
#GCT：垃圾回收消耗总时间
```

(4)堆内存统计

```shell
jstat -gccapacity <pid>

#NGCMN：新生代最小容量 
#NGCMX：新生代最大容量 
#NGC：当前新生代容量 
#S0C：第一个幸存区大小 
#S1C：第二个幸存区的大小 
#EC：伊甸园区的大小 
#OGCMN：老年代最小容量 
#OGCMX：老年代最大容量 
#OGC：当前老年代大小 
#OC:当前老年代大小 
#MCMN:最小元数据容量 
#MCMX：最大元数据容量 
#MC：当前元数据空间大小 
#CCSMN：最小压缩类空间大小 
#CCSMX：最大压缩类空间大小 
#CCSC：当前压缩类空间大小 
#YGC：年轻代gc次数 
#FGC：老年代GC次数
```

(5)新生代垃圾回收统计

```shell
jstat -gcnew <pid>

#S0C：第一个幸存区大小 
#S1C：第二个幸存区的大小 
#S0U：第一个幸存区的使用大小 
#S1U：第二个幸存区的使用大小 
#TT:对象在新生代存活的次数 
#MTT:对象在新生代存活的最大次数 
#DSS:期望的幸存区大小 
#EC：伊甸园区的大小 
#EU：伊甸园区的使用大小 
#YGC：年轻代垃圾回收次数 
#YGCT：年轻代垃圾回收消耗时间
```

(6)新生代内存统计

```shell
jstat -gcnewcapacity <pid>

#NGCMN：新生代最小容量 
#NGCMX：新生代最大容量 
#NGC：当前新生代容量 
#S0CMX：最大幸存1区大小 
#S0C：当前幸存1区大小 
#S1CMX：最大幸存2区大小 
#S1C：当前幸存2区大小 
#ECMX：最大伊甸园区大小 
#EC：当前伊甸园区大小 
#YGC：年轻代垃圾回收次数 
#FGC：老年代回收次数
```

(7)老年代垃圾回收统计

```shell
jstat -gcold <pid>

#MC：方法区大小 
#MU：方法区使用大小 
#CCSC:压缩类空间大小  
#CCSU:压缩类空间使用大小  
#OC：老年代大小  
#OU：老年代使用大小  
#YGC：年轻代垃圾回收次数  
#FGC：老年代垃圾回收次数  
#FGCT：老年代垃圾回收消耗时间  
#GCT：垃圾回收消耗总时间
```

(8)老年代内存统计

```shell
jstat -gcoldcapacity <pid>

#OGCMN：老年代最小容量   
#OGCMX：老年代最大容量   
#OGC：当前老年代大小   
#OC：老年代大小   
#YGC：年轻代垃圾回收次数   
#FGC：老年代垃圾回收次数   
#FGCT：老年代垃圾回收消耗时间   
#GCT：垃圾回收消耗总时间
```

(9)JDK7 下 永久代空间统计

```shell
jstat -gcpermcapacity <pid>
   
#PGCMN:最小永久代容量    
#PGCMX：最大永久代容量    
#PGC：当前新生成的永久代空间大小    
#PC ：永久代空间大小    
#YGC：年轻代垃圾回收次数    
#FGC：老年代垃圾回收次数    
#FGCT：老年代垃圾回收消耗时间    
#GCT：垃圾回收消耗总时间
```

(10)JDK8 下 元数据空间统计

```shell
jstat -gcmetacapacity <pid>

#MCMN:最小元数据容量    
#MCMX：最大元数据容量    
#MC：当前元数据空间大小    
#CCSMN：最小压缩类空间大小   
#CCSMX：最大压缩类空间大小    
#CCSC：当前压缩类空间大小    
#YGC：年轻代垃圾回收次数    
#FGC：老年代垃圾回收次数    
#FGCT：老年代垃圾回收消耗时间   
#GCT：垃圾回收消耗总时间
```

(11)总结垃圾回收统计

```shell
jstat -gcutil <pid>

#S0：幸存1区当前使用比例    
#S1：幸存2区当前使用比例    
#E：伊甸园区使用比例    
#O：老年代使用比例    
#M：元数据区使用比例    
#CCS：压缩使用比例    
#YGC：年轻代垃圾回收次数    
#FGC：老年代垃圾回收次数    
#FGCT：老年代垃圾回收消耗时间    
#GCT：垃圾回收消耗总时间
```

(12)JVM编译方法统计

```shell
jstat -printcompilation <pid>

#Compiled：最近编译方法的数量 
#Size：最近编译方法的字节码数量  
#Type：最近编译方法的编译类型  
#Method：方法名标识
```

### 4.jar 命令

```shell
jar {ctxu}[vfm0M] [jar-文件] [manifest-文件] [-C 目录] 文件名 ...
 
#其中 {ctxu} 是 jar 命令的子命令，每次 jar 命令只能包含 ctxu 中的一个，它们分别表示：
-c 创建新的 JAR 文件包
-t 列出 JAR 文件包的内容列表
-x 展开 JAR 文件包的指定文件或者所有文件
-u 更新已存在的 JAR 文件包 (添加文件到 JAR 文件包中)
# [vfm0M] 中的选项可以任选，也可以不选，它们是 jar 命令的选项参数
-v 生成详细报告并打印到标准输出
-f 指定 JAR 文件名，通常这个参数是必须的
-m 指定需要包含的 MANIFEST 清单文件
-0 只存储，不压缩，这样产生的 JAR 文件包会比不用该参数产生的体积大，但速度更快
-M 不产生所有项的清单（MANIFEST〕文件，此参数会忽略 -m 参数 
```
例子 : 
 (1) jar tf test.jar
在 test.jar 已经存在的情况下，可以查看 test.jar 中的内容

