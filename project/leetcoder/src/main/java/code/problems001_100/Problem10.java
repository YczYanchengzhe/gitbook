package code.problems001_100;

/**
 * @author chengzhe yan
 * @description 正则表达式匹配
 * @date 2021/10/5 3:44 下午
 */
public class Problem10 {

    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();

        boolean[][] arr = new boolean[m + 1][n + 1];
        arr[0][0] = true;
        for (int i = 0; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 挨个匹配
                if (p.charAt(j - 1) != '*') {
                    if (match(s, p, i, j)) {
                        arr[i][j] = arr[i - 1][j - 1];
                    }
                } else {
                    arr[i][j] = arr[i][j - 2];
                    if (match(s, p, i, j - 1)) {
                        arr[i][j] = arr[i - 1][j] || arr[i][j - 2];
                    }
                }
            }
        }
        return arr[m][n];
    }

    private boolean match(String s, String p, int i, int j) {
        if (i == 0) {
            return false;
        }
        return p.charAt(j - 1) == '.' || s.charAt(i - 1) == p.charAt(j - 1);
    }
}
