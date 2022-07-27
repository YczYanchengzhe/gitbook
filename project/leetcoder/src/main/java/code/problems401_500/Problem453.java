package code.problems401_500;

import java.util.Arrays;

/**
 * @author chengzhe yan
 * @description
 * @date 2021/10/20 9:14 下午
 */
public class Problem453 {

	/**
	 * 给你一个长度为 n 的整数数组，每次操作将会使 n - 1 个元素增加 1 。返回让数组所有元素相等的最小操作次数。
	 *   注意 题目转换 , 每次n-1 元素增加1 可以转换为最大的 每次-1
	 * @param nums
	 * @return
	 */
	public int minMoves(int[] nums) {
		// 最大值 减一 == 最小值
		int min = Arrays.stream(nums).min().getAsInt();
		int res = 0;
		for (int num : nums) {
			res += num - min;
		}
		return res;
	}
}
