package code.problems001_100;

import java.util.Arrays;

/**
 * @author chengzhe yan
 * @description : 数组中元素第一次出现的最大最小位置
 * @date 2021/9/16 6:01 下午
 */
public class Problem34 {

    public int[] searchRange(int[] nums, int target) {
        int left = binarySearch(nums, target, true);
        int right = binarySearch(nums, target, false) - 1;
        if (left <= right && right <= nums.length && nums[left] == target && nums[right] == target) {
            return new int[] {
                left,
                right
            };
        }
        return new int[] {
            -1,
            -1
        };
    }

    private int binarySearch(final int[] nums, final int target, final boolean isLeft) {
        int left = 0;
        int right = nums.length - 1;
        int ans = nums.length;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] > target || (isLeft && nums[mid] >= target)) {
                right = mid - 1;
                ans = mid;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        int[] number = new int[] {
            1
        };
        int[] ints = new Problem34().searchRange(number, 1);
        System.out.println(Arrays.toString(ints));
    }
}
