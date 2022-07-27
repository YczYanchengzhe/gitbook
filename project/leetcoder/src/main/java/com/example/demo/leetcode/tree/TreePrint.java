package com.example.demo.leetcode.tree;

import lombok.Data;
import lombok.ToString;

/**
 * @author chengzhe yan
 * @description
 * @date 2021/9/21 10:31 上午
 */
public class TreePrint {

    /**
     * 数据查找
     *
     * @param root
     * @param value
     * @return
     */
    public static Node find(Node root, int value) {
        while (root != null) {
            if (root.getValue() < value) {
                root = root.getRight();
            } else if (root.getValue() > value) {
                root = root.getLeft();
            } else {
                return root;
            }
        }
        return null;
    }

    /**
     * 插入数据
     *
     * @param root
     * @param value 需要插入的值
     * @return 插入后的根节点
     */
    public static Node insert(Node root, int value) {
        Node result = root;
        if (root == null) {
            result = new Node(value);
            return result;
        }
        Node newNode = new Node(value);
        while (root != null) {
            if (root.getValue() < value) {
                if (root.getRight() == null) {
                    root.setRight(newNode);
                    newNode.setParent(root);
                    return result;
                }
                root = root.getRight();
            } else if (root.getValue() > value) {
                if (root.getLeft() == null) {
                    root.setLeft(newNode);
                    newNode.setParent(root);
                    return result;
                }
                root = root.getLeft();
            }
        }
        return result;
    }

    public static Node delete(Node root, int value) {
        Node needDelete = find(root, value);
        if (needDelete == null) {
            // 没找到
            return root;

        }

        // 要删除的节点有两个子节点 左右孩子都不是空 , 选择右边最小的作为根
        if (needDelete.getRight() != null && needDelete.getLeft() != null) {
            Node newSon = needDelete.getRight();
            while (newSon.getLeft() != null) {
                newSon = newSon.getLeft();
            }
            // 进行数据的替换
            needDelete.setValue(newSon.getValue());
            needDelete = newSon;
        }
        // 删除节点是叶子节点或者只有一个孩子
        Node child = null;
        if (needDelete.getLeft() != null) {
            child = needDelete.getLeft();
        } else if (needDelete.getRight() != null) {
            child = needDelete.getRight();
        }
        if (needDelete.getParent() == null) {
            // 删除的是根节点
            if (child != null) {
                child.setParent(null);
            }
            return child;
        } else if (needDelete.getParent().getLeft() == needDelete) {
            // 左节点
            needDelete.getParent().setLeft(child);
        } else {
            // 右节点
            needDelete.getParent().setRight(child);
        }
        if (child != null) {
            child.setParent(needDelete.getParent());
        }
        return root;
    }

    /**
     * 前序遍历
     *
     * @param root
     */
    public static void preOrder(Node root) {
        if (root == null) {
            return;
        }
        printInfo(root);
        preOrder(root.getLeft());
        preOrder(root.getRight());
    }

    /**
     * 中序遍历
     *
     * @param root
     */
    public static void inOrder(Node root) {
        if (root == null) {
            return;
        }
        inOrder(root.getLeft());
        printInfo(root);
        inOrder(root.getRight());
    }

    /**
     * 后序遍历
     *
     * @param root
     */
    public static void postOrder(Node root) {
        if (root == null) {
            return;
        }
        postOrder(root.getLeft());
        postOrder(root.getRight());
        printInfo(root);
    }

    public static void printInfo(Node node) {
        System.out.print(node.getValue() + " ");
    }

    static class Node {
        private int value;
        private Node parent;
        private Node left;
        private Node right;

        public Node(final int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(final int value) {
            this.value = value;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(final Node parent) {
            this.parent = parent;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(final Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(final Node right) {
            this.right = right;
        }
    }

    public static void main(String[] args) {

        Node node = insert(insert(insert(insert(null, 3), 1), 5), 2);

        node = delete(node, 3);
        node = delete(node, 1);
        node = delete(node, 5);

        Node node0 = new Node(0);
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);

        node3.setLeft(node1);
        node3.setRight(node5);

        node1.setLeft(node0);
        node1.setRight(node2);

        node5.setLeft(node4);
        node5.setRight(node6);

        preOrder(node3);
        System.out.println();
        inOrder(node3);
        System.out.println();
        postOrder(node3);
        System.out.println();

        Node findValue = find(node3, 2);
        System.out.println(findValue);
    }
}
