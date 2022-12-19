package code.common;


public class ListNode {
	public int val;
	public ListNode next;


	public ListNode(int x) {
		val = x;
		next = null;
	}

	public ListNode(int x, ListNode head) {
		val = x;
		next = head;
	}

	@Override
	public String toString() {
//		return "ListNode{" +
//				"val=" + val +
//				", next=" + next +
//				'}';
		return "a";
	}

	public String print() {
		StringBuilder s = new StringBuilder();
		ListNode curr = this;
		while (curr != null) {
			s.append(" --> ").append(curr.val);
			curr = curr.next;
		}
		return s.toString();
	}


}