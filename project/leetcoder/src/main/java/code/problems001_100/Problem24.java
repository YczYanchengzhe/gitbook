package code.problems001_100;

import code.common.ListNode;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/12/5 19:26
 */
public class Problem24 {

	public ListNode swapPairs(ListNode head) {
		ListNode dummyHead = new ListNode(0);
		dummyHead.next = head;
		ListNode pre = dummyHead;
		while (pre.next != null && pre.next.next != null) {
			ListNode temp = head.next.next;
			pre.next = head.next;
			head.next.next = head;
			head.next = temp;
			pre = head;
			head = temp;
		}
		return dummyHead.next;
	}

	public ListNode swap2(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}
		ListNode next = head.next;
		ListNode newNode = swap2(next.next);
		next.next = head;
		head.next = newNode;
		return next;
	}


	public static void main(String[] args) {
		ListNode listNode1 = new ListNode(1);
		ListNode listNode2 = new ListNode(2);
		ListNode listNode3 = new ListNode(3);
		ListNode listNode4 = new ListNode(4);
		listNode1.next = listNode2;
		listNode2.next = listNode3;
		listNode3.next = listNode4;
		System.out.println(listNode1.print());
		ListNode listNode = new Problem24().swap2(listNode1);
		System.out.println(listNode.print());
	}
}
