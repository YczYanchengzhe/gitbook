package com.example.demo.leetcode;

import lombok.ToString;

/**
 * @ClassName: ReverseList
 * @Description: 翻转链表
 * @Create by: A
 * @Date: 2021/5/18 8:47
 */
public class ReverseList {
	public ListNode reverseList(ListNode head) {
		if (head == null) {
			return null;
		}
		ListNode list;
		ListNode lastNode = null;
		while (head.next != null) {
			list = head.next;
			head.next = lastNode;
			lastNode = head;
			head = list;
		}
		return head;
	}

	public ListNode reverseList2(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}
		ListNode newHead = reverseList2(head.next);
		head.next.next = head;
		head.next = null;
		return newHead;
	}

	public static void main(String[] args) {
		ListNode listNode = new ListNode(1, new ListNode(2, null));
		ReverseList reverseList = new ReverseList();
		listNode = reverseList.reverseList2(listNode);
		System.out.println(listNode);
	}

	@ToString
	static class ListNode {
		int val;
		ListNode next;

		ListNode() {
		}

		ListNode(int val) {
			this.val = val;
		}

		ListNode(int val, ListNode next) {
			this.val = val;
			this.next = next;
		}
	}
}
