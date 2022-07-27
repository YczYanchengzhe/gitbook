package code.problems201_300;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengzhe yan
 * @description : 回溯算法
 * @date 2021/10/16 11:47 上午
 */
public class Problem282 {

	int n;
	String num;
	int target;
	List<String> ans;


	public List<String> addOperators(String num, int target) {
		this.n = num.length();
		this.num = num;
		this.target = target;
		this.ans = new ArrayList<>();
		StringBuffer expr = new StringBuffer();
		backtrack(expr, 0, 0, 0);
		return ans;
	}

	public void backtrack(StringBuffer expr, int i, long res, long mul) {
		if (i == n) {
			if (res == target) {
				ans.add(expr.toString());
			}
			return;
		}
		// 提前拼接一个占位符
		int sign = expr.length();
		if (i > 0) {
			expr.append(0);
		}
		// 考虑处理的中间过程可能超过 int
		long val = 0;
		// 以 105 ==> 5 为例 ; 可以允许 10 - 5
		for (int j = i; j < n && (j == i || num.charAt(i) != '0'); j++) {
			val = val * 10 + num.charAt(j) - '0';
			expr.append(num.charAt(j));
			if (i == 0) {
				backtrack(expr, j + 1, val, val);
			} else {
				expr.setCharAt(sign, '+');
				backtrack(expr, j + 1, res + val, val);
				expr.setCharAt(sign, '-');
				backtrack(expr, j + 1, res - val, -val);
				expr.setCharAt(sign, '*');
				backtrack(expr, j + 1, res - mul + mul * val, mul * val);
			}
		}
		expr.setLength(sign);
	}

	public static void main(String[] args) {
		Problem282 problem282 = new Problem282();
		List<String> list = problem282.addOperators("105", 105);
		System.out.println(list);
	}


}
