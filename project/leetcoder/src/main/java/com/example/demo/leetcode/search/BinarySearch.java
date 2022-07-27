package com.example.demo.leetcode.search;

/**
 * @ClassName: BinarySearch
 * @Description: TODO
 * @Create by: A
 * @Date: 2021/9/3 21:23
 */
public class BinarySearch implements Search {


    @Override
    public int search(int[] nums, int search) {
        int start = 0;
        int end = nums.length - 1;
        // 注意 退出条件包含等于
        while (start <= end) {
            int tmp = start + ((end - start) / 2);
//            int tmp = start + ((end - start) >> 1);
            if (nums[tmp] > search) {
                end = tmp - 1;
            } else if (nums[tmp] < search) {
                start = tmp + 1;
            } else {
                return tmp;
            }

        }
        return -1;
    }

    public int search_2(int[] nums, int search, int start, int end) {
        if (start > end) {
            return -1;
        }
        int mid = start + ((end - start) / 2);
        if (search == nums[mid]) {
            return mid;
        } else if (search > nums[mid]) {
            return search_2(nums, search, mid + 1, end);
        } else {
            return search_2(nums, search, start, mid - 1);
        }
    }


    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        BinarySearch search = new BinarySearch();
        System.out.println(search.search_2(nums, 9, 0, 7));

    }
}
