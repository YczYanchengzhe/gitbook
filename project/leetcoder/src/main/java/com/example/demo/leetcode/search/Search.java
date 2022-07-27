package com.example.demo.leetcode.search;

/**
 * @author chengzhe yan
 * @description
 * @date 2021/9/16 5:29 下午
 */
public interface Search {
    /**
     * 在数组中查询某个数,返回他的下标
     *
     * @param nums 数组
     * @param i 需要查找
     * @return 所在下标 , 如果不存在返回-1
     */
    int search(int[] nums, int i);
}
