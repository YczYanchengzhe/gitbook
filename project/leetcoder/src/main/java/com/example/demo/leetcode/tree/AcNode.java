package com.example.demo.leetcode.tree;

import lombok.Data;

/**
 * @author chengzhe yan
 * @description
 * @date 2021/9/23 10:49 下午
 */
@Data
public class AcNode {
    public char data;
    public AcNode[] children = new AcNode[26];
    public boolean isEndingChar = false;
    /**
     * 当isEndingChar=true时，记录模式串长度
     */
    public int length = -1;
    /**
     * 失败指针
     */
    public AcNode fail;

    public AcNode(char data) {
        this.data = data;
    }
}
