package com.example.demo.leetcode.sort;

/**
 * @author chengzhe yan
 * @description
 * @date 2021/8/29 3:52 下午
 */
public interface Sort {
	public void sort(int[] nums, int start, int end);


	default void swap(int[] nums, int n1, int n2) {
		int tmp = nums[n1];
		nums[n1] = nums[n2];
		nums[n2] = tmp;
	}
}
