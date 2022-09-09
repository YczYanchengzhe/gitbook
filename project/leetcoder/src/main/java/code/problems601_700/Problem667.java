package code.problems601_700;

import java.lang.reflect.AnnotatedArrayType;
import java.util.Arrays;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/9/8 22:02
 */
public class Problem667 {


    public int[] constructArray(int n, int k) {
        int[] result = new int[n];
        int idx = 0;
        for (int i = 1; i < n - k; i++) {
            result[idx] = i;
            idx++;
        }
        for (int i = n - k, j = n; i <= j; i++, j--) {
            result[idx] = i;
            idx++;
            if (i != j) {
                result[idx] = j;
                idx++;
            }
        }
        return result;
    }



    public static void main(String[] args) {
        int[] ints = new Problem667().constructArray(4, 2);
        String string = Arrays.toString(ints);
        System.out.println(string);
    }

}
