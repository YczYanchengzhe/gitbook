# Linux 常用命令

#### 1. find - 查找文件

#### 2.ps - 查看进行相关信息

#### 3.netstat - 检验本机各端口的网络连接情况

#### 4.zip unzip  tar 打包相关命令

#### 5.crontab - 定时任务

#### 6.strings - 打印文件中可打印的字符 (可用于查找某一个文件,例如全量包中的低版本jar)

#### 7.nohup 

​		nohup 命令运行由 Command参数和任何相关的 Arg参数指定的命令，忽略所有挂断（SIGHUP）信号。在注销后使用 nohup 命令运行后台中的程序。要运行后台中的 nohup 命令，添加 & （ 表示“and”的符号）到命令的尾部。

#### 8.curl - 利用URL规则在命令行下工作的文件传输工具

#### 9.du / df  - 查看磁盘占用情况 
为了便于查看 , 一般加上 -h 

#### 10.grep - 在每个文件或标准输入中搜索 (-v 搜索不匹配的)

#### 11.linux 下杀死多个进程
```shell
# 批量杀死进程
ps -ef  | grep 关键字| grep -v grep | cut -c 5-23 |xargs kill -9

# kill 指定信息相关进程 : killall 进程名 
# 杀死java相关进程
killall java 
```

> `cut -c 5-23` : 是截取输入行的第5个字符到第23个字符，而这正好是进程号PID。

> `xargs kill -9`中的xargs命令是用来把前面命令的输出结果（PID）作为“kill -9”命令的参数，并执行该令。


#### 12.nslookup - 查询DNS的记录，查询域名解析是否正常

#### 13.awk - 处理文本,文本分析工具

awk [选项参数] 'script' var=value file(s) 或 awk [选项参数] -f scriptfile var=value file(s)

常用参数 :  
```shell
-F fs or --field-separator fs # 指定输入文件折分隔符，fs是一个字符串或者是一个正则表达式
-f scripfile or --file scriptfile # 从脚本文件中读取awk命令
# 输出第二列包含 "th"，并打印第二列与第四列 
awk '$2 ~ /th/ {print $2,$4}' log.txt
# 指定  ' 为分隔符,并打印分隔后的第五个和第六个参数
awk -F ' ' '{print $5 $6 }'
```

#### 14.nc - 检测远程 ip 端口是否监听

#### 15. iostat

```
-C 显示CPU使用情况
-d 显示磁盘使用情况
-k 以 KB 为单位显示
-m 以 M 为单位显示
-N 显示磁盘阵列(LVM) 信息
-n 显示NFS 使用情况
-p[磁盘] 显示磁盘和分区的情况
-t 显示终端和CPU的信息
-x 显示详细信息
-V 显示版本信息
```

```shell
# 常规用法
iostat -d -k 1 10         #查看TPS和吞吐量信息
iostat -d -x -k 1 10      #查看设备使用率（%util）、响应时间（await）
iostat -c 1 10            #查看cpu状态
```



##### (1) 基本使用

```shell
# -d 表示，显示设备（磁盘）使用状态；
# -k某些使用block为单位的列强制使用Kilobytes为单位；
# 1 10表示，数据显示每隔1秒刷新一次，共显示10次。
iostat -d -k 1 10
```

![1617900494263](../../resources/cmd/linux/iostat_1.png)

含义说明 : 

```
tps：该设备每秒的传输次数。“一次传输”意思是“一次I/O请求”。多个逻辑请求可能会被合并为“一次I/O请求”。“一次传输”请求的大小是未知的。

kB_read/s：每秒从设备（drive expressed）读取的数据量；
kB_wrtn/s：每秒向设备（drive expressed）写入的数据量；

kB_read：读取的总数据量；
kB_wrtn：写入的总数量数据量；

这些单位都为Kilobytes。
```

##### (2) -x 参数

```shell
# -x 获得更多统计信息
iostat -d -x -k 1 10
```

