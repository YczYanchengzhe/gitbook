# 链表
## 一.循环链表

循环链表的尾节点指针指向头结点.

## 二.双向链表

每个节点有两个指针 , 指向前驱节点和后继节点

 双向链表可以支持 O(1) 时间复杂度的情况下找到前驱结点 

 LinkedHashMap  也是一个双向链表

## 三. 链表和数组的比较

| **时间复杂度** | **数组** | **链表** |
| -------------- | -------- | -------- |
| **插入/删除**  | O(n) | O(1)     |
| **随机访问**   | O(1)    | O(n)     |

>  链表的应用 : 

循环链表 :  约瑟夫问题

双向链表 : 支持在O(1)复杂度的情况下找到前驱结点. 在删除或者添加指定指针执行的节点的时候 , 不需要为了寻找前驱结点而遍历链表,所以可以在O(1)时间复杂度下实现

>  内存区别 : 

因为数组使用的是连续的内存空间 , 可以使用的CPU的缓存机制(CPU在数据读取时候读取的一块内存,并不是一个内存地址),预读数组中的数据,而链表由于不是连续存储,所以对于CPU缓存不友好,没办法有效预读.

> 扩缩容和大小

数组在申请时候需要指定申请的空间, 如果太小会出现不够用 , 如果太大会内存不足 , 并且对于不够的情况,只能在申请一个更大的空间,吧原数组拷贝进去,非常费时.

链表本身没有大小限制,天然支持动态扩缩容.但是由于需要额外存储一个指向下一个节点的指针,所以内存消耗会比数组多. 并且,对于频繁的插入 , 删除操作,还会导致频繁的内存申请和释放,容易造成内存碎片,对于Java来说 , 可能会导致频繁GC.

## 四.技巧
1. 借助哨兵节点
(1) 通过给链表加一个头结点,尾节点.解决对于空链表的添加 , 尾节点的删除需要处理的特殊情况问题.
//TODO 需要做 : 链表(下)
(2) 哨兵节点不单单能用在链表中 , 比如数组比较时候的边界判断 , 也可以通过增加一个哨兵来解决

2. 重点留意边界条件处理  : 
- 链表为空时
- 链表只有一个元素时
- 链表只有两个元素时
- 代码在处理链表头结点尾节点时候是否正常

3. 举例和画图 , 帮助理解

## 五.链表练习[详情](https://github.com/YczYanchengzhe/gitbook/tree/main/project/data-structure_project/src/main/java/yczyanchengzhe/structure/list)
> LeetCode : 206，

#### 1.单链表反转 - 206

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
	public ListNode reverseList(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}
		ListNode newHead = reverseList(head.next);
		head.next.next = head;
		head.next = null;
		return newHead;
	}
}
```

#### 2.链表环检测 - 141

```java
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
	public boolean hasCycle(ListNode head) {
		if (head == null || head.next == null ) {
			return false;
		}
		ListNode fast = head.next;
		ListNode low = head;
		while (true) {
			if (fast == low) {
				// 存在环
				return true;
			}
			// 速度不同的遍历
			else if (fast == null || fast.next == null) {
				return false;
			}
			low = low.next;
			fast = fast.next.next;
		}
	}
}
```

#### 3.两个有序链表合并 - 21

```java
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
```

#### 4.删除链表倒数第n个节点 - 19

```java
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
```

#### 5.求链表的中间节点 - 876

```java
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
```
