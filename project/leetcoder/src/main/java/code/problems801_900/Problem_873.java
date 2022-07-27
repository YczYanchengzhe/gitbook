package code.problems801_900;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/7/9 15:50
 */
public class Problem_873 {

	/**
	 * k , j , i
	 * dp[j][i] = max(dp[k][j],3) , 0<k<j
	 * dp[j][i] = 0 , k<0 or k>j
	 *
	 * @param arr
	 * @return
	 */
	public int lenLongestFibSubseq(int[] arr) {
		// 初始化数据
		Map<Integer, Integer> indices = new HashMap<>();
		for (int i = 0; i < arr.length; i++) {
			indices.put(arr[i], i);
		}
		int n = arr.length;
		int[][] dp = new int[n][n];
		int ans = 0;
		for (int i = 0; i < n; i++) {
			/*
				 j>=0 表示 j 范围
				 arr[j] * 2 > arr[i] : 表示arr[i]的值要小于  arr[j] + arr[k] 的最大值 ,这里 arr[k]最大不会超过 arr[j],所以×2
			 */
			for (int j = i - 1; j >= 0 && arr[j] * 2 > arr[i]; j--) {
				// 取出 k 对应的下标判断是否存在
				int k = indices.getOrDefault(arr[i] - arr[j], -1);
				if (k >= 0) {
					dp[j][i] = Math.max(dp[k][j] + 1, 3);
				}
				ans = Math.max(ans, dp[j][i]);
			}
		}
		return ans;
	}


}
