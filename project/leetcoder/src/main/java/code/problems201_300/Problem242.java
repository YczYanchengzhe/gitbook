package code.problems201_300;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/12/7 22:35
 */
public class Problem242 {


	public boolean isAnagram2(String s, String t) {
		int[] arr = new int[26];
		for (int i = 0; i < s.length(); i++) {
			arr[s.charAt(i) - 'a']++;
		}
		for (int i = 0; i < t.length(); i++) {
			arr[t.charAt(i) - 'a']--;
		}
		for (int count : arr) {
			if (count != 0) {
				return false;
			}
		}
		return true;
	}

	public boolean isAnagram(String s, String t) {
		Map<Character, Integer> map = new HashMap();
		for (int i = 0; i < s.length(); i++) {
			if (map.containsKey(s.charAt(i))) {
				map.put(s.charAt(i), map.get(s.charAt(i)) + 1);
			} else {
				map.put(s.charAt(i), 1);
			}
		}
		for (int i = 0; i < t.length(); i++) {
			if (map.containsKey(s.charAt(i))) {
				if (map.get(s.charAt(i)) != 0) {
					map.put(s.charAt(i), map.get(s.charAt(i)) - 1);
				} else {
					map.remove(s.charAt(i));
				}
			} else {
				return false;
			}
		}
		return map.isEmpty();
	}
}
