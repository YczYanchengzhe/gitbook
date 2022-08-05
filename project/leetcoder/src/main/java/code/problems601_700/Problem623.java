package code.problems601_700;

import code.common.TreeNode;
import jdk.nashorn.internal.runtime.PrototypeObject;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/8/5 17:40
 */
public class Problem623 {

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		TreeNode treeNode = new Problem623().addOneRow(root, 5, 1);
		System.out.println(treeNode);

	}

	public TreeNode addOneRow(TreeNode root, int val, int depth) {
		if (root == null) {
			return null;
		}
		if (depth == 1) {
			return new TreeNode(val, root, null);
		}
		if (depth == 2) {
			root.left = new TreeNode(val, root.left, null);
			root.right = new TreeNode(val, null, root.right);
		} else {
			root.left = addOneRow(root.left, val, depth - 1);
			root.right = addOneRow(root.right, val, depth - 1);
		}
		return root;
	}


	public TreeNode addOneRow2(TreeNode root, int val, int depth) {
		if (depth == 1) {
			TreeNode treeNode = new TreeNode(val);
			treeNode.left = root;
			return treeNode;
		}
		Queue<Object> next = new ArrayDeque<>();
		next.add(root);
		int currDepth = 1;
		next.add(currDepth++);
		while (!next.isEmpty() && currDepth < depth) {
			Object poll = next.poll();
			if (poll instanceof TreeNode) {
				TreeNode node = (TreeNode) poll;
				if (node.left != null) {
					next.add(node.left);
				}
				if (node.right != null) {
					next.add(node.right);
				}
			} else {
				next.add(currDepth++);
			}
		}
		for (Object o : next) {
			if (o instanceof TreeNode) {
				TreeNode node = (TreeNode) o;
				TreeNode newNode1 = new TreeNode(val);
				newNode1.left = node.left;
				TreeNode newNode2 = new TreeNode(val);
				newNode2.right = node.right;
				node.left = newNode1;
				node.right = newNode2;
			}
		}
		return root;
	}
}
