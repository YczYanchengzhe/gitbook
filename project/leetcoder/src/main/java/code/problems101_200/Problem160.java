package code.problems101_200;

import code.common.ListNode;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/12/6 08:10
 */
public class Problem160 {

	public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
		ListNode first = headA;
		ListNode second = headB;
		while (headA != null && headB != null) {
			headA = headA.next;
			headB = headB.next;
		}
		while (headB != null) {
			second = second.next;
			headB = headB.next;
		}
		while (headA != null) {
			first = first.next;
			headA = headA.next;
		}
		while (first != null && second != null) {
			if (first.equals(second)) {
				return first;
			}
			first = first.next;
			second = second.next;
		}
		return null;
	}

	public static void main(String[] args) {
		ListNode listNode1 = new ListNode(1);
		ListNode listNode2 = new ListNode(2, listNode1);
		ListNode listNode3 = new ListNode(3, listNode2);
		ListNode listNode4 = new ListNode(4, listNode3);


		ListNode listNode5 = new ListNode(5, listNode3);

		ListNode intersectionNode = new Problem160().getIntersectionNode(listNode5, listNode4);
		System.out.println(intersectionNode);
	}
}
