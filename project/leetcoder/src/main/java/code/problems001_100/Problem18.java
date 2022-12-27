package code.problems001_100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/12/27 17:29
 */
public class Problem18 {

	public static void main(String[] args) {
//		List<List<Integer>> lists = new Problem18().fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0);
		List<List<Integer>> lists = new Problem18().fourSum(new int[]{1000000000,1000000000,1000000000,1000000000}, -294967296);
		System.out.println(lists);
	}

	/**
	 * 给你一个由 n 个整数组成的数组 nums ，
	 * 和一个目标值 target 。
	 * 请你找出并返回满足下述全部条件且不重复的四元组
	 * [nums[a], nums[b], nums[c], nums[d]]
	 * （若两个四元组元素一一对应，则认为两个四元组重复）
	 *
	 * @param nums
	 * @param target
	 * @return
	 */
	public List<List<Integer>> fourSum(int[] nums, int target) {
		List<List<Integer>> result = new ArrayList<>();
		Arrays.sort(nums);
		for (int i = 0; i < nums.length; i++) {
			if (i > 0 && nums[i] == nums[i - 1]) {
				continue;
			}
			for (int j = i + 1; j < nums.length; j++) {
				if (j > i + 1 && nums[j] == nums[j - 1]) {
					continue;
				}
				int left = j + 1;
				int right = nums.length - 1;
				while (left < right) {
					long sum = (long)nums[i] + nums[j] + nums[left] + nums[right];
					if (sum > target) {
						right--;
					} else if (sum < target) {
						left++;
					} else {
						if (left + 1 < right && nums[left] == nums[left + 1]) {
							left++;
						} else if (left + 1 < right && nums[right] == nums[right - 1]) {
							right--;
						} else {
							List<Integer> tmp = new ArrayList<>();
							tmp.add(nums[i]);
							tmp.add(nums[j]);
							tmp.add(nums[left]);
							tmp.add(nums[right]);
							result.add(tmp);
							left++;
							right--;
						}
					}
				}
			}
		}
		return result;
	}
}
