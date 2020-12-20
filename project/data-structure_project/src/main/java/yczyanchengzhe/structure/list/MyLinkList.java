package yczyanchengzhe.structure.list;

import lombok.AllArgsConstructor;

/**
 * @ClassName: MyLinkLIst
 * @Description: 我的链表
 * @Create by: A
 * @Date: 2020/12/20 23:24
 */
public class MyLinkList<T> {

    /**
     * 哨兵节点 ,始终头结点
     */
    private Node<T> firstNode;

    /**
     * 哨兵节点 ,始终尾结点
     */
    private Node<T> lastNode;

    private int listSize = 0;

    public MyLinkList() {
        //初始化哨兵节点
        firstNode = new Node<>(null, null, null);
        lastNode = new Node<>(null, firstNode, null);
        firstNode.next = lastNode;
    }

    /**
     * 默认尾添加
     *
     * @param info
     */
    public void add(T info) {
        Node<T> end = lastNode.last;
        Node<T> node = (Node<T>) new Node(info, end, lastNode);
        end.next = node;
        lastNode.last = node;
        listSize++;
    }

    //删除 : 头删除 , 尾删除 , 空删除 , 只有一个元素的删除

    /**
     * 按照id 删除
     *
     * @param index
     */
    public void delete(int index) {
        //如果无元素 , 不可删除
        if (listSize == 0 || index > listSize - 1) {
            return;
        }
        //找到index 位置
        Node<T> currNode = firstNode;
        //找到需要删除的上一个 : 因为有哨兵节点  ,所以自然需要多算一个 -1(找到需要删除的上一个) +1(找到头哨兵的下一个)
        for (int i = 0; i < index; i++) {
            currNode = currNode.next;
        }
        //进行删除操作 , 将其设置为不可达 ,在GC时候会被回收
        //要删除的上一个节点的下一个节点应该是删除节点的下一个节点
        currNode.next = currNode.next.next;
        //要删除节点的下一个节点的上一个节点应该是删除节点的上一个节点
        currNode.next.last = currNode;
        listSize--;
    }

    /**
     * 按照下标查找
     *
     * @param index
     * @return
     */
    public T get(int index) {
        if (listSize == 0 || index > listSize - 1 || index < 0) {
            return null;
        }

//        Node<T> currNode = firstNode;
//        for (int i = 0; i < index; i++) {
//            currNode = currNode.next;
//        }
//        return currNode.next.info;

        //使用一次二分  :
        Node<T> currNode;
        if (index > listSize / 2) {
            //从后往前
            currNode = lastNode.last;
            for (int i = listSize - 1; i > index; i--) {
                currNode = currNode.last;
            }
        } else {
            //从前往后
            currNode = firstNode.next;
            for (int i = 0; i < index; i++) {
                currNode = currNode.next;
            }
        }
        return currNode.info;

    }

    /**
     * 按照元素删除
     *
     * @return
     */
    public boolean deleteByNode(T info) {
        if (listSize == 0 || info == null) {
            return false;
        }

//        Node<T> currNode = firstNode.next;
//        //此处有两个判断
//        for (int i =0 ;i < listSize; i++) {
//            if (info == currNode.info) {
//                //执行删除逻辑
//                return true;
//            }
//            currNode = currNode.next;
//        }

        //临时保存尾结点信息
        T tmpInfo = lastNode.last.info;

        //判断尾节点是不是要找的
        if (tmpInfo == info) {
            lastNode.last.last.next = lastNode;
            lastNode.last = lastNode.last.last;
            listSize--;
            return true;
        }
        //将尾结点设置为哨兵
        lastNode.last.info = info;
        Node<T> currNode = firstNode.next;
        while (currNode.info != info) {
            currNode = currNode.next;
        }
        //恢复哨兵节点的数据
        lastNode.last.info = tmpInfo;
        //验证找到的是不是哨兵节点
        if (currNode.next == lastNode) {
            // 要删除的节点不存在
            return false;
        } else {
            currNode.last.next = currNode.next;
            currNode.next.last = currNode.last;
            listSize--;
            return true;
        }
    }

    @AllArgsConstructor
    class Node<T> {
        T info;
        Node<T> last;
        Node<T> next;
    }
}
