package com.example.demo.leetcode.sort;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author chengzhe yan
 * @description : 冒泡排序
 * @date 2021/8/29 3:29 下午
 */
public class BubbleSort implements Sort {

	@Override
	public void sort(int[] nums, int start, int end) {
		for (int i = 0; i < nums.length; i++) {
			// 优化点 如果这一次遍历没有交换 ,可以提前退出
			boolean flag = false;
			for (int j = i; j < nums.length; j++) {
				if (nums[i] > nums[j]) {
					swap(nums, i, j);
					flag = true;
				}
			}
			if (!flag) {
				break;
			}
		}
	}

	public static void main(String[] args) {
		BubbleSort bubbleSort = new BubbleSort();
		int[] ints = {9, 7, 6, 5, 4};
		bubbleSort.sort(ints, 0, 5);
		System.out.println(Arrays.toString(ints));
	}
}
