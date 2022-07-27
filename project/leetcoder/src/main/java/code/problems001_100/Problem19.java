package code.problems001_100;

import code.common.ListNode;

/**
 * @author chengzhe yan
 * @description
 * @date 2021/8/29 2:30 下午
 */
public class Problem19 {
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
}
