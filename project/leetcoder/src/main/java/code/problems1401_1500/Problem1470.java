package code.problems1401_1500;


/**
 * @author chengzhe yan
 * @description
 * @date 2022/8/29 22:20
 */
public class Problem1470 {
    public int[] shuffle(int[] nums, int n) {
        int[] result = new int[2 * n];
        for (int i = 0; i < n; i++) {
            result[2 * i] = nums[i];
            result[2 * i + 1] = nums[i + n];
        }
        return result;
    }


}
