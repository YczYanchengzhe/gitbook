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
