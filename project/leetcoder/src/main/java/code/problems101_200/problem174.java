package code.problems101_200;

import java.util.Arrays;

/**
 * className : problem174
 * description : 地下城游戏
 *
 * @author : yanchengzhe
 * date : 2020/7/12 23:12
 **/
public class problem174 {
    /**
     * 状态转移方程
     * dp[i][j]=max(min(dp[i+1][j],dp[i][j+1])−dungeon(i,j),1) 解出 dp[i][j]
     *
     * @param dungeon
     * @return
     */
    public int calculateMinimumHP(int[][] dungeon) {
        int n = dungeon.length;
        int m = dungeon[0].length;
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        //终点的下和右给1 , 保证到达终点还有一点体力
        dp[n][m - 1] = dp[n - 1][m] = 1;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                int min = Math.min(dp[i][j + 1], dp[i + 1][j]);
                dp[i][j] = Math.max(min - dungeon[i][j], 1);
            }
        }
        return dp[0][0];
    }

    /**
     * 状态转移方程
     * dp[i][j]=max(min(dp[i+1][j],dp[i][j+1])−dungeon(i,j),1) 解出 dp[i][j]
     * 滚动数组 优化
     *
     * @param dungeon
     * @return
     */
    public int calculateMinimumHP2(int[][] dungeon) {
        int n = dungeon.length;
        int m = dungeon[0].length;
        int[] dp = new int[m + 1];
        int[] tmp = new int[m];
        Arrays.fill(dp, Integer.MAX_VALUE);
        Arrays.fill(tmp, Integer.MAX_VALUE);
        //终点的下和右给1 , 保证到达终点还有一点体力
        dp[m] = tmp[m - 1] = 1;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                int min = Math.min(tmp[j], dp[j + 1]);
                dp[j] = Math.max(min - dungeon[i][j], 1);
                tmp[j] = dp[j];
            }
            //需要注意 : 在使用滚动数组时候 , dp数组第一次的最后一个元素给1 ,之后都给 Integer.MAX_VALUE
            dp[m] = Integer.MAX_VALUE;
        }
        return dp[0];
    }
}
