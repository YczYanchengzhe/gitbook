package cl5

import "testing"

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
