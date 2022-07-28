package code.problems1301_1400;

import java.util.*;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/7/28 17:07
 */
public class Problem_1331 {

	public int[] arrayRankTransform(int[] arr) {
		int[] sortArr = new int[arr.length];
		System.arraycopy(arr, 0, sortArr, 0, arr.length);
		Arrays.sort(sortArr);
		Map<Integer, Integer> ranks = new HashMap<>();
		int[] ans = new int[arr.length];
		for (int a : sortArr) {
			if (!ranks.containsKey(a)) {
				ranks.put(a, ranks.size() + 1);
			}
		}
		for (int i = 0; i < arr.length; i++) {
			ans[i] = ranks.get(arr[i]);
		}
		return ans;
	}

	public static void main(String[] args) {
		int[] ints = new Problem_1331().arrayRankTransform(new int[]{40, 10, 20, 30});
//		int[] ints = new Problem_1331().arrayRankTransform(new int[]{100,100,100});
		Arrays.stream(ints).forEach(System.out::println);
	}
}
