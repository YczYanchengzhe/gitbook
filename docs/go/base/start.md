# Go 语言 开始啦



## 一.应用程序的入口

1. 必须是main 包 : package main
2. 必须是main方法 : func main()
3. 文件名不一定是 main.go

```go
package main

import "fmt"

func main() {
	fmt.Println("Hello World")
}
```

4. Go 中main函数不支持任何返回值 ,可以使用os.Exit(code) 来返回指定错误码
5. Go 中main函数的入参获取使用`os.Args`,其中第一个参数是执行的exe文件

```go
package main

import (
	"fmt"
	"os"
)

func main() {
	if len(os.Args) > 1 {
		fmt.Println("Hello World" + os.Args[1])
	}
	os.Exit(111)
}
```

6. 测试
> 测试文件必须以`**_test.go`结尾
> 测试函数必须以`Test**`开头

```go
package cl2_test

import "testing"

func TestGo(t *testing.T){
	t.Log("My first test")
}
```



# 二.变量,常量

## 1. 变量
#### 和其他语言差异
- 赋值可以自动进行类型推断
- 在一个赋值语句中可以对多个变量进行同时赋值

```go
//声明
//第一种
var a int = 1
var b int = 2
//第二种
var (
	a int = 1
    b	  = 1
)
//第三种
a := 1
```
```go
//交换两变量
a :=1
b :=2
tmp :=a
a =b
b = tmp
t.log(a,b)
//或者直接
a,b=b,a
t.log(a,b)
```
## 2.常量

```go
// iota 表示下面的自增
const(
	Monday = iota + 1 	//1
	Tuesday				//2
	Wednesday			//3
)

const(
	Reradable = 1<<iota	//1
	Writeable			//2
	Executable			//4
)
```



# 三.数据类型

## 1.数据类型以及表示

| 数据类型 | 表示                                     | 描述                         |
| -------- | ---------------------------------------- | ---------------------------- |
| bool     | bool                                     | 布尔                         |
| string   | string                                   | 字符串                       |
| int      | int  int8  int16  int32  int64           | 有符号整型                   |
| uint     | uint  uint8 uint16 uint32 uint64 uintptr | 无符号整型                   |
| byte     |                                          | 类似 uint8                   |
| rune     |                                          | 类似 int32                   |
| float    | float32  float64                         | 浮点型                       |
| complex  | complex64  complex128                    | 实数和虚数                   |
| uintptr  | uintptr                                  | 无符号整型，用于存放一个指针 |

> **complex64** : 32 位实数和虚数 
>
>  **complex128** : 64 位实数和虚数 

## 2.不支持隐式类型转换

```go
package cl3

import "testing"

type MyType  float32

func TestType(t *testing.T) {
	var a int32 = 12
	// 报错:不允许隐式类型转换
	var b1 int64 = a
	var b2 int64 = int64(a)
	//报错:相同类型的别名也不可以转换
	var c1 MyType = b1
	var c2 MyType = MyType(b2)

	t.Log(a,b1,b2,c1,c2)
}
```

## 3.类型的预定义值
(1) math.MaxInt64
(2) math.MaxFloat64
(3) math.MaxUint32

## 4.注意
(1) 不支持指针运算
(2) 字符串默认被初始化为空字符串`""`
```go
func TestString(t *testing.T) {
	var s string
	t.Log(len(s))
	t.Log("*" + s +" +")
}
```
(3) 没有 前置++,-- 
(4) 比较数组使用`==`
> 相同维数且含有相同元素个数的数组才可以比较
> 每个元素都相同才相等
```go
func TestArray(t *testing.T) {
	a := [...]int{1, 2, 3, 4}
	//b := [3]int{1, 2, 3}
	c := [...]int{1, 2, 3, 4}
	d := [...]int{1, 3, 3, 4}

	//t.Log(a == b)
	t.Log(a == c)
	t.Log(a == d)

}
```
(5) 位运算符
> &^ 按位置零 : 
> 右边操作数位为1 , 无论左边是什么都置零
> 右边操作数为0 , 无论左边是什么都不变
```go
1 &^ 0 \\ 1
1 &^ 1 \\ 0
0 &^ 1 \\ 0
0 &^ 0 \\ 0
5 &^ 6 \\ 1
```

(6) 对于不关心的数据 , 可以使用`_` 下换线占位

# 四.条件和循环

## 1.循环 : for
```go
//无限循环
for {
}
//while循环
for condition {
}
```
## 2.条件 : if
> if 后面可以进行变量赋值
> condition必须是布尔值
```go
if var declaration; condition {
}
```

## 3.选择 : switch
>1. 条件表达式不限制为常量或者整数；
>2. 单个case中,可以出现多个结果选项,使用`,`号分割
>3. 不需要在case之后明确break,Go语言会默认补充上
>4. 可以不设定switch之后的条件表达式,在此情况下,switch结构和if...else逻辑作用相同

```go
switch var declaration; condition {
    case 1:
    case 2:
    default:
}
//switch 后面无条件类似if/else
switch {
}
```

例子 : 
```go
func TestChoose(t *testing.T) {
	for i := 0; i < 5; i++ {
		switch q := i; q {
		case 0, 2:
			t.Log("case 1 is ", i)
		case 1, 3:
			t.Log("case 2 is ", i)
		default:
			t.Log("case 3 is ", i)
		}
	}

	//if 后面可以加赋值 , 最终判断必须是布尔值
	if a := 12; a == 12 {
		t.Log("test")
	}

	var m = 1
	var n = 2
	//switch 后面没有语句,可以视为 if else
	switch {
	case m%n == 0:
		t.Log(0)
	case n-m == 1:
		t.Log(1)

	}
}
```


