package code.problems101_200;

/**
 * @author chengzhe yan
 * @description
 * @date 2023/1/4 17:28
 */
public class Problem151 {

	/**
	 * 给定一个字符串，逐个翻转字符串中的每个单词。
	 *
	 * @param s
	 * @return
	 */
	public String reverseWords(String s) {
		StringBuilder result = new StringBuilder();
		int endIndex;
		int startIndex;
		for (int i = s.length() - 1; i >= 0; i--) {
			if (s.charAt(i) != ' ') {
				endIndex = i;
				while (i >= 0 && s.charAt(i) != ' ') {
					i--;
				}
				startIndex = i + 1;
				result.append(s, startIndex, endIndex + 1).append(" ");
			}
		}
		return result.substring(0, result.length() - 1);
	}

	public static void main(String[] args) {
		String the_sky_is_blue = new Problem151().reverseWords("  hello world  ");
		System.out.println(the_sky_is_blue);
	}
}
