package com.example.demo.leetcode.arithmetic.backtracking;


/**
 * @author chengzhe yan
 * @description : 8皇后问题
 * @date 2021/10/5 12:54 下午
 */
public class Queens8 {

    /**
     * 数组的维度
     */
    private final int count;

    /**
     * 下标代表第几行 , 元素代表第几列 , 通过这个数组存储 Q 都放在了哪些位置
     */
    private final int[] arr;

    /**
     * 计算第几行
     *
     * @param row
     */
    public void calc8Queen(int row) {
        // 计算完成
        if (row == count) {
            // 打印结果
            printQueen(arr);
        }
        // 每一行中都有 count 中放置方法
        for (int column = 0; column < arr.length; column++) {
            // 判断当前行列能否放置
            if (isSet(row, column)) {
                // 进行放置
                arr[row] = column;
                // 可以的话 放置下一行
                calc8Queen(row + 1);
            }
        }

    }

    private void printQueen(int[] arr) {
        // 使用二位数组的方式打印
        for (int row = 0; row < arr.length; row++) {
            for (int column = 0; column < arr.length; column++) {
                if (arr[row] == column) {
                    System.out.print("Q ");
                } else {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * 判断该位置是否可以放置
     *
     * @param row
     * @param column
     * @return
     */
    private boolean isSet(int row, int column) {
        // 判断行列是否存在Q , 对角线是否存在Q
        int leftUp = column - 1;
        int rightUp = column + 1;
        // 逐行判断
        for (int currRow = row - 1; currRow >= 0; currRow--) {
            // 判断行列是否存在Q , 左对角线 , 右对角线
            if (arr[currRow] == column || arr[currRow] == leftUp || arr[currRow] == rightUp) {
                return false;
            }
            // 左右对角线移动
            leftUp--;
            rightUp++;
        }
        return true;
    }

    public static void main(String[] args) {
        Queens8 queens8 = new Queens8(4);
    }


    public Queens8() {
        this.count = 8;
        arr = new int[count];
        calc8Queen(0);
    }

    public Queens8(int count) {
        this.count = count;
        arr = new int[count];
        calc8Queen(0);
    }
}
