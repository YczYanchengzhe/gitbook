package code.problems1601_1700;

import java.util.*;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/9/19 20:08
 */
public class Problem1636 {

    public static void main(String[] args) {
        int[] ints = new Problem1636().frequencySort(new int[]{1, 1, 2, 2, 2, 3});
        for (int anInt : ints) {
            System.out.print(anInt + " ");
        }
    }

    public int[] frequencySort(int[] nums) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int num : nums) {
            cnt.put(num, cnt.getOrDefault(num, 0) + 1);
        }
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }
        list.sort((a, b) -> {
            int cnt1 = cnt.get(a);
            int cnt2 = cnt.get(b);
            return cnt1 != cnt2 ? cnt1 - cnt2 : b - a;
        });
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            nums[i] = list.get(i);
        }
        return nums;
    }

}
