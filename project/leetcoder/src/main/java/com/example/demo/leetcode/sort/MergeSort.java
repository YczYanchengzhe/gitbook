package com.example.demo.leetcode.sort;

import java.util.Arrays;

/**
 * @author chengzhe yan
 * @description 归并排序
 * @date 2021/9/2 5:32 下午
 */
public class MergeSort implements Sort {

	@Override
	public void sort(int[] nums, int start, int end) {
		nums = mergeSort(nums, start, end - 1);
		System.out.println(Arrays.toString(nums));
	}

	public int[] mergeSort(int[] nums, int start, int end) {
		if (start == end) {
			return new int[]{nums[start]};
		}
		int mid = start + (end - start) / 2;
		int[] left = mergeSort(nums, start, mid);
		int[] right = mergeSort(nums, mid + 1, end);
		//合并两个排序数组
		int i = 0, j = 0, k = 0;
		int[] result = new int[left.length + right.length];
		while (i < left.length && j < right.length) {
			result[k++] = left[i] < right[j] ? left[i++] : right[j++];
		}
		while (i < left.length) {
			result[k++] = left[i++];
		}
		while (j < right.length) {
			result[k++] = right[j++];
		}
		return result;

	}

	public static void main(String[] args) {
		Sort sort = new MergeSort();
		int[] ints = {9, 7, 6, 5, 4};
		sort.sort(ints, 0, 5);
	}
}
