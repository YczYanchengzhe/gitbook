package code.problems1101_1200;

import java.util.Arrays;
import java.util.Random;
import java.util.TreeMap;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/7/26 19:51
 */
public class Problem_1206 {

}

class Skiplist {

	static final int MAX_LEVEL = 32;
	static final double P_FACTOR = 0.25;
	private SkipListNode head;
	private int level;
	private Random random;

	public Skiplist() {
		this.head = new SkipListNode(-1, MAX_LEVEL);
		this.level = 0;
		this.random = new Random();
	}

	public boolean search(int target) {
		SkipListNode curr = this.head;
		for (int i = level - 1; i >= 0; i--) {
			// 找到最接近 target 的元素
			while (curr.forward[i] != null && curr.forward[i].val < target) {
				curr = curr.forward[i];
			}
		}
		curr = curr.forward[0];
		if (curr != null && curr.val == target) {
			return true;
		}
		return false;
	}

	public void add(int num) {
		SkipListNode[] update = new SkipListNode[MAX_LEVEL];
		Arrays.fill(update, head);
		SkipListNode curr = this.head;
		for (int i = level - 1; i >= 0; i--) {
			// 找到 最接近 num 的元素
			while (curr.forward[i] != null && curr.forward[i].val < num) {
				curr = curr.forward[i];
			}
			update[i] = curr;
		}
		int lv = randomLevel();
		level = Math.max(level, lv);
		SkipListNode newNode = new SkipListNode(num, lv);
		for (int i = 0; i < lv; i++) {
			newNode.forward[i] = update[i].forward[i];
			update[i].forward[i] = newNode;
		}
	}

	private int randomLevel() {
		int lv = 1;
		while (random.nextDouble() < P_FACTOR && lv < MAX_LEVEL) {
			lv++;
		}
		return lv;
	}

	public boolean erase(int num) {
		SkipListNode[] update = new SkipListNode[MAX_LEVEL];
		SkipListNode curr = this.head;
		for (int i = level - 1; i >= 0; i--) {
			while (curr.forward[i] != null && curr.forward[i].val < num) {
				curr = curr.forward[i];
			}
			update[i] = curr;
		}
		curr = curr.forward[0];
		if (curr == null || curr.val != num) {
			return false;
		}
		for (int i = 0; i < level; i++) {
			if (update[i].forward[i] != curr) {
				break;
			}
			update[i].forward[i] = curr.forward[i];
		}
		while (level > 1 && head.forward[level - 1] == null) {
			level--;
		}
		return true;
	}
}

class SkipListNode {
	int val;
	SkipListNode[] forward;

	public SkipListNode(int val, int maxLevel) {
		this.val = val;
		this.forward = new SkipListNode[maxLevel];
	}
}



