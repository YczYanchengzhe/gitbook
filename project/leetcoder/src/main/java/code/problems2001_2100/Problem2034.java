package code.problems2001_2100;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/1/23 12:18 下午
 */
public class Problem2034 {
	public static void main(String[] args) {
		StockPrice stockPrice = new StockPrice();
		stockPrice.update(1, 2);
		int result = stockPrice.maximum();
		System.out.println(result);
	}

}

class StockPrice {
	/**
	 * 时间 -> 价格
	 */
	private Map<Integer, Integer> timePriceMap;
	/**
	 * 价格 -> 次数
	 */
	private TreeMap<Integer, Integer> prices;

	private int maxTimeStamp;

	public StockPrice() {
		timePriceMap = new HashMap<>();
		prices = new TreeMap<>();
		maxTimeStamp = 0;
	}

	public void update(int timestamp, int price) {
		if (timestamp == 0) {
			return;
		}
		maxTimeStamp = Math.max(timestamp, maxTimeStamp);
		// 记录之前的价格
		int prePrice = timePriceMap.getOrDefault(timestamp, 0);
		timePriceMap.put(timestamp, price);
		if (prePrice > 0) {
			// 之前价格次数-1 ,
			prices.put(prePrice, prices.getOrDefault(prePrice, 0) - 1);
			//  如果-1 后次数为 0,删除
			prices.remove(prePrice, 0);
		}
		prices.put(price, prices.getOrDefault(price, 0) + 1);
	}

	public int current() {
		return timePriceMap.get(maxTimeStamp);
	}

	public int maximum() {
		return prices.lastKey();
	}

	public int minimum() {
		return prices.firstKey();
	}
}
/*
 * Your StockPrice object will be instantiated and called as such:
 * StockPrice obj = new StockPrice();
 * obj.update(timestamp,price);
 * int param_2 = obj.current();
 * int param_3 = obj.maximum();
 * int param_4 = obj.minimum();
 */