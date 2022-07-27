package code.problems201_300;

import code.common.TreeNode;

import java.util.*;

/**
 * @author chengzhe yan
 * @description
 * @date 2021/10/17 6:01 下午
 */
public class Problem230 {

	public static void main(String[] args) {
		TreeNode treeNode_1 = new TreeNode(1);
		TreeNode treeNode_2 = new TreeNode(2, treeNode_1, null);
		TreeNode treeNode_4 = new TreeNode(4);
		TreeNode treeNode_3 = new TreeNode(3, treeNode_2, treeNode_4);
		TreeNode treeNode_6 = new TreeNode(6);
		TreeNode treeNode_5 = new TreeNode(5, treeNode_3, treeNode_6);

		int result = new Problem230().kthSmallest2(treeNode_5, 3);
		System.out.println(result);
	}

	/**
	 * 第一种方法
	 * @param root
	 * @param k
	 * @return
	 */
	public int kthSmallest(TreeNode root, int k) {
		Deque<TreeNode> stack = new ArrayDeque<>();
		while (root != null || !stack.isEmpty()) {
			// 找最左
			while (root != null) {
				stack.push(root);
				root = root.left;
			}
			root = stack.pop();
			k--;
			// 处理
			if (k == 0) {
				break;
			}
			// 右
			root = root.right;
		}
		assert root != null;
		return root.val;
	}

	/**
	 * 第二种方法
	 * @param root
	 * @param k
	 * @return
	 */
	public int kthSmallest2(TreeNode root, int k) {
		MyBst myBst = new MyBst(root);
		return myBst.search(k);
	}

	static class MyBst {

		TreeNode root;
		Map<TreeNode, Integer> nodeNum = new HashMap<>();

		public MyBst(TreeNode root) {
			this.root = root;
			countNumNode(root);
		}

		public int search(int k) {
			TreeNode node = root;
			while (node != null) {
				int left = getNodeNumber(node.left);
				if (left < k - 1) {
					// 在右边找
					node = node.right;
					k = k - left + 1;
				} else if (left == k - 1) {
					break;
				} else {
					// 在左边找
					node = node.left;
				}
			}
			return node.val;
		}

		/**
		 * 统计以当前节点为根节点的子节点个数
		 *
		 * @param node
		 */
		private int countNumNode(TreeNode node) {
			if (node == null) {
				return 0;
			}
			nodeNum.put(node, 1 + countNumNode(node.left) + countNumNode(node.right));
			return nodeNum.get(node);
		}

		private int getNodeNumber(TreeNode node) {
			return nodeNum.getOrDefault(node, 0);
		}
	}


	// 第三种方法
	public int kthSmallest3(TreeNode root, int k) {
		return -1;
	}


}
