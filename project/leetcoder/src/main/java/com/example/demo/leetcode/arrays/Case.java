package com.example.demo.leetcode.arrays;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/9/3 16:48
 */
public class Case {
	public static void main(String[] args) {
		int[] m1 = new int[]{
				1, 2, 3
		};
		int[] m2 = Arrays.copyOf(m1, m1.length);
		Arrays.stream(m2).forEach(System.out::println);
	}
}
