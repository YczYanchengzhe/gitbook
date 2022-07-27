package code.problems101_200;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/2/14 7:41 下午
 */
public class Problem146 {

}

class LRUCache extends LinkedHashMap<Integer, Integer> {

	private int capacity;

	public LRUCache(int capacity) {
		super(capacity, 0.75F, true);
		this.capacity = capacity;
	}

	public int get(int key) {
		return super.getOrDefault(key, -1);
	}

	public void put(int key, int value) {
		super.put(key, value);
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry eldest) {
		return size() > capacity;
	}
}