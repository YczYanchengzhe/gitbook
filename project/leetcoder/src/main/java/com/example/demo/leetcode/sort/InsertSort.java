package com.example.demo.leetcode.sort;

import java.util.Arrays;

/**
 * @author chengzhe yan
 * @description 插入排序
 * @date 2021/8/29 3:45 下午
 */
public class InsertSort implements Sort {

	@Override
	public void sort(int[] nums, int start, int end) {
		if (end < 1) {
			return;
		}
		for (int i = 1; i < nums.length; i++) {
			int value = nums[i];
			// 前j 个元素都排序好了
			int j = i - 1;
			for (; j >= 0; j--) {
				// 前一个值覆盖后一个的数值,相当于往后挪
				if (nums[j] > value) {
					nums[j + 1] = nums[j];
				} else {
					break;
				}
			}
			// 找到位置之后放入
			nums[j+1] = value;
		}
	}

	public static void main(String[] args) {
		Sort sort = new InsertSort();
		int[] ints = {9, 7, 6, 5, 4};
		sort.sort(ints, 0, 5);
		System.out.println(Arrays.toString(ints));
	}
}
