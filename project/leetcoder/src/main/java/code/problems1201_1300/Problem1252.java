package code.problems1201_1300;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/7/12 23:01
 */
public class Problem1252 {


	public int oddCells(int m, int n, int[][] indices) {
		int[][] arr = new int[m][n];
		for (int[] index : indices) {
			for (int c = 0; c < n; c++) {
				arr[index[0]][c]++;
			}
			for (int r = 0; r < m; r++) {
				arr[r][index[1]]++;
			}
		}
		int count = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if ((arr[i][j] & 1) != 0) {
					count++;
				}
			}
		}
		return count;
	}

	public int oddCells2(int m, int n, int[][] indices) {
		int[] row = new int[m];
		int[] col = new int[n];

		for (int[] index : indices) {
			row[index[0]]++;
			col[index[1]]++;
		}
		int count = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (((row[i] + col[j]) & 1) != 0) {
					count++;
				}
			}
		}
		return count;
	}

	public int oddCells3(int m, int n, int[][] indices) {
		int[] row = new int[m];
		int[] col = new int[n];
		for (int[] index : indices) {
			row[index[0]]++;
			col[index[1]]++;
		}
		int oddx = 0, oddy = 0;
		for (int i = 0; i < m; i++) {
			if ((row[i] & 1) != 0) {
				oddx++;
			}
		}
		for (int i = 0; i < n; i++) {
			if ((col[i] & 1) != 0) {
				oddy++;
			}
		}
		return oddx * (n - oddy) + (m - oddx) * oddy;
	}
}
