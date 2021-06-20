# 简单的压测工具使用

## 一.wrk 的使用

### 1.1 安装
```shell
# 以 mac系统为例 , 假设您的电脑已经准备号git环境,如果没有,请自行查找git安装以及配置方式
# 依次执行如下命令：
git clone https://github.com/wg/wrk.git wrk
cd wrk
# 打包
make
# 将可执行文件移动到 /usr/local/bin 位置
sudo cp wrk /usr/local/bin
```

### 1.2 相关参数
```shell
-c, --connections 跟服务器建立并保持的TCP连接数量
-d, --duration 压测时间
-t, --threads 使用多少个线程进行压测

-s, --script      <S>  指定Lua脚本路径       
-H, --header      <H>  为每一个HTTP请求添加HTTP头      
    --latency          在压测结束后，打印延迟统计信息   
    --timeout     <T>  socket/请求 的超时时间     
-v, --version          打印正在使用的wrk的详细版本信息

# 单位支持 : 
## 数字单位
Numeric arguments may include a SI unit (1k, 1M, 1G)
## 时间单位
Time arguments may include a time unit (2s, 2m, 2h)
```


### 1.3 测试demo
> 项目case
```java
@RestController
@RequestMapping(value = "/test")
@Slf4j
public class TestController {

	@RequestMapping(value = "/testaaa", method = RequestMethod.GET)
	public String test() {
		// 关于 lombok 可以根据需求决定是否使用
		log.info("aa");
		return "aa";
	}
}
```

> 压测case
```shell
# 使用 3 线程,100链接, 压测10s , 最终生成报表
wrk -t3 -c100 -d10s --latency http://localhost:8009/test/testaaa
```

> 结果
```log
Running 10s test @ http://localhost:8009/test/testaaa
  3 threads and 100 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     6.81ms    9.77ms 206.45ms   84.65%
    Req/Sec    13.07k     2.16k   16.69k    80.53%
  Latency Distribution
     50%    1.30ms
     75%   11.08ms
     90%   20.09ms
     99%   40.15ms
  394178 requests in 10.10s, 43.30MB read
Requests/sec:  39016.21
Transfer/sec:      4.29MB
```





## 二. sb的使用





# 参考文献

- [1] [性能测试工具-WRK](https://blog.csdn.net/bug_null/article/details/114482614)