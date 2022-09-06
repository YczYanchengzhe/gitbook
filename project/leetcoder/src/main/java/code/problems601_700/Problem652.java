package code.problems601_700;

import code.common.Pair;
import code.common.TreeNode;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/9/5 19:51
 */
public class Problem652 {

	Map<String, TreeNode> seen = new HashMap<>();
	Set<TreeNode> node = new HashSet<>();

	public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
		dfs(root);
		return new ArrayList<>(node);
	}

	private String dfs(TreeNode root) {
		if (root == null) {
			return "";
		}
		String s = root.val + "(" + dfs(root.left) + ")(" + dfs(root.right) + ")";
		if (seen.containsKey(s)) {
			node.add(seen.get(s));
		} else {
			seen.put(s, root);
		}
		return s;
	}

}
