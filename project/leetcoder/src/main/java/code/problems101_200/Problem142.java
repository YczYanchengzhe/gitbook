package code.problems101_200;

import code.common.ListNode;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/12/6 08:34
 */
public class Problem142 {

	public static void main(String[] args) {
		ListNode listNode4 = new ListNode(4);
		ListNode listNode0 = new ListNode(0, listNode4);
		ListNode listNode2 = new ListNode(2, listNode0);
		ListNode listNode3 = new ListNode(3, listNode2);
		listNode4.next = listNode2;
		new Problem142().detectCycle(listNode3);
	}

	public ListNode detectCycle(ListNode head) {
		ListNode slow = head;
		ListNode quick = head;
		ListNode tmp = head;
		while (true) {
			if (quick == null || quick.next == null) {
				return null;
			}
			slow = slow.next;
			quick = quick.next.next;
			if (slow.equals(quick)) {
				while(tmp.equals(quick)){
					tmp = tmp.next;
					quick = quick.next;
				}
				return tmp;
			}
		}
	}
}
