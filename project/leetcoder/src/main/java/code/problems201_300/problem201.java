package code.problems201_300;

/**
 * className : problem201
 * description : 数字范围按位与
 *
 * @author : yanchengzhe
 * date : 2020/8/23 17:53
 **/
public class problem201 {
    /**
     * 给定范围 [m, n]，其中 0 <= m <= n <= 2147483647，
     * 返回此范围内所有数字的按位与（包含 m, n 两端点）。
     *
     * @param m
     * @param n
     * @return
     */
    public static int rangeBitwiseAnd(int m, int n) {
        //寻找最长相同前缀 - 之后 m 和n 的最小相同前缀
        //1. 位移
//        int count = 0;
//        while (m != n) {
//            m >>= 1;
//            n >>= 1;
//            count++;
//        }
        //Brian Kernighan 算法 n & (n-1)
        while (m < n) {
            n = n & (n-1);
        }
        return  n;
    }

    public static void main(String[] args) {
        int i = rangeBitwiseAnd(5, 16);
        System.out.println(i);
    }
}
