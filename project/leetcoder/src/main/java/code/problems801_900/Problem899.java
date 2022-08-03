package code.problems801_900;

import java.util.Arrays;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/8/3 16:54
 */
public class Problem899 {
	public static void main(String[] args) {
		String s = new Problem899().orderlyQueue("cba", 1);
		System.out.println(s);
	}

	public String orderlyQueue(String s, int k) {
		if (k == 1) {
			String result = s;
			StringBuilder stringBuilder = new StringBuilder(s);
			for (int i = 1; i < s.length(); i++) {
				char tmp = stringBuilder.charAt(0);
				stringBuilder.deleteCharAt(0);
				stringBuilder.append(tmp);
				if (stringBuilder.toString().compareTo(result) < 0) {
					result = stringBuilder.toString();
				}
			}
			return result;
		}

		if (k > 1) {
			char[] arr = s.toCharArray();
			Arrays.sort(arr);
			return new String(arr);
		}
		return s;
	}

}
