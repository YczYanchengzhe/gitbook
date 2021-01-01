package cl4

import "testing"

func TestArray(t *testing.T) {
	a := [...]int{1, 2, 3, 4}
	//b := [3]int{1, 2, 3}
	c := [...]int{1, 2, 3, 4}
	d := [...]int{1, 3, 3, 4}

	//t.Log(a == b)
	t.Log(a == c)
	t.Log(a == d)
	t.Log(5 &^ 6)
}
