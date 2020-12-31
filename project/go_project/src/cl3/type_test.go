package cl3

import (
	"testing"
)

type MyType  float32

func TestType(t *testing.T) {
	//var a int32 = 12
	// 报错:不允许隐式类型转换
	//var b1 int64 = a
	//var b2 int64 = int64(a)
	//报错:相同类型的别名也不可以转换
	//var c1 MyType = b1
	//var c2 MyType = MyType(b2)

	//t.Log(a,b1,b2,c1,c2)
}

func TestString(t *testing.T) {
	var s string
	t.Log(len(s))
	t.Log("*" + s +" +")
}
