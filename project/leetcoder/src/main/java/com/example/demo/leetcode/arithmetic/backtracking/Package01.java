package com.example.demo.leetcode.arithmetic.backtracking;

/**
 * @author chengzhe yan
 * @description 01 背包问题
 * @date 2021/10/5 12:54 下午
 */
public class Package01 {

    public int maxWeight = -1;

    /**
     * @param i          表示考察到哪个物品了
     * @param currWeight 表示当前已经装进去的物品的重量和
     * @param items      表示每个物品的重量
     * @param n          表示物品个数
     * @param weight     背包重量
     */
    private void myPackage(int i, int currWeight, int[] items, int n, int weight) {
        // 看到背包中的最后一个物品了,可以退出
        if (i == n) {
            maxWeight = Math.max(currWeight, maxWeight);
            return;
        }
        // 每个物品都有两种情况 , 放和不放
        // 不放
        myPackage(i + 1, currWeight, items, n, weight);
        // 放 要保证不超重
        if (currWeight + items[i] <= weight) {
            myPackage(i + 1, currWeight + items[i], items, n, weight);
        }
    }

    public static void main(String[] args) {
        Package01 package01 = new Package01();
        int[] a = new int[10];
        a[0] = 14;
        a[1] = 22;
        a[2] = 81;
        a[3] = 43;
        a[4] = 56;
        a[5] = 62;
        a[6] = 73;
        a[7] = 81;
        a[8] = 9;
        a[9] = 10;
        package01.myPackage(0, 0, a, 10, 98);
        System.out.println(package01.maxWeight);
    }
}
