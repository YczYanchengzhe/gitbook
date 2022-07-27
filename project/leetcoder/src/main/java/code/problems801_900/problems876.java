package code.problems801_900;

import code.common.ListNode;

/**
 * @author chengzhe yan
 * @description
 * @date 2021/8/29 2:44 下午
 */
public class problems876 {

	/**
	 * 双指针法  一个走一步 ,一个走两步
	 *
	 * @param head
	 * @return
	 */
	public ListNode middleNode(ListNode head) {
		if (head == null) {
			return null;
		}
		ListNode low = head;
		ListNode fast = head;
		while (fast != null) {
			if (fast.next == null) {
				break;
			}
			fast = fast.next.next;
			low = low.next;
		}
		return low;
	}
}
