package code.problems901_1000;

import code.common.TreeNode;
import lombok.ToString;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/7/25 22:14
 */
public class Problem_919 {

}

class CBTInserter {

	Queue<TreeNode> candidate;
	TreeNode root;

	public CBTInserter(TreeNode root) {
		this.candidate = new ArrayDeque<>();
		this.root = root;

		Queue<TreeNode> queue = new ArrayDeque<>();
		queue.offer(root);
		// 初始化一个队列存储可以添加孩子的所有节点
		while (!queue.isEmpty()) {
			TreeNode node = queue.poll();
			if (node.left != null) {
				queue.offer(node.left);
			}
			if (node.right != null) {
				queue.offer(node.right);
			}
			if (!(node.left != null && node.right != null)) {
				candidate.offer(node);
			}
		}
	}

	public int insert(int val) {
		TreeNode child = new TreeNode(val);
		TreeNode node = candidate.peek();
		int ret = node.val;
		if (node.left == null) {
			node.left = child;
		} else {
			node.right = child;
			candidate.poll();
		}
		candidate.offer(child);
		return ret;
	}

	public TreeNode get_root() {
		return root;
	}
}
