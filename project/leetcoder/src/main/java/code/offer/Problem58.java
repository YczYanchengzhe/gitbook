package code.offer;

/**
 * @author chengzhe yan
 * @description
 * @date 2023/2/9 23:15
 */
public class Problem58 {

	public String reverseLeftWords(String s, int n) {
		int length = s.length();
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < length; i++) {
			result.append(s.charAt(i + n >= length ? i + n - length : i + n));
		}
		return result.toString();
	}

	public static void main(String[] args) {
		System.out.println(new Problem58().reverseLeftWords("", 2));
	}
}
