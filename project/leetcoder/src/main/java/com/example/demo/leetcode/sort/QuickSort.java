package com.example.demo.leetcode.sort;

import java.util.Arrays;

/**
 * @author chengzhe yan
 * @description 快排
 * @date 2021/6/5 5:49 下午
 */
public class QuickSort implements Sort {

    private void quickSort(int[] a, int head, int tail) {
        // 借助哨兵节点减少交换
        int low = head;
        int high = tail;
        int pivot = a[low];
        if (low < high) {
            while (low < high) {
                // 从右找,一直找到一个比基准小的,
                while (low < high && pivot <= a[high]) {
                    high--;
                }
                // 交换
                a[low] = a[high];
                // 从左找,一直找到一个比基准大的,
                while (low < high && pivot >= a[low]) {
                    low++;
                }
                // 交换
                a[high] = a[low];
            }
            a[low] = pivot;
            // 分段操作
            if (low > head + 1) {
                quickSort(a, head, low - 1);
            }
            if (high < tail - 1) {
                quickSort(a, high + 1, tail);
            }
        }
    }

    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        int[] nums = new int[] {
            6,
            3,
            1,
            8,
            2,
            7,
            4
        };
        quickSort.quickSort(nums, 0, nums.length - 1);
        quickSort.sort(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
    }

    @Override
    public void sort(int[] nums, int start, int end) {
        if (start > end) {
            return;
        }
        // 分段 排序
        // 不停进行分区 , 分区左边都是小于分区点 , 分区右边都是大于分区点,不断分区,直到区间缩小到1
        int partition = divide(nums, start, end);
        sort(nums, start, partition - 1);
        sort(nums, partition + 1, end);
    }

    private int divide(int[] nums, int start, int end) {
        // 选择基准 按照最后一个为基准
        int base = nums[end];
        while (start < end) {
            while (start < end && nums[start] <= base) {
                start++;
            }
            if (start < end) {
                swap(nums, start, end);
                end--;
            }
            while (start < end && nums[end] >= base) {
                end--;
            }
            if (start < end) {
                swap(nums, start, end);
                start++;
            }
        }
        return start;
    }

    /**
     * 数据交换
     *
     * @param nums
     * @param i
     * @param j
     */
    @Override
    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
