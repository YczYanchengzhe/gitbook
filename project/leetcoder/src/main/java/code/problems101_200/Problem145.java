package code.problems101_200;

import code.common.TreeNode;

import java.util.*;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/12/6 22:11
 */
public class Problem145 {
	/**
	 * 后序遍历
	 *
	 * @param root
	 * @return
	 */
	public List<Integer> postorderTraversal(TreeNode root) {
		List<Integer> result = new ArrayList<>();
		// 左 , 右 ,中
		Deque<TreeNode> stack = new ArrayDeque<>();
		TreeNode prev = null;
		while (root != null || !stack.isEmpty()) {
			while (root != null) {
				stack.push(root);
				root = root.left;
			}
			root = stack.pop();
			// 在右不为空的时候 , 中的前面永远是右 , 所以判断当前的前一个是不是右,决定左是否遍历过了
			if (root.right == null || root.right == prev) {
				result.add(root.val);
				prev = root;
				root = null;
			}else {
				stack.push(root);
				root = root.right;
			}
		}
		return result;
	}
}
