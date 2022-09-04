package code.problems1501_1600;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/9/4 11:34
 */
public class Problem1582 {


	public int numSpecial(int[][] mat) {
		int[] rolSum = new int[mat.length];
		int[] colSum = new int[mat[0].length];
		int result = 0;
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				rolSum[i] += mat[i][j];
				colSum[j] += mat[i][j];
			}
		}
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				if (mat[i][j] == 1 && rolSum[i] == 1 && colSum[j] == 1) {
					result++;
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		int i = new Problem1582().numSpecial(new int[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}});
		System.out.println(i);
	}
}
