package com.example.demo.leetcode.future;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/9/21 16:40
 */
public class FutureDemo {
    public static void main(String[] args) throws InterruptedException {
        List<CompletableFuture<Boolean>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
            completableFuture.completeAsync(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            });
            futureList.add(completableFuture);
        }
        System.out.println("run : " + System.currentTimeMillis());
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join();
//        whenComplete((v, e) -> {
//            System.out.println(Thread.currentThread().getId() + " : end : " + System.currentTimeMillis());
//        });
        System.out.println("run : " + System.currentTimeMillis());
        Thread.sleep(10000);
    }
}
