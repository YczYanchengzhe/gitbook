# 相关知识 



## 1. 协程 ： goroutine



## 2. panic ： 
当出现异常时候，会打印协程堆栈信息 ，进而排查问题


## 3. go-map 并发问题
go 的map并发问题 ， go不允许map并发读写操作，存在的话会直接进程退出。