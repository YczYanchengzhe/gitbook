package com.example.demo.leetcode.sort;

import java.util.Arrays;

/**
 * @author chengzhe yan
 * @description 选择排序
 * @date 2021/8/29 4:02 下午
 */
public class SelectionSort implements Sort {

	@Override
	public void sort(int[] nums, int start, int end) {
		if (nums == null || nums.length == 1) {
			return;
		}
		for (int i = 0; i < nums.length; i++) {
			int tmp = i;
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[j] < nums[tmp]) {
					tmp = j;
				}
			}
			// swap tmp 和 i
			if (tmp != i) {
				swap(nums, tmp, i);
			}
		}
	}

	public static void main(String[] args) {
		Sort sort = new SelectionSort();
		int[] ints = {9, 7, 6, 5, 4};
		sort.sort(ints, 0, 5);
		System.out.println(Arrays.toString(ints));
	}
}
