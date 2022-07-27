package code.problems1101_1200;

import java.util.Arrays;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/2/13 4:00 下午
 */
public class Problem1189 {
	public int maxNumberOfBalloons(String text) {
		if (text == null || text.length() == 0) {
			return 0;
		}
		int[] arr = new int[5];
		for (char c : text.toCharArray()) {
			if (c == 'b') {
				arr[0]++;
			} else if (c == 'a') {
				arr[1]++;
			} else if (c == 'l') {
				arr[2]++;
			} else if (c == 'o') {
				arr[3]++;
			} else if (c == 'n') {
				arr[4]++;
			}
		}
		arr[2] /= 2;
		arr[3] /= 2;
		return Arrays.stream(arr).min().getAsInt();
	}

	public static void main(String[] args) {
		Problem1189 problem1189 = new Problem1189();
		System.out.println(problem1189.maxNumberOfBalloons(""));
	}
}
