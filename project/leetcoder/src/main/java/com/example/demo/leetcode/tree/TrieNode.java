package com.example.demo.leetcode.tree;

import lombok.Data;

/**
 * @author chengzhe yan
 * @description
 * @date 2021/9/23 9:03 下午
 */
@Data
public class TrieNode {
    private char data;
    private boolean isEnd;
    private TrieNode[] children = new TrieNode[26];

    public TrieNode(final char data) {
        this.data = data;
    }
}
