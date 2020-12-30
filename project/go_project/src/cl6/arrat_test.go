package cl6

import (
	"testing"
)

func TestArray(t *testing.T) {
	var arr [3] int
	arr1 := [4]int{1, 2, 3, 4}
	arr2 := [...] int{1, 2, 3, 4}
	//初始化数组 , 打印长度
	t.Log(len(arr), len(arr1), len(arr2))
	//遍历
	for i := 0; i < len(arr1); i++ {
		t.Log(arr1[i])
	}
	//遍历
	for index, data := range arr2 {
		t.Log(index, data)
	}
	//数组截取
	arr3 := arr2[1:2]
	for _, data := range arr3 {
		t.Log(data)
	}
	arr3 = arr2[1:]
	for _, data := range arr3 {
		t.Log(data)
	}
	arr3 = arr2[:3]
	for _, data := range arr3 {
		t.Log(data)
	}
}

func TestSlice(t *testing.T) {
	var s0 [] int
	t.Log(len(s0), cap(s0))
	s0 = append(s0, 1)
	t.Log(len(s0), cap(s0))

	s1 := []int{1, 2, 3, 4}
	t.Log(len(s1), cap(s1))
	s1 = append(s1, 1)
	t.Log(len(s1), cap(s1))
	//类型 , 长度 , 容量
	var s2 = make([]int,5,7)
	t.Log(len(s2),cap(s2))
}
