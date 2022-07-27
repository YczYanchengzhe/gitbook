package code.problems101_200;

import code.common.ListNode;

/**
 * Definition for singly-linked list.
 * class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) {
 * val = x;
 * next = null;
 * }
 * }
 */
public class problem141 {

	/**
	 * 快慢指针
	 *
	 * @param head
	 * @return
	 */
	public boolean hasCycle(ListNode head) {
		if (head == null || head.next == null ) {
			return false;
		}
		ListNode fast = head.next;
		ListNode low = head;
		while (true) {
			if (fast == low) {
				// 存在环
				return true;
			}
			// 速度不同的遍历
			else if (fast == null || fast.next == null) {
				return false;
			}
			low = low.next;
			fast = fast.next.next;
		}
	}

	public static void main(String[] args) {
		ListNode a = new ListNode(1);
		ListNode b = new ListNode(2);
		ListNode c = new ListNode(3);
		ListNode d = new ListNode(4);

		a.next = b;
		b.next = c;
		c.next = d;
		d.next = a;
		problem141 problem141 = new problem141();
		System.out.println(problem141.hasCycle(a));
	}
}
