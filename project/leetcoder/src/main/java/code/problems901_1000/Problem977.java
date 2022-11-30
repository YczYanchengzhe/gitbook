package code.problems901_1000;

import java.util.Arrays;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/11/29 20:23
 */
public class Problem977 {
	/**
	 * 双指针法,指针从数组头尾向中间遍历
	 *
	 * @param nums
	 * @return
	 */
	public int[] sortedSquares(int[] nums) {
		int[] result = new int[nums.length];
		int index = nums.length - 1;
		int i = 0;
		int j = nums.length - 1;
		while (i <= j) {
			if (nums[i] * nums[i] > nums[j] * nums[j]) {
				result[index--] = nums[i] * nums[i];
				i++;
			} else {
				result[index--] = nums[j] * nums[j];
				j--;
			}
		}
		return result;
	}

	public static void main(String[] args) {
		int[] ints = new Problem977().sortedSquares(new int[]{-5, -3, -2, -1});
		String string = Arrays.toString(ints);
		System.out.println(string);
	}
}
