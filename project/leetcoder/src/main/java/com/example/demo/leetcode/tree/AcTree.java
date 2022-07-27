package com.example.demo.leetcode.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author chengzhe yan
 * @description TODO 需要复习
 * @date 2021/9/23 10:49 下午
 */
public class AcTree {

    private AcNode root = new AcNode('/');

    public static void main(String[] args) {
        AcTree tireTree = new AcTree();
        tireTree.add("hello");
        tireTree.add("helloworld");
        tireTree.buildFailurePointer();
        tireTree.match(new char[] {
            'h',
            'e',
            'l',
            'l',
            'o'
        });

    }

    public void add(String data) {
        char[] chars = data.toCharArray();
        AcNode curr = root;
        for (int i = 0; i < chars.length; i++) {
            if (curr.getChildren()[chars[i] - 'a'] == null) {
                curr.getChildren()[chars[i] - 'a'] = new AcNode(chars[i]);
            }
            curr = curr.getChildren()[chars[i] - 'a'];
        }
        curr.setEndingChar(true);
    }

    public void buildFailurePointer() {
        Queue<AcNode> queue = new LinkedList<>();
        root.fail = null;
        queue.add(root);
        while (!queue.isEmpty()) {
            AcNode p = queue.remove();
            for (int i = 0; i < 26; ++i) {
                AcNode pc = p.children[i];
                if (pc == null) {
                    continue;
                }
                if (p == root) {
                    pc.fail = root;
                } else {
                    AcNode q = p.fail;
                    while (q != null) {
                        AcNode qc = q.children[pc.data - 'a'];
                        if (qc != null) {
                            pc.fail = qc;
                            break;
                        }
                        q = q.fail;
                    }
                    if (q == null) {
                        pc.fail = root;
                    }
                }
                queue.add(pc);
            }
        }
    }

    public void match(char[] text) { // text是主串
        int n = text.length;
        AcNode p = root;
        for (int i = 0; i < n; ++i) {
            int idx = text[i] - 'a';
            while (p.children[idx] == null && p != root) {
                // 失败指针发挥作用的地方
                p = p.fail;
            }
            p = p.children[idx];
            // 如果没有匹配的，从root开始重新匹配
            if (p == null) {
                p = root;
            }
            AcNode tmp = p;
            // 打印出可以匹配的模式串
            while (tmp != root) {
                if (tmp.isEndingChar) {
                    int pos = i - tmp.length + 1;
                    System.out.println("匹配起始下标" + pos + "; 长度" + tmp.length);
                }
                tmp = tmp.fail;
            }
        }
    }
}
