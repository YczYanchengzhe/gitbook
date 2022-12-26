package code.problems001_100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/12/22 20:32
 */
public class Problem15 {

	public static void main(String[] args) {
		List<List<Integer>> lists = new Problem15().threeSum(new int[]{0, 0, 0});
		System.out.println(lists);
	}

	/**
	 * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请
	 * <p>
	 * 你返回所有和为 0 且不重复的三元组。
	 *
	 * @param nums
	 * @return
	 */
	public List<List<Integer>> threeSum(int[] nums) {
		Arrays.sort(nums);
		List<List<Integer>> result = new ArrayList<>();
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] > 0) {
				break;
			}
			if (i > 0 && nums[i] == nums[i - 1]) {
				continue;
			}
			int left = i + 1;
			int right = nums.length - 1;
			while (left < right) {
				if (nums[i] + nums[left] + nums[right] > 0) {
					right--;
				} else if (nums[i] + nums[left] + nums[right] < 0) {
					left++;
				} else {
					if (left + 1 < right && nums[left] == nums[left + 1]) {
						left++;
					} else if (left + 1 < right && nums[right] == nums[right - 1]) {
						right--;
					} else {
						List<Integer> number = new ArrayList<>();
						number.add(nums[i]);
						number.add(nums[left]);
						number.add(nums[right]);
						result.add(number);
						left++;
						right--;
					}
				}

			}
		}
		return result;
	}
}