![1617900694341](../../resources/cmd/linux/iostat_2.png)

含义说明 : 

```
rrqm/s:每秒进行 merge 的读操作数目.即 delta(rmerge)/s（当系统调用需要读取数据的时候，VFS将请求发到各个FS，如果FS发现不同的读取请求读取的是相同Block的数据，FS会将这个请求合并Merge）
wrqm/s:每秒进行 merge 的写操作数目.即 delta(wmerge)/s

r/s:每秒完成的读 I/O 设备次数.即 delta(rio)/s
w/s:每秒完成的写 I/O 设备次数.即 delta(wio)/s

rsec/s:每秒读扇区数.即 delta(rsect)/s
wsec/s:每秒写扇区数.即 delta(wsect)/s

rkB/s:每秒读K字节数.是 rsect/s 的一半,因为每扇区大小为512字节.(需要计算)
wkB/s:每秒写K字节数.是 wsect/s 的一半.(需要计算)

avgrq-sz: 平均每次设备I/O操作的数据大小 (扇区).delta(rsect+wsect)/delta(rio+wio)
avgqu-sz: 平均I/O队列长度.即 delta(aveq)/s/1000 (因为aveq的单位为毫秒).

await:平均每次设备I/O操作的等待时间 (毫秒).即 delta(ruse+wuse)/delta(rio+wio)

svctm:平均每次设备I/O操作的服务时间 (毫秒).即 delta(use)/delta(rio+wio)

%util:一秒中有百分之多少的时间用于 I/O 操作,或者说一秒中有多少时间 I/O 队列是非空的.即 delta(use)/s/1000 (因为use的单位为毫秒)
```



##### （3）-c参数

```shell
# cpu相关信息
iostat -c 1 10
```

![1617901210884](../../resources/cmd/linux/1617901210884.png)

```
%user：CPU处在用户模式下的时间百分比

%nice：CPU处在带NICE值的用户模式下的时间百分比

%system：CPU处在系统模式下的时间百分比

%iowait：CPU等待输入输出完成时间的百分比

%steal：管理程序维护另一个虚拟处理器时，虚拟CPU的无意识等待时间百分比

%idle：CPU空闲时间百分比 
```


#### 16. uniq - 检查及删除文本文件中重复出现的行列，一般与 sort 命令结合使用

语法 : 
```shell
uniq [-cdu][-f<栏位>][-s<字符位置>][-w<字符位置>][--help][--version][输入文件][输出文件]
```
参数 : 
```shell
-c或--count 在每列旁边显示该行重复出现的次数。
-d或--repeated 仅显示重复出现的行列。
-f<栏位>或--skip-fields=<栏位> 忽略比较指定的栏位。
-s<字符位置>或--skip-chars=<字符位置> 忽略比较指定的字符。
-u或--unique 仅显示出一次的行列。
-w<字符位置>或--check-chars=<字符位置> 指定要比较的字符。
--help 显示帮助。
--version 显示版本信息。
[输入文件] 指定已排序好的文本文件。如果不指定此项，则从标准读取数据；
[输出文件] 指定输出的文件。如果不指定此选项，则将内容显示到标准输出设备（显示终端）。
```

注意 :  当重复的行并不相邻时，uniq 命令是不起作用的,所以一般和sort结合使用

```shell
# 先排序,在去重 ,在统计数量
sort  test | uniq | wc -l
```


# 参考资料

- [1] [linux下杀死多个进程](https://blog.csdn.net/lgh1117/article/details/48402285)
- [2] [菜鸟教程-killall](https://www.runoob.com/linux/linux-comm-killall.html)
- [3] [Linux下使用iostat 监视I/O状态](https://www.cnblogs.com/chenpingzhao/p/5115063.html)
- [4] [Linux iostat监测IO状态](https://www.orczhou.com/index.php/2010/03/iostat-detail/)