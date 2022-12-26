package code.problems301_400;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/12/22 20:24
 */
public class Problem383 {

	public static void main(String[] args) {
		boolean b = new Problem383().canConstruct("aa", "aab");
		System.out.println(b);
	}

	/**
	 * 给你两个字符串：ransomNote 和 magazine ，判断 ransomNote 能不能由 magazine 里面的字符构成。
	 * <p>
	 * 如果可以，返回 true ；否则返回 false 。
	 * <p>
	 * magazine 中的每个字符只能在 ransomNote 中使用一次。
	 *
	 * @param ransomNote
	 * @param magazine
	 * @return
	 */
	public boolean canConstruct(String ransomNote, String magazine) {
		Map<Character, Integer> chars = new HashMap<>(26);
		for (char c : magazine.toCharArray()) {
			chars.put(c, chars.getOrDefault(c, 0) + 1);
		}
		for (char c : ransomNote.toCharArray()) {
			if (!chars.containsKey(c)) {
				return false;
			} else {
				Integer count = chars.get(c);
				if (count == 1) {
					chars.remove(c);
				} else {
					chars.put(c, count - 1);
				}
			}
		}
		return true;
	}
}
