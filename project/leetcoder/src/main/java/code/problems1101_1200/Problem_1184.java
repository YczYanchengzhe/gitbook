package code.problems1101_1200;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/7/24 15:30
 */
public class Problem_1184 {

	public int distanceBetweenBusStops(int[] distance, int start, int destination) {
		int sum1 = 0;
		int sum2 = 0;
		int min = Math.min(start, destination);
		int max = Math.max(start, destination);
		for (int i = 0; i < distance.length; i++) {
			if (i >= min & i < max) {
				sum1 += distance[i];
			} else {
				sum2 += distance[i];
			}
		}
		return Math.min(sum2, sum1);
	}


	public static void main(String[] args) {
		int i = new Problem_1184().distanceBetweenBusStops(new int[]{1, 2, 3, 4}, 0, 3);
		System.out.println(i);
	}
}
