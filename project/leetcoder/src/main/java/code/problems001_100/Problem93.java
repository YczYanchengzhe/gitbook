package code.problems001_100;

import java.util.ArrayList;
import java.util.List;

/**
 * className : problem93
 * description : 复原ip地址
 * 1.0.0.1 - 239.255.255.254
 *
 * @author : yanchengzhe
 * date : 2020/8/9 19:30
 **/
public class Problem93 {
	/**
	 * 存储一个有几个网段
	 */
	private int count = 4;

	/**
	 * 记录每一个网段
	 */
	private int[] arr = new int[count];

	private List<String> result = new ArrayList<>();

	public List<String> restoreIpAddresses2(String s) {
		//dfs 深度优先遍历
		dfs(s, 0, 0);
		return result;
	}

	/**
	 * dfs
	 *
	 * @param s            需要遍历的数据
	 * @param currentCount 当前是第一个网段
	 * @param current      当前遍历的下标
	 */
	private void dfs(String s, int currentCount, int current) {
		//达到最后一个网段
		if (currentCount == count) {
			//此时已经有完整的ip - 如果此时到了尾 , 可以进行设置
			if (current == s.length()) {
				StringBuilder stringBuilder = new StringBuilder();
				for (int i = 0; i < count; i++) {
					if (i == count - 1) {
						stringBuilder.append(arr[i]);
					} else {
						stringBuilder.append(arr[i]).append(".");
					}
				}
				result.add(stringBuilder.toString());
			}
			return;
		}
		//判断是否还没达到count 个网段 字符串就便利完成了 ,提前返回
		if (current == s.length()) {
			return;
		}
		//不能有连续0
		if (s.charAt(current) == '0') {
			arr[currentCount] = 0;
			dfs(s, currentCount + 1, current + 1);
		}

		//正常情况 , 进行遍历
		int number = 0;
		for (int i = current; i < s.length(); i++) {
			number = number * 10 + (s.charAt(i) - '0');
			//不需要等于0 0 在上面已经特殊处理了
			if (number > 0x00 && number <= 0xFF) {
				arr[currentCount] = number;
				dfs(s, currentCount + 1, i + 1);
			} else {
				break;
			}
		}

	}

	/**
	 * @param s ip : 127.0.0.1  127001
	 * @return
	 */
	public List<String> restoreIpAddresses(String s) {
		List<String> resultList = new ArrayList<>();
		if (s == null || s.length() == 0) {
			return resultList;
		}
		//约束 :
		//1. 四位数字
		//2. 第一位 1-239 第二位 0 -255 第三位 0 -255 第四位 1 -254
		//检测第一位 , 检测第2,3 位 , 检测第四位
		//取一个 , 检测第一位 , 再去一个 检测第二位 , 在取一个,检测第三位 , 剩下的检测第四位
		//每一个都可以 ++ , 直到不合理
		//第一位最多四个
		String s1, s2, s3, s4;
		for (int i = 1; i < 4; i++) {
			//取出第一位的串
			s1 = check(s, 0, i);
			if (s1 == null) {
				continue;
			}
			int secondEnd = i + 4;
			for (int a = i + 1; a < secondEnd; a++) {
				s2 = check(s, i, a);
				if (s2 == null) {
					continue;
				}
				int threadEnd = a + 4;
				for (int b = a + 1; b < threadEnd; b++) {
					s3 = check(s, a, b);
					if (s3 == null) {
						continue;
					}
					s4 = check(s, b, s.length());
					if (s4 == null) {
						continue;
					}
					//取到四个串了, 且都符合规范
					String result = String.join(".", s1, s2, s3, s4);
					resultList.add(result);
				}
			}
		}
		return resultList;
	}

	private String check(String s, int start, int end) {
		if (s.length() < end) {
			return null;
		}
		String tmp = s.substring(start, end);
		if (tmp.length() < 1 || (tmp.length() > 1 && tmp.startsWith("0"))) {
			return null;
		}
		int i = -1;
		try {
			i = Integer.valueOf(tmp);
		} catch (NumberFormatException e) {
			return null;
		}
		return i >= 0 && i <= 255 ? tmp : null;
	}


	public static void main(String[] args) {
		Problem93 problem93 = new Problem93();
		List<String> resultList = problem93.restoreIpAddresses2("0000");
		System.out.println(resultList);
	}
}
