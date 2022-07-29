package code.problems501_600;

import java.util.Arrays;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/7/29 16:16
 */
public class Problem_593 {

	public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
		// 以p1 为顶点 ,和其他点连接作为对角线,验证是否可以构成正方形
		// 点相同 ,不能作为对角线
		if (Arrays.equals(p1, p2)) {
			return false;
		}
		// p1,p2 对角线
		if (help(p1, p2, p3, p4)) {
			return true;
		}

		if (Arrays.equals(p1, p3)) {
			return false;
		}
		if (help(p1, p3, p2, p4)) {
			return true;
		}

		if (Arrays.equals(p1, p4)) {
			return false;
		}
		if (help(p1, p4, p2, p3)) {
			return true;
		}
		return false;
	}

	private boolean help(int[] p1, int[] p2, int[] p3, int[] p4) {
		int[] v1 = {p2[0] - p1[0], p2[1] - p1[1]};
		int[] v2 = {p4[0] - p3[0], p4[1] - p3[1]};
		return checkSide(p1, p2, p3, p4) && checkCos(v1, v2) && checkLength(v1, v2);
	}

	/**
	 * 验证边是否相等
	 *
	 * @return
	 */
	private boolean checkSide(int[] p1, int[] p2, int[] p3, int[] p4) {
		return p1[1] + p2[1] == p3[1] + p4[1] && p1[0] + p2[0] == p3[0] + p4[0];
	}

	/**
	 * 验证对角线是否垂直
	 *
	 * @return
	 */
	private boolean checkCos(int[] v1, int[] v2) {
		return v1[0] * v2[0] + v1[1] * v2[1] == 0;
	}

	/**
	 * 验证对角线是否相等
	 *
	 * @return
	 */
	private boolean checkLength(int[] v1, int[] v2) {
		return (v1[0] * v1[0] + v1[1] * v1[1]) == (v2[0] * v2[0] + v2[1] * v2[1]);
	}

}
