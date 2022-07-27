package com.example.demo.leetcode.search;

/**
 * @author chengzhe yan
 * @description
 * @date 2021/9/16 5:45 下午
 */
public class SearchFirst implements Search {

    @Override
    public int search(final int[] nums, final int i) {
        int start = 0;
        int end = nums.length;
        while (start < end) {
            int curr = (start + end) / 2;
            if (nums[curr] == i) {
                if (curr == 0 || nums[curr - 1] != i) {
                    return curr;
                } else {
                    end = curr;
                }
            } else if (nums[curr] > i) {
                end = curr;
            } else {
                start = curr;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] number = new int[] {
            0,
            1,
            1,
            1,
            5,
            6,
            6,
            8
        };
        int i = new SearchFirst().search(number, 1);
        System.out.println(i);
    }
}
