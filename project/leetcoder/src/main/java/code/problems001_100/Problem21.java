package code.problems001_100;

import code.common.ListNode;

/**
 * @author chengzhe yan
 * @description
 * @date 2021/8/29 2:07 下午
 */
public class Problem21 {

	public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		if (l1 == null) {
			return l2;
		} else if (l2 == null) {
			return l1;
		} else if (l1.val < l2.val) {
			l1.next = mergeTwoLists(l1.next, l2);
			return l1;
		} else {
			l2.next = mergeTwoLists(l1, l2.next);
			return l2;
		}
	}
}
