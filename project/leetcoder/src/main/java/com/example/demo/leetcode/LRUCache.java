package com.example.demo.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/2/14 7:57 下午
 */
public class LRUCache {
	class DLinkedNode {
		int key;
		int value;
		DLinkedNode prev;
		DLinkedNode next;

		public DLinkedNode() {
		}

		public DLinkedNode(int key, int value) {
			this.key = key;
			this.value = value;
		}
	}

	private Map<Integer, DLinkedNode> cache = new HashMap<>();

	private int size;
	private int capacity;
	private DLinkedNode head, tail;

	public LRUCache(int capacity) {
		this.capacity = capacity;
		this.size = 0;
		head = new DLinkedNode();
		tail = new DLinkedNode();
		head.next = tail;
		tail.prev = head;
	}

	public int get(int key) {
		if (cache.containsKey(key)) {
			moveToHead(cache.get(key));
			return cache.get(key).value;
		}
		return -1;
	}

	public void put(int key, int value) {
		DLinkedNode node = cache.get(key);
		if (node == null) {
			node = new DLinkedNode(key, value);
			cache.put(key, node);
			addToHead(node);
			size++;
			if (size > capacity) {
				DLinkedNode dLinkedNode = removeTail();
				cache.remove(dLinkedNode.key);
				size--;
			}
		} else {
			node.value = value;
			moveToHead(node);
		}

	}

	private void addToHead(DLinkedNode node) {
		node.prev = head;
		node.next = head.next;
		node.next.prev = node;
		head.next = node;
	}

	private void removeNode(DLinkedNode node) {
		node.prev.next = node.next;
		node.next.prev = node.prev;
	}

	private void moveToHead(DLinkedNode node) {
		removeNode(node);
		addToHead(node);
	}

	private DLinkedNode removeTail() {
		DLinkedNode res = tail.prev;
		removeNode(res);
		return res;
	}
}
