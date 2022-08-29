package code.problems1401_1500;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/8/4 16:24
 */
public class Problem1403 {

	public static void main(String[] args) {
		List<Integer> list = new Problem1403().minSubsequence(new int[]{3, 5, 6, 3, 2, 4});
		System.out.println(list);

	}

	public List<Integer> minSubsequence(int[] nums) {
		List<Integer> collect = Arrays.stream(nums).boxed().sorted((o1, o2) -> o2 - o1).collect(Collectors.toList());
		int sum = Arrays.stream(nums).sum();
		int find = sum / 2;
		int count = 0;
		List<Integer> result = new ArrayList<>();
		for (int num : collect) {
			count += num;
			result.add(num);
			if (count > find) {
				break;
			}
		}
		return result;
	}
}
