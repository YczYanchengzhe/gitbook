package main

import (
	"fmt"
	"net/http"
)

func main() {
	fmt.Print("Hello world")

	go func() {
		http.ListenAndServe("0.0.0.0:8023", nil)
	}()

	select {}
}


