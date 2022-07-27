package com.example.demo.leetcode.sort;

import java.util.Arrays;

/**
 * @author chengzhe yan
 * @description 堆排序
 * @date 2021/6/20 2:57 下午
 */
public class HeapSort implements Sort {

    public static void main(String[] args) {
        HeapSort heapSort = new HeapSort();
        int[] nums = new int[] {
            10,
            15,
            9,
            8,
            7,
            6,
            5,
            4,
            3,
            1,
            2
        };
        heapSort.sort(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
    }

    @Override
    public void sort(int[] nums, int start, int end) {
        // 从 最后一个非叶子结点进行查找
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            adjust(nums, i, nums.length);
        }
        // 交换堆顶和堆尾元素
        for (int i = nums.length - 1; i >= 0; i--) {
            swap(nums, i, 0);
            adjust(nums, 0, i);
        }
    }

    /**
     * 调整某个元素 : 查看该元素是不是孩子中最大的
     *
     * @param nums   待调整的数据集
     * @param index  需要调整的下标
     * @param length 数据集长度(在进行堆排序之后,长度会不断减小)
     */
    private void adjust(int[] nums, int index, int length) {
        int tmp = nums[index];
        for (int i = index * 2 + 1; i < length; i = i * 2 + 1) {
            // 找两个孩子中的相对较大的那个
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

    private void heapify(int[] nums, int n, int i) {
        while (true) {
            int maxPos = i;
            if (i * 2 <= n && nums[i] < nums[i * 2]) {
                maxPos = i * 2;
            }
            if (i * 2 + 1 <= n && nums[maxPos] < nums[i * 2 + 1]) {
                maxPos = i * 2 + 1;
            }
            if (maxPos == i) {
                break;
            }
            swap(nums, i, maxPos);
            i = maxPos;
        }
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
        //		int tmp = nums[i];
        //		nums[i] = nums[j];
        //		nums[j] = tmp;

        if (nums[j] == nums[i]) {
            return;
        }
        nums[i] = nums[j] ^ nums[i];
        nums[j] = nums[j] ^ nums[i];
        nums[i] = nums[j] ^ nums[i];
    }

}
