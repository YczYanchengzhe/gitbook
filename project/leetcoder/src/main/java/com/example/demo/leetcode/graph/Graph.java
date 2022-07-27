package com.example.demo.leetcode.graph;

import java.util.Arrays;
import java.util.LinkedList;
import lombok.Data;

/**
 * @author chengzhe yan
 * @description
 * @date 2021/9/21 10:15 下午
 */
@Data
public class Graph {
    /**
     * 定点的个数
     */
    private int v;

    /**
     * 邻接表
     */
    private LinkedList<Integer>[] adj;

    public Graph(final int v) {
        this.v = v;
        adj = new LinkedList[v];
        for (int i = 0; i <v ; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(int s, int t) {
        // 无向图,添加两次
        adj[s].add(t);
        adj[t].add(s);
    }
}
