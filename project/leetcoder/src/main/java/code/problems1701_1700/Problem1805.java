package code.problems1701_1700;


import java.util.HashSet;
import java.util.Set;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/12/6 08:40
 */
public class Problem1805 {

	public static void main(String[] args) {
		int a123bc34d8ef34 = new Problem1805().numDifferentIntegers("sh8s0");
		System.out.println(a123bc34d8ef34);
	}

	public int numDifferentIntegers(String word) {
		Set<String> set = new HashSet<>();
		int length = word.length();
		int p1 = 0;
		int p2;
		while (true) {
			while (p1 < length && !Character.isDigit(word.charAt(p1))) {
				p1++;
			}
			if (p1 == length) {
				break;
			}
			p2 = p1;
			while (p2 < length && Character.isDigit(word.charAt(p2))) {
				p2++;
			}
			// 去 0
			while (p2 - p1 > 1 && word.charAt(p1) == '0') {
				p1++;
			}
			String number = word.substring(p1, p2);
			set.add(number);
			p1 = p2;
		}
		return set.size();
	}


//	public int numDifferentIntegers(String word) {
//		Set<String> set = new HashSet<>();
//		int length = word.length();
//		int p1 = 0;
//		int p2;
//		while (true) {
//			while (p1 < length && !Character.isDigit(word.charAt(p1))) {
//				p1++;
//			}
//			if (p1 == length) {
//				break;
//			}
//			p2 = p1;
//			while (p2 < length && Character.isDigit(word.charAt(p2))) {
//				p2++;
//			}
//			while (p2 - p1 > 1 && word.charAt(p1) == '0') {
//				p1++;
//			}
//			String number = word.substring(p1, p2);
//			set.add(number);
//			p1 = p2;
//		}
//		return set.size();
//	}

}
