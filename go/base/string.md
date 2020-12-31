# 字符串



## 一.string

- string 是数据类型，不是引⽤或指针类型 
- string 是只读的 byte slice，len 函数是指所包含的 byte 数  , 并且其byte不可变
- string 的 byte 数组可以存放任何数据 , 即使编码不识别



## 二.字符集

1. Unicode 是⼀种字符集（code point）
2. UTF8 是 unicode 的存储实现 （转换为字节序列的规则）

| 字符          | "中"             |
| ------------- | ---------------- |
| unicode       | 0x4E2D           |
| UTF-8         | 0xE4B8AD         |
| string/[]byte | [0xE4,0xB8,0xAD] |

## 三.字符串处理

#### 1.字符串分割
```go
strings.Split(s, "-")
```
#### 2.字符串拼接
```go
strings.Join(a, ",")
```
#### 3. 字符串转换int
```go
//注意:会返回两个值
strconv.Atoi(str)
```
#### 4.int转换字符串
```go
strconv.Itoa(10)
```

## 四.例子

```go
package cl8

import (
	"strconv"
	"strings"
	"testing"
)

func TestString(t *testing.T) {
	aaa:="中"
	asa := []rune(aaa)
	t.Logf("%x",asa[0])
	t.Logf("%s , %[1]x",aaa)

	var s string
	s = "你好么"
	//一个汉字3byte
	t.Log(len(s))
	//rune 取出字符串的数组切片
	c := [] rune(s)
	t.Logf("%c", c)
	//遍历字符串
	for _, c := range s {
		//都按照第一个参数去格式化
		t.Logf("%[1]c %[1]x", c)
	}
}

func TestStringFunc(t *testing.T) {
	s := "q-w-e-r"
	t.Log(len(s))
	a := strings.Split(s, "-")
	//遍历切片
	for _, b := range a {
		t.Logf("%s", b)
	}
	d := strings.Join(a, ",")
	t.Log(d)

	str := strconv.Itoa(10)
	t.Log(str + "sss")

	//返回两个值 , 其中一个标识是否有错
	if i, err := strconv.Atoi(str); err == nil {
		t.Log(i + 10)
	}

}
```

