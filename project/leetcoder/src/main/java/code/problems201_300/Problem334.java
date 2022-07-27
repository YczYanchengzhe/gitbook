package code.problems201_300;

/**
 * @author chengzhe yan
 * @description : 递增的三元子序列
 * @date 2022/1/12 11:02 下午
 */
public class Problem334 {

	/**
	 * 双指针法
	 *
	 * @param nums
	 * @return
	 */
	public boolean increasingTriplet(int[] nums) {
		int length = nums.length;
		if (length < 3) {
			return false;
		}
		// 左找最小
		int[] left = new int[length];
		left[0] = nums[0];
		for (int i = 1; i < length; i++) {
			left[i] = Math.min(left[i - 1], nums[i]);
		}
		// 右找最大
		int[] right = new int[length];
		right[length - 1] = nums[length - 1];
		for (int i = length - 2; i >= 0; i--) {
			right[i] = Math.max(right[i + 1], nums[i]);
		}
		// 查找符合条件
		for (int i = 1; i < length - 1; i++) {
			if (left[i - 1] < nums[i] && right[i + 1] > nums[i]) {
				return true;
			}
		}
		return false;
	}


	/**
	 * 贪心
	 *
	 * @param nums
	 * @return
	 */
	public boolean increasingTriplet2(int[] nums) {
		int length = nums.length;
		if (length < 3) {
			return false;
		}
		int first = nums[0], second = Integer.MAX_VALUE;
		for (int i = 1; i < length; i++) {
			int tmp = nums[i];
			if (tmp > second) {
				// 出现第三个就说明符合条件
				return true;
			} else if (tmp > first) {
				// 第二个只要比第一个大就行
				second = tmp;
			} else {
				// 第一个是最小的
				first = tmp;
			}
		}
		return false;
	}

}
