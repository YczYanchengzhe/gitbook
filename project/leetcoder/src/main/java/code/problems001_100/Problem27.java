package code.problems001_100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/11/24 22:44
 */
public class Problem27 {

	public int removeElement(int[] nums, int val) {
		int length = nums.length;
		int i = 0;
		int j = 0;
		while (i < length) {
			if (nums[i] != val) {
				nums[j] = nums[i];
				j++;
			}
			i++;
		}
		return j;
	}

	public static void main(String[] args) {
		Problem27 problem27 = new Problem27();
		int[] ints = {0, 1, 2, 2, 3, 0, 4, 2};
		int i = problem27.removeElement(ints, 2);
		System.out.println(i);
		System.out.println(Arrays.toString(ints));
	}
}
