package cl7

import (
	"testing"
)

func TestMap(t *testing.T) {
	m := map[string]int{"sa": 1, "122": 2}
	//遍历
	for k, v := range m {
		t.Log(k, v)
	}
	//返回两个变量 , 第一个是值 , 第二个是是否存在
	if e, ok := m["sasa"]; ok {
		t.Logf("sa is : %d ,%t  \n", e, ok)
	} else if e, ok := m["sa"]; ok {
		t.Logf("sasa is : %d ,%t  \n", e, ok)
	}
	m1 := map[string]int{}
	t.Log(m1["qq"])
	m1["qq"] = 1
	t.Log(m1["qq"])
	m2 := make(map[string]int, 10)
	t.Log(m2["10"])
}

func TestSet(t *testing.T) {
	m := map[int]bool{}
	m[1] = true
	m[2] = true
	t.Log(m[1])
	delete(m,1)
	t.Log(m[1])
}
