package code.problems301_400;

import java.util.HashSet;
import java.util.Set;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/12/19 20:36
 */
public class Problem349 {

	public int[] intersection(int[] nums1, int[] nums2) {
		// 遍历第一个数组,生成是 set ,遍历第二个,判断是否存在
		Set<Integer> set = new HashSet<>();
		for (int i : nums1) {
			set.add(i);
		}
		Set<Integer> arr = new HashSet<>();
		for (int i : nums2) {
			if (set.contains(i)) {
				arr.add(i);
			}
		}
		return arr.stream().mapToInt(x -> x).toArray();
	}

	public static void main(String[] args) {

	}
}
