package code.problems1301_1400;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/8/1 10:36
 */
public class Problem1374 {
	public String generateTheString(int n) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < n; i++) {
			if (i == n - 1 && i % 2 == 1) {
				stringBuilder.append("a");
			} else {
				stringBuilder.append("s");
			}
		}
		return stringBuilder.toString();
	}

	public static void main(String[] args) {
		System.out.println(new Problem1374().generateTheString(7));
	}
}
