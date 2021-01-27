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

#### 11.linux下同时杀死多个进程


#### 12.nslookup - 查询DNS的记录，查询域名解析是否正常

#### 13.awk - 处理文本,文本分析工具

awk [选项参数] 'script' var=value file(s) 或 awk [选项参数] -f scriptfile var=value file(s)

常用参数 :   -F fs or --field-separator fs : 指定输入文件折分隔符，fs是一个字符串或者是一个正则表达式，如-F:。 -f scripfile or --file scriptfile : 从脚本文件中读取awk命令。 

eg :  # 输出第二列包含 "th"，并打印第二列与第四列 $ awk '$2 ~ /th/ {print $2,$4}' log.txt

#### 14.nc - 检测远程 ip 端口是否监听
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

常用参数 :   -F fs or --field-separator fs : 指定输入文件折分隔符，fs是一个字符串或者是一个正则表达式，如-F:。 -f scripfile or --file scriptfile : 从脚本文件中读取awk命令。 eg :  # 输出第二列包含 "th"，并打印第二列与第四列 $ awk '$2 ~ /th/ {print $2,$4}' log.txt

#### 14.nc - 检测远程 ip 端口是否监听



# 参考资料

- [1] [linux下杀死多个进程](https://blog.csdn.net/lgh1117/article/details/48402285)
- [2] [菜鸟教程-killall](https://www.runoob.com/linux/linux-comm-killall.html)