package code.problems201_300;

import code.problems901_1000.Problem977;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/11/29 22:01
 */
public class Problem209 {

    public int minSubArrayLen(int target, int[] nums) {
        int i = 0;
        int j = 0;
        int sum = 0;
        int count = Integer.MAX_VALUE;
        while (i <= j && j <= nums.length) {
            if (sum >= target) {
                sum -= nums[i];
                count = Math.min(count, j - i);
                i++;
            } else if (j == nums.length) {
                break;
            } else {
                sum += nums[j];
                j++;
            }
        }
        return count == Integer.MAX_VALUE ? 0 : count;
    }

    public static void main(String[] args) {
        int i = new Problem209().minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3});
        System.out.println(i);
    }
}
