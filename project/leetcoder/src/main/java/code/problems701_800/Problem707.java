package code.problems701_800;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/12/1 10:05
 */
public class Problem707 {

    static class MyLinkedList {
        Node dummyHead;
        Node dummyTail;
        int size;

        public MyLinkedList() {
            dummyHead = new Node(null, null, -1);
            dummyTail = new Node(dummyHead, null, -1);
            dummyHead.next = dummyTail;
            size = 0;
        }

        public int get(int index) {
            if (index >= size || index < 0) {
                return -1;
            }
            Node curr = dummyHead.next;
            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }
            return curr.val;
        }

        public void addAtHead(int val) {
            addAtIndex(0, val);
        }

        public void addAtTail(int val) {
            addAtIndex(size, val);
        }

        public void addAtIndex(int index, int val) {
            if (index > size) {
                return;
            }
            if (index < 0) {
                index = 0;
            }
            size++;
            Node curr = dummyHead;
            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }
            Node add = new Node(curr, curr.next, val);
            curr.next = add;
            if (add.next != null) {
                add.next.prep = add;
            }
        }

        public void deleteAtIndex(int index) {
            if (index >= size || index < 0) {
                return;
            }
            Node curr = dummyHead.next;
            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }
            curr.prep.next = curr.next;
            if (curr.next != null) {
                curr.next.prep = curr.prep;
            }
            size--;
        }

        class Node {
            Node prep;
            Node next;
            int val;

            public Node(Node prep, Node next, int val) {
                this.prep = prep;
                this.next = next;
                this.val = val;
            }
        }

        @Override
        public String toString() {
            StringBuilder listInfo = new StringBuilder("MyLinkedList : ");
            Node curr = dummyHead;
            while (curr != null) {
                listInfo.append(curr.val).append(" -> ");
                curr = curr.next;
            }
            return listInfo.toString();
        }
    }

    public static void main(String[] args) {
        MyLinkedList linkedList = new MyLinkedList();
        linkedList.addAtHead(1);
        linkedList.addAtTail(3);
        linkedList.addAtIndex(1, 2);
        System.out.println(linkedList.get(1));
        linkedList.deleteAtIndex(1);
        System.out.println(linkedList.get(1));

        System.out.println(linkedList);
    }


    public static void main2(String[] args) {
        MyLinkedList linkedList = new MyLinkedList();
        linkedList.addAtHead(7);
        linkedList.addAtHead(2);
        linkedList.addAtHead(1);
        linkedList.addAtIndex(3, 0);
        linkedList.deleteAtIndex(2);
        linkedList.addAtHead(6);
        linkedList.addAtTail(4);
        int i = linkedList.get(4);
        System.out.println(i);
        linkedList.addAtHead(4);
        linkedList.addAtIndex(5, 0);
        linkedList.addAtHead(6);

        System.out.println(linkedList);
    }
}
