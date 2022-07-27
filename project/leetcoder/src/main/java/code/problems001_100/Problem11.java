package code.problems001_100;

/**
 * @author chengzhe yan
 * @description : 盛最多水的容器
 * @date 2021/9/26 11:27 下午
 */
public class Problem11 {


	public static void main(String[] args) {
		Problem11 problem11 = new Problem11();
		int[] height = new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7};
		int maxArea = problem11.maxArea(height);
		System.out.println(maxArea);
	}

	/**
	 * 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点(i,ai) 。
	 * 在坐标内画 n 条垂直线，垂直线 i的两个端点分别为(i,ai) 和 (i, 0) 。
	 * 找出其中的两条线，使得它们与x轴共同构成的容器可以容纳最多的水。
	 * <p>
	 * 来源：力扣（LeetCode）
	 * 链接：https://leetcode-cn.com/problems/container-with-most-water
	 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
	 * <p>
	 * 解题思路 : 双指针法
	 *
	 * @param height
	 * @return
	 */
	public int maxArea(int[] height) {
		int i = 0;
		int j = height.length - 1;

		int size = 0;
		int curr = 0;
		while (i < j) {
			curr = (j - i) * Math.min(height[i], height[j]);
			if (curr > size) {
				size = curr;
			}
			if (height[i] > height[j]) {
				j--;
			} else {
				i++;
			}
		}
		return size;
	}


}
