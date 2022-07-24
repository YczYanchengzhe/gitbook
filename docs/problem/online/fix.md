# 故障问题解决手册

## 一. 线上机器出现 CPU 使用过高处理手册

```shell
# 1. 找到进程ID
ps -aux | grep <info>
# 2. 根据进程 ID 找到占用 cpu 高的线程
ps -Hp <pid>
# 3. 找到占用最高的线程 id,转换为 16 进制数字 : 
printf "%x\n" <tid>
# 4. dump 线程信息
jstack <pid> > thread.log
# 5. 找到问题线程 : 注意这里的 tid 是上面转换的 16 进制 id,-C 是查看符合条件的日志上下 n 条数据
cat thread.log | grep <tid>  -C 10
```

## 二. 使用 mat 内存分析工具
#### 2.1 JDK 问题设置
```xml
<string>-vm</string><string>/jdkPath/azul-11.0.11/Contents/Home/bin/java</string>
```