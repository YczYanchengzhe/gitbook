package com.example.demo.leetcode.tree;

/**
 * @author chengzhe yan
 * @description
 * @date 2021/9/23 8:54 下午
 */
public class TireTree {

    private TrieNode root = new TrieNode('/');

    public static void main(String[] args) {
        TireTree tireTree = new TireTree();
        tireTree.add("hello");
        tireTree.add("helloworld");

        System.out.println(tireTree.search("hello"));
        System.out.println(tireTree.search("helloworld"));
        tireTree.delete("hello");
        System.out.println(tireTree.search("hello"));
    }

    public void add(String data) {
        char[] chars = data.toCharArray();
        TrieNode curr = root;
        for (int i = 0; i < chars.length; i++) {
            if (curr.getChildren()[chars[i] - 'a'] == null) {
                curr.getChildren()[chars[i] - 'a'] = new TrieNode(chars[i]);
            }
            curr = curr.getChildren()[chars[i] - 'a'];
        }
        curr.setEnd(true);
    }

    public boolean search(String data) {
        char[] chars = data.toCharArray();
        TrieNode curr = root;
        for (int i = 0; i < chars.length; i++) {
            if (curr.getChildren()[chars[i] - 'a'] == null) {
                curr.getChildren()[chars[i] - 'a'] = new TrieNode(chars[i]);
            }
            curr = curr.getChildren()[chars[i] - 'a'];
        }
        return curr.isEnd();
    }

    public void delete(String data) {
        char[] chars = data.toCharArray();
        TrieNode curr = root;
        for (int i = 0; i < chars.length; i++) {
            if (curr.getChildren()[chars[i] - 'a'] == null) {
                curr.getChildren()[chars[i] - 'a'] = new TrieNode(chars[i]);
            }
            curr = curr.getChildren()[chars[i] - 'a'];
        }
        curr.setEnd(false);
    }
}
