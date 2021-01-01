package cl2

import "testing"

func TestVar(t *testing.T) {

	//var a int = 1
	//var b int = 1

	//var (
	//	a  =1
	//	b  =1
	//)
	a := 1
	b := 1

	var tmp int
	for i := 0; i < 5; i++ {
		tmp = a + b
		a = b
		b = tmp
		t.Log(tmp)
	}

}

func TestChange(t *testing.T) {
	a := 1
	b := 2
	t.Log(a, b)
	a, b = b, a
	t.Log(a, b)
}

const (
	Mondy = iota + 1
	Tuesday
	Wednesday
)
const(
	Reradable = 1<<iota
	Writeable
	Executable
)
func TestConst(t *testing.T) {
	t.Log(Mondy,Tuesday,Wednesday)
	t.Log(Reradable,Writeable,Executable)
}
