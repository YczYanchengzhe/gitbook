package com.example.demo.leetcode;

import java.util.Arrays;

/**
 * @author chengzhe yan
 * @description
 * @date 2021/6/5 5:04 下午
 */
public class FindKthLargest {
	/**
	 * 暴力解法
	 *
	 * @param nums
	 * @param k
	 * @return
	 */
	public int findKthLargestOne(int[] nums, int k) {
		Arrays.sort(nums);
		return nums[nums.length - k];
	}

	/**
	 * 快排
	 *
	 * @param nums
	 * @param k
	 * @return
	 */
	public int findKthLargest2(int[] nums, int k) {
		int len = nums.length;
		int left = 0;
		int right = len - 1;
		// 转换一下，第 k 大元素的索引是 len - k
		int target = len - k;
		while (true) {
			int index = partition(nums, left, right);
			if (index == target) {
				return target;
			} else if (index < target) {
				left = index + 1;
			} else {
				right = index - 1;
			}
		}
	}

	/**
	 * 在数组 nums 的子区间 [left, right] 执行 partition 操作，返回 nums[left] 排序以后应该在的位置
	 * 在遍历过程中保持循环不变量的语义
	 * 1、[left + 1, j] < nums[left]
	 * 2、(j, i] >= nums[left]
	 *
	 * @param nums
	 * @param left
	 * @param right
	 * @return
	 */
	public int partition(int[] nums, int left, int right) {
		int pivot = nums[left];
		int j = left;
		for (int i = left + 1; i <= right; i++) {
			if (nums[i] < pivot) {
				// 小于 pivot 的元素都被交换到前面
				j++;
				if (j != i) {
					swap(nums, j, i);
				}
			}
		}
		// 在之前遍历的过程中，满足 [left + 1, j] < pivot，并且 (j, i] >= pivot
		swap(nums, j, left);
		// 交换以后 [left, j - 1] < pivot, nums[j] = pivot, [j + 1, right] >= pivot
		return j;
	}

	private void swap(int[] nums, int index1, int index2) {
		int temp = nums[index1];
		nums[index1] = nums[index2];
		nums[index2] = temp;
	}

	/**
	 * 堆排序
	 */
	public int findKthLargest3(int[] nums, int k) {
		// 构建 大顶堆  ,
		for (int i = nums.length / 2 - 1; i >= 0; i--) {
			adjust(nums, i, nums.length);
		}
		// 触发 k-1 次排序操作
		for (int i = nums.length - 1; i > nums.length - k; i--) {
			swap(nums, 0, i);
			adjust(nums, 0, i);
		}
		return nums[0];
	}

	public void adjust(int[] nums, int index, int length) {
		int tmp = nums[index];
		for (int i = index * 2 + 1; i < length; i = i * 2 + 1) {
			if (i + 1 < length && nums[i] < nums[i + 1]) {
				i++;
			}
			if (nums[i] > tmp) {
				nums[index] = nums[i];
				index = i;
			} else {
				break;
			}
		}
		nums[index] = tmp;
	}


	public static void main(String[] args) {
		FindKthLargest findKthLargest = new FindKthLargest();
		int[] nums = new int[]{6, 3, 4, 8, 2, 7, 1};
		int result = findKthLargest.findKthLargest3(nums, 5);
		System.out.println(result);
		System.out.println(Arrays.toString(nums));
	}
}
