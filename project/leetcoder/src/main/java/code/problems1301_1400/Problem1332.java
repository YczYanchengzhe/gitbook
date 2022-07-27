package code.problems1301_1400;

/**
 * @author chengzhe yan
 * @description : 删除回文子序列
 * @date 2022/1/22 7:28 下午
 */
public class Problem1332 {

	/**
	 * 由于相同的字符组成的子序列一定是回文子序列，因此最多只需要删除 22 次即可删除所有的字符
	 *
	 * @param s
	 * @return
	 */
	public int removePalindromeSub(String s) {
		int n = s.length();
		for (int i = 0; i < n / 2; i++) {
			if (s.charAt(i) != s.charAt(n - 1 - i)) {
				return 2;
			}
		}
		return 1;
	}

}
