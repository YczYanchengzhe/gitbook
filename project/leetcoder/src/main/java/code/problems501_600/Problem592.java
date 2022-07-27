package code.problems501_600;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/7/27 09:36
 */
public class Problem592 {

	public String fractionAddition(String expression) {
		// 分子,分母
		long numerator = 0, denominator = 1;
		int index = 0, n = expression.length();
		while (index < n) {
			// 分子
			long numeratorTmp = 0, sign = 1;
			if (expression.charAt(index) == '-' || expression.charAt(index) == '+') {
				sign = expression.charAt(index) == '-' ? -1 : 1;
				index++;
			}
			while (index < n && Character.isDigit(expression.charAt(index))) {
				numeratorTmp = numeratorTmp * 10 + expression.charAt(index) - '0';
				index++;
			}
			numeratorTmp = sign * numeratorTmp;
			index++;
			// 分母
			long denominatorTmp = 0;
			while (index < n && Character.isDigit(expression.charAt(index))) {
				denominatorTmp = denominatorTmp * 10 + expression.charAt(index) - '0';
				index++;
			}
			numerator = numerator * denominatorTmp + numeratorTmp * denominator;
			denominator *= denominatorTmp;
		}
		if (numerator == 0) {
			return "0/1";
		}
		// 约分
		long remainder = gcd(Math.abs(numerator), denominator);
		return numerator / remainder + "/" + denominator / remainder;
	}

	public long gcd(long a, long b) {
		long remainder = a % b;
		while (remainder != 0) {
			a = b;
			b = remainder;
			remainder = a % b;
		}
		return b;
	}

	public static void main(String[] args) {
		String s = new Problem592().fractionAddition("1/2+2/3");
//		String s = new Problem592().fractionAddition("-1/2+1/2+1/3");
		System.out.println(s);
	}

}
