package code.problems901_1000;


import java.util.Arrays;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/1/4 7:57 上午
 */
public class Problem913 {

	private static final int MOUSE_WIN = 1;
	private static final int CAT_WIN = 2;
	private static final int DRAW = 0;

	int n;
	int[][] graph;
	/**
	 * dp[mouse][cat][turns] : 老鼠位于节点 mouse , 猫位于 cat ,游戏进行的轮次 turns
	 * dp[1][2][0] 初始结果 : 因为初始状态老鼠在节点 ,猫节点 2 ,
	 * dp[0][cat][turns] = 1  ,  cat != mouse: 老鼠获胜
	 * cat == mouse  , dp[mouse][cat][turns]   : 猫获胜
	 * turns >= 2n : 平局
	 */
	int[][][] dp;

	public int catMouseGame(int[][] graph) {
		init(graph);
		// 默认老鼠从 1,猫从 2 轮次为0
		return getResult(1, 2, 0);
	}

	private void init(int[][] graph) {
		this.n = graph.length;
		this.graph = graph;
		this.dp = new int[n][n][n * 2];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Arrays.fill(dp[i][j], -1);
			}

		}
	}

	private int getResult(int mouse, int cat, int turns) {
		// 2n 轮次之后必定是平局
		if (turns == n * 2) {
			return DRAW;
		}
		if (dp[mouse][cat][turns] < 0) {
			if (mouse == 0) {
				// 老鼠走到洞中,老鼠赢
				dp[mouse][cat][turns] = MOUSE_WIN;
			} else if (cat == mouse) {
				// 猫抓到老鼠
				dp[mouse][cat][turns] = CAT_WIN;
			} else {
				// 继续移动,设置状态数组
				getNextResult(mouse, cat, turns);
			}
		}
		// 根据状态数组返回结果
		return dp[mouse][cat][turns];
	}

	/**
	 * 更新状态数组
	 * @param mouse
	 * @param cat
	 * @param turns
	 */
	private void getNextResult(int mouse, int cat, int turns) {
		// 因为老鼠先移动,猫后移动,所以根据奇偶性来决定移动人选
		int curMove = turns % 2 == 0 ? mouse : cat;
		int defaultResult = curMove == mouse ? CAT_WIN : MOUSE_WIN;
		int result = defaultResult;
		// 遍历图的下一步节点
		int[] nextNodes = graph[curMove];
		for (int next : nextNodes) {
			// 猫不能走 0
			if (curMove == cat && next == 0) {
				continue;
			}
			// 获取老鼠的下一个位置
			int nextMouse = curMove == mouse ? next : mouse;
			// 获取猫的下一个位置
			int nextCat = curMove == cat ? next : cat;
			// 轮次+1 , 计算下一个状态结果
			int nextResult = getResult(nextMouse, nextCat, turns + 1);
			if (nextResult != defaultResult) {
				// 如果下一步的结果和当前结果不一样,更新结果
				result = nextResult;
				// 没有达到平局场次,设置 dp 数组
				if (result != DRAW) {
					break;
				}
			}
		}
		dp[mouse][cat][turns] = result;
	}
}
