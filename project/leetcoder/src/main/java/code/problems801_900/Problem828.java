package code.problems801_900;

import java.util.*;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/9/6 16:29
 */
public class Problem828 {

// 	我们定义了一个函数 countUniqueChars(s) 来统计字符串 s 中的唯一字符，并返回唯一字符的个数。
//
//	例如：s = "LEETCODE" ，则其中 "L", "T","C","O","D" 都是唯一字符，因为它们只出现一次，所以 countUniqueChars(s) = 5 。
//
//	本题将会给你一个字符串 s ，我们需要返回 countUniqueChars(t) 的总和，其中 t 是 s 的子字符串。输入用例保证返回值为32 位整数。
//
//	注意，某些子字符串可能是重复的，但你统计时也必须算上这些重复的子字符串（也就是说，你必须统计 s 的所有子字符串中的唯一字符）。

	Map<String, Integer> stringToSize = new HashMap<>();

	public int uniqueLetterString(String s) {
		int result = 0;
		for (int i = 0; i < s.length(); i++) {
			for (int j = i + 1; j <= s.length(); j++) {
				String substring = s.substring(i, j);
				result += countUniqueChars(substring);
			}
		}
		return result;
	}

	public int countUniqueChars(String s) {
		if (!stringToSize.containsKey(s)) {
			Map<Integer, Integer> charSet = new HashMap<>();
			for (char c : s.toCharArray()) {
				charSet.merge((int) c, 1, Integer::sum);
			}
			int count = (int) charSet.entrySet().stream().filter(entry -> entry.getValue() == 1).count();
			stringToSize.put(s, count);
			return count;
		} else {
			return stringToSize.get(s);
		}
	}

	public int uniqueLetterString_3(String s) {

		Map<Character, List<Integer>> index = new HashMap<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (!index.containsKey(c)) {
				index.put(c, new ArrayList<>());
				index.get(c).add(-1);
			}
			index.get(c).add(i);
		}
		int res = 0;
		for (Map.Entry<Character, List<Integer>> entry : index.entrySet()) {
			List<Integer> arr = entry.getValue();
			// 增加字符串的尾
			arr.add(s.length());
			for (int i = 1; i < arr.size() - 1; i++) {
				// 该字符 上一次出现位置到这一次出现位置之间的字符数 * 这一次出现位置到下一次出现位置之间的字符数.
				res += (arr.get(i) - arr.get(i - 1)) * (arr.get(i + 1) - arr.get(i));
			}
		}
		return res;
	}

	public int uniqueLetterString_2(String s) {
		// 记录每个字符在列表中出现的下标集合
		Map<Character, List<Integer>> charToIndexs = new HashMap<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (!charToIndexs.containsKey(c)) {
				charToIndexs.put(c, new ArrayList<>());
				// 补充空位 , 第 0 次出现位置 -1,为了保证后面计算时候没有 0
				charToIndexs.get(c).add(-1);
			}
			charToIndexs.get(c).add(i);
		}
		int result = 0;
		for (Map.Entry<Character, List<Integer>> characterListEntry : charToIndexs.entrySet()) {
			List<Integer> value = characterListEntry.getValue();
			// 增加字符串的尾
			value.add(s.length());
			for (int i = 1; i < value.size() - 1; i++) {
				// 该字符 上一次出现位置到这一次出现位置之间的字符数 * 这一次出现位置到下一次出现位置之间的字符数.
				result += (value.get(i) - value.get(i - 1)) * (value.get(i + 1) - value.get(i));
			}
		}
		return result;
	}


	public static void main(String[] args) {
		int abc = new Problem828().uniqueLetterString("LEETCODE");
		System.out.println(abc);
	}
}
