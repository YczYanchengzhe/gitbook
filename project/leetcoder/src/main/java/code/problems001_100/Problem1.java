package code.problems001_100;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/12/19 21:24
 */
public class Problem1 {

	public static void main(String[] args) {
		int[] ints = new Problem1().twoSum(new int[]{3, 3}, 6);
		System.out.println(Arrays.toString(ints));
	}

	/**
	 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
	 *
	 * @param nums
	 * @param target
	 * @return
	 */
	public int[] twoSum(int[] nums, int target) {
		int[] result = new int[2];
		Map<Integer, Integer> save = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			if (save.containsKey(target - nums[i])) {
				result[0] = i;
				result[1] = save.get(target - nums[i]);
				return result;
			} else {
				save.put(nums[i], i);
			}
		}
		return null;
	}
}
