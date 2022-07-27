package code.problems701_800;

import java.util.Stack;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/7/13 00:26
 */
public class Problem735 {

	public int[] asteroidCollision(int[] asteroids) {
		if (asteroids.length <= 1) {
			return asteroids;
		}
		Stack<Integer> stack = new Stack<>();
		stack.push(asteroids[0]);
		int i = 1;
		int curr = asteroids[i];
		while (i < asteroids.length) {
			Integer pop = stack.pop();
			if ((curr > 0 && pop > 0) || (curr < 0 && pop < 0)) {
				stack.push(curr);
			} else if ((curr > 0 && pop < 0) || (curr < 0 && pop > 0)) {
				// 一正一负
				curr = (Math.abs(curr) - Math.abs(pop)) > 0 ? curr : pop;
				continue;
			}
			i++;
			curr = asteroids[i];
		}
		int[] result = new int[stack.size()];
		for (int i1 = stack.size() - 1; i1 >= 0; i1++) {
			result[i1] = stack.pop();
		}
		return result;
	}
}
