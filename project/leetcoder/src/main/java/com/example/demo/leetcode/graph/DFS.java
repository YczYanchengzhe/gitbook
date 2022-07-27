package com.example.demo.leetcode.graph;

import java.util.Arrays;

/**
 * @author chengzhe yan
 * @description
 * @date 2021/9/22 5:12 下午
 */
public class DFS {

    private Graph graph = new Graph(8);

    public static void main(String[] args) {
        DFS dfs = new DFS();
        // 创建图
        dfs.graph.addEdge(0, 1);
        dfs.graph.addEdge(0, 3);
        dfs.graph.addEdge(1, 2);
        dfs.graph.addEdge(1, 4);
        dfs.graph.addEdge(3, 4);
        dfs.graph.addEdge(2, 5);
        dfs.graph.addEdge(4, 5);
        dfs.graph.addEdge(4, 6);
        dfs.graph.addEdge(5, 7);
        dfs.graph.addEdge(6, 7);
        dfs.dfs(0, 6);
    }

    /**
     * 标识是否已经找到
     */
    boolean isFound = false;

    public void dfs(int start, int end) {
        if (start == end) {
            return;
        }
        boolean[] visited = new boolean[graph.getV()];
        Arrays.fill(visited, false);
        visited[start] = true;
        int[] prev = new int[graph.getV()];
        Arrays.fill(prev, -1);
        // 进行 dfs 深度搜索
        recurDfs(start, end, visited, prev);
        print(prev, start, end);
    }

    /**
     * @param start
     * @param end
     * @param visited
     * @param prev
     */
    private void recurDfs(final int start, final int end, final boolean[] visited, final int[] prev) {
        if (isFound) {
            return;
        }
        if (start == end) {
            isFound = true;
            return;
        }
        for (int i = 0; i < graph.getAdj()[start].size(); i++) {
            int curr = graph.getAdj()[start].get(i);
            if (!visited[curr]) {
                visited[start] = true;
                prev[curr] = start;
                recurDfs(curr, end, visited, prev);
            }
        }
    }

    /**
     * 递归打印s->t的路径
     *
     * @param prev
     * @param start
     * @param end
     */
    private void print(final int[] prev, final int start, final int end) {
        // 判断 prev[end] 不是-1 因为使用-1 初始化,如果是-1 说明不可达
        // 因为 每个位置记录的是哪个节点可以到达当前位置,
        // 以0->6 ,为例 : 首先要看 6 这个位置存储的是谁能到 6,之后在递归往回找,直到找到头结点,这样就得到一条路径.
        if (start != end && prev[end] != -1) {
            print(prev, start, prev[end]);
        }
        System.out.print(" " + end);
    }
}
