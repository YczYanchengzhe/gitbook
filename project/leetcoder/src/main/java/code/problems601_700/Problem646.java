package code.problems601_700;

import java.security.Key;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/9/3 15:31
 */
public class Problem646 {

	public int findLongestChain(int[][] pairs) {
		int curr = Integer.MIN_VALUE, res = 0;
		Arrays.sort(pairs, Comparator.comparingInt(a -> a[1]));
		for (int[] p : pairs) {
			if (curr < p[0]) {
				curr = p[1];
				res++;
			}
		}
		return res;
	}
}
