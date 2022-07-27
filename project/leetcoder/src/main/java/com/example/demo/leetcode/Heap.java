package com.example.demo.leetcode;

import com.example.demo.leetcode.sort.HeapSort;
import java.util.Arrays;

/**
 * @author chengzhe yan
 * @description
 * @date 2021/9/21 7:17 下午
 */
public class Heap {
    public static void main(String[] args) {
        Heap heapSort = new Heap();
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
        heapSort.build(nums);
        System.out.println(Arrays.toString(nums));
        nums = heapSort.deleteTop(nums);
        System.out.println(Arrays.toString(nums));
        nums = heapSort.add(nums, 15);
        System.out.println(Arrays.toString(nums));
    }

    public int[] add(int[] nums, int newValue) {
        int[] newArr = new int[nums.length + 1];
        System.arraycopy(nums, 0, newArr, 0, nums.length);
        // 添加元素
        newArr[nums.length] = newValue;
        adjust2(newArr, newArr.length - 1);
        return newArr;
    }

    public int[] deleteTop(int[] nums) {
        // 堆顶和堆底互换
        nums[0] = nums[nums.length - 1];
        int[] newArr = new int[nums.length - 1];
        // 删除堆顶元素
        System.arraycopy(nums, 0, newArr, 0, nums.length - 1);
        adjust(newArr, 0, nums.length - 1);
        return newArr;
    }

    public void build(int[] nums) {
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            adjust(nums, i, nums.length);
        }
    }

    /**
     * 调整某个元素 : 查看该元素是不是孩子中最大的 : 自下往上进行堆化处理
     *
     * @param nums  待调整的数据集
     * @param index 需要调整的下标
     */
    private void adjust2(int[] nums, int index) {
        while (index / 2 > 0 && nums[index] > nums[index / 2 - 1]) {
            swap(nums, index, index / 2 - 1);
            index = index / 2 - 1;
        }
        // 处理头结点临界值问题
        if (nums[0] < nums[1]) {
            swap(nums, 0, 1);
        }
    }

    /**
     * 调整某个元素 : 查看该元素是不是孩子中最大的 : 自上往下进行堆化处理
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
