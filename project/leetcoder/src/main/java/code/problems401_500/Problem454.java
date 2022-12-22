package code.problems401_500;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/12/22 19:44
 */
public class Problem454 {

	public static void main(String[] args) {
		int i = new Problem454().fourSumCount(new int[]{1, 2}, new int[]{-2, -1}, new int[]{-1, 2}, new int[]{0, 2});
		i = new Problem454().fourSumCount(new int[]{0}, new int[]{-0}, new int[]{0}, new int[]{0});
		i = new Problem454().fourSumCount(new int[]{-1, -1}, new int[]{-1, 1}, new int[]{-1, 1}, new int[]{1, -1});
		System.out.println(i);
	}

	/**
	 * 给你四个整数数组 nums1、nums2、nums3 和 nums4 ，数组长度都是 n ，请你计算有多少个元组 (i, j, k, l) 能满足：
	 *
	 * @param nums1
	 * @param nums2
	 * @param nums3
	 * @param nums4
	 * @return
	 */
	public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
		int count = 0;
		Map<Integer, Integer> sum = new HashMap<>();
		for (int j : nums1) {
			for (int k : nums2) {
				sum.put(j + k, sum.getOrDefault(j + k, 0) + 1);
			}
		}
		for (int m : nums3) {
			for (int n : nums4) {
				if (sum.containsKey(-m - n)) {
					count += sum.get(-m - n);
				}
			}
		}
		return count;
	}
}
