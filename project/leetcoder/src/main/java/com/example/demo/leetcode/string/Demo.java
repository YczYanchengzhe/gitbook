package com.example.demo.leetcode.string;

import java.util.Date;
import java.util.Scanner;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/9/3 16:25
 */
public class Demo {
	public static void main(String[] args) {
		String demo = "q";
		/*
			将字符串重复 n 倍后返回
		 */
		String repeat = demo.repeat(12);
		System.out.println(repeat);
		// < 复用前一个参数
		System.out.printf("%s %tB %<te, %<tY","Due date:", new Date());
	}
}
