package com.example.demo.leetcode.graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * @author chengzhe yan
 * @description : 广度优先搜索
 * @date 2021/9/21 10:25 下午
 */
public class BFS {

    private Graph graph = new Graph(8);

    public static void main(String[] args) {
        BFS bfs = new BFS();
        // 创建图
        bfs.graph.addEdge(0, 1);
        bfs.graph.addEdge(0, 3);
        bfs.graph.addEdge(1, 2);
        bfs.graph.addEdge(1, 4);
        bfs.graph.addEdge(3, 4);
        bfs.graph.addEdge(2, 5);
        bfs.graph.addEdge(4, 5);
        bfs.graph.addEdge(4, 6);
        bfs.graph.addEdge(5, 7);
        bfs.graph.addEdge(6, 7);
        bfs.bfs(1, 7);
    }

    /**
     * @param start 从哪个位置开始找
     * @param end   找到哪里
     */
    public void bfs(int start, int end) {
        // 头尾相同,不需要处理
        if (start == end) {
            return;
        }
        // 构建数组保存访问过的元素,长度和图的维度一样
        boolean[] visited = new boolean[graph.getV()];
        // 默认所有节点都没有访问过
        Arrays.fill(visited, false);
        // 记录头结点已经被访问
        visited[start] = true;
        // 借助队列进行图的遍历
        Queue<Integer> queue = new ArrayDeque<>();
        // 借助数组保存遍历后的可达路径
        int[] prev = new int[graph.getV()];
        // 使用 -1 进行路径的初始化
        Arrays.fill(prev, -1);
        // 头结点入队
        queue.offer(start);
        while (!queue.isEmpty()) {
            // 从队列中取出一个
            int current = queue.poll();
            // 取出该节点的所有可达路径
            for (int i = 0; i < graph.getAdj()[current].size(); i++) {
                // 遍历当前节点的所有可达路径
                int to = graph.getAdj()[current].get(i);
                // 该节点没有被访问过
                if (!visited[to]) {
                    // 记录可达路径
                    prev[to] = current;
                    // to 表示当前到达的节点 , end 表示目标节点 ,如果二者相等,说明到达目标节点
                    if (to == end) {
                        // 数据打印 : start 头结点 , end 目标节点
                        print(prev, start, end);
                        return;
                    }
                    // 所有未访问节点入队
                    queue.offer(to);
                }
            }
            visited[current] = true;

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
