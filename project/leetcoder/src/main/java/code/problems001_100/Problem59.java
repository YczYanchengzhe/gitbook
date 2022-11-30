package code.problems001_100;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/11/30 22:34
 */
public class Problem59 {
    public int[][] generateMatrix(int n) {
        int[][] arr = new int[n][n];
        int startX = 0;
        int startY = 0;
        int offset = 1;
        int i = 0;
        int j = 0;
        int count = 1;
        // 圈数 为 n/2
        for (int k = 0; k < n / 2; k++) {
            // 左闭右开区间
            for (j = startY; j < n - offset; j++) {
                arr[startX][j] = count++;
            }
            for (i = startX; i < n - offset; i++) {
                arr[i][j] = count++;
            }
            for (; j > startY; j--) {
                arr[i][j] = count++;
            }
            for (; i > startX; i--) {
                arr[i][j] = count++;
            }
            startY++;
            startX++;
            offset++;
        }
        if (n % 2 == 1) {
            arr[startX][startY] = count;
        }
        return arr;
    }

    public static void main(String[] args) {
        int[][] ints = new Problem59().generateMatrix(5);
        System.out.println("a");
    }
}
