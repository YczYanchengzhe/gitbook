package code.problems201_300;

import code.common.ListNode;

import java.util.List;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/12/1 20:31
 */
public class Problem206 {


    public ListNode reverseList(ListNode head) {
        ListNode tmp;
        ListNode curr = head;
        ListNode pre = null;
        while (curr != null) {
            tmp = curr.next;
            curr.next = pre;
            pre = curr;
            curr = tmp;
        }
        return pre;
    }

    public ListNode reverseList2(ListNode head) {
        return reverse(null, head);
    }

    public ListNode reverse(ListNode pre, ListNode curr) {
        if (curr == null) {
            return pre;
        }
        ListNode tmp = curr.next;
        curr.next = pre;
        return reverse(curr, tmp);
    }


    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        ListNode listNode = new Problem206().reverseList(listNode1);
        System.out.println();
    }
}
