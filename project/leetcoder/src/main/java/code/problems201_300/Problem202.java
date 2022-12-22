package code.problems201_300;

import java.util.HashSet;
import java.util.Set;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/12/19 21:06
 */
public class Problem202 {
	public boolean isHappy(int n) {
		Set<Integer> result = new HashSet<>();
		while (n != 1 && !result.contains(n)) {
			result.add(n);
			int next = 0;
			while (n > 0) {
				int d = n % 10;
				next += d * d;
				n /= 10;
			}
			n = next;
		}
		return n == 1;
	}


	public static void main(String[] args) {
		System.out.println(new Problem202().isHappy(12));
	}
}
