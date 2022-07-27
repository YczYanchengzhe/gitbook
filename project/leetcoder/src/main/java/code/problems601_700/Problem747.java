package code.problems601_700;

import java.util.stream.IntStream;

/**
 * @author chengzhe yan
 * @description : 至少是其他数字两倍的最大数
 * @date 2022/1/13 8:45 下午
 */
public class Problem747 {

	public int dominantIndex(int[] nums) {
		if (nums.length == 1) {
			return 0;
		}
		int max = -1;
		int max2 = -1;
		int index = -1;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] > max) {
				max2 = max;
				max = nums[i];
				index = i;
			} else if (nums[i] > max2) {
				max2 = nums[i];
			}
		}

		return max >= max2 * 2 ? index : -1;
	}

}
