package code.problems901_1000;

import java.util.Arrays;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/7/30 09:44
 */
public class Problem_952 {
	public int largestComponentSize(int[] nums) {
		int m = Arrays.stream(nums).max().getAsInt();
		UnionFind unionFind = new UnionFind(m + 1);
		for (int num : nums) {
			// 计算 [2,sqrt(num)] 内每个正整数 i,如果 i 是 num 因数 ,那么 num , i , num/i 属于同一组件.
			for (int i = 2; i * i <= num; i++) {
				if (num % i == 0) {
					unionFind.union(num, i);
					unionFind.union(num, num / i);
				}
			}
		}
		int[] counts = new int[m + 1];
		int ans = 0;
		for (int num : nums) {
			int root = unionFind.find(num);
			// 所有有因数关系的都会++
			counts[root]++;
			ans = Math.max(ans, counts[root]);

		}
		return ans;
	}

	/**
	 * 创建一个并查集
	 */
	class UnionFind {
		int[] parent;
		int[] rank;

		public UnionFind(int n) {
			parent = new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = i;
			}
			rank = new int[n];
		}

		/**
		 * 合并
		 *
		 * @param x
		 * @param y
		 */
		public void union(int x, int y) {
			int rootx = find(x);
			int rooty = find(y);
			// 合并时比较两个根节点，把rank(秩)较小者往较大者上合并。合并时候 ,挂在根节点上
			if (rootx != rooty) {
				if (rank[rootx] > rank[rooty]) {
					parent[rooty] = rootx;
				} else if (rank[rootx] < rank[rooty]) {
					parent[rootx] = rooty;
				} else {
					// 一样的时候 都可以 ,秩要++
					parent[rooty] = rootx;
					rank[rootx]++;
				}
			}
		}

		/**
		 * 递归寻找根节点
		 *
		 * @param x
		 * @return
		 */
		private int find(int x) {
			if (parent[x] != x) {
				parent[x] = find(parent[x]);
			}
			return parent[x];
		}
	}
}
