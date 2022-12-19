package code.problems001_100;

import code.common.ListNode;

/**
 * @author chengzhe yan
 * @description
 * @date 2021/8/29 2:30 下午
 */
public class Problem19 {


	public ListNode removeNthFromEnd2(ListNode head, int n) {
		ListNode dummyHead = new ListNode(0, head);
		ListNode first = head;
		ListNode second = dummyHead;
		for (int i = 0; i < n; i++) {
			first = first.next;
		}
		while (first != null) {
			first = first.next;
			second = second.next;
		}
		second.next = second.next.next;
		return dummyHead.next;
	}


	/**
	 * 删除倒数第n个节点,双指针法
	 *
	 * @param head
	 * @param n
	 * @return
	 */
	public ListNode removeNthFromEnd(ListNode head, int n) {
		// 通过引入哨兵节点 可以减少空判断
		ListNode result = new ListNode(0, head);
		ListNode first = head;
		ListNode second = result;
		for (int i = 0; i < n; i++) {
			first = first.next;
		}
		while (first != null) {
			first = first.next;
			second = second.next;
		}
		second.next = second.next.next;
		return result.next;
	}

	public static void main(String[] args) {
		ListNode listNode1 = new ListNode(1);
		ListNode listNode2 = new ListNode(2);
		ListNode listNode3 = new ListNode(3);
		ListNode listNode4 = new ListNode(4);
		listNode1.next = listNode2;
		listNode2.next = listNode3;
		listNode3.next = listNode4;
		ListNode listNode = new Problem19().removeNthFromEnd2(listNode1, 2);
		System.out.println(listNode.print());
	}
}
