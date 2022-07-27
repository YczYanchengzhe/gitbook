package code.problems301_400;


import java.util.*;

/**
 * className : problem350
 * description : 两个数组的交集
 *
 * @author : yanchengzhe
 * date : 2020/7/13 23:29
 **/
public class problem350 {

    public int[] intersect(int[] nums1, int[] nums2) {
        nums1 = sort(nums1);
        nums2 = sort(nums2);
        int i = 0, j = 0, k = 0;
        int[] result = new int[nums1.length];
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] == nums2[j]) {
                result[k] = nums1[i];
                k++;
                i++;
                j++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            } else {
                i++;
            }
        }
        int[] result2 = new int[k];
        System.arraycopy(result, 0, result2, 0, k);
        return result2;
    }

    private int[] sort(int[] nums1) {
        for (int i = 0; i < nums1.length; i++) {
            for (int j = i + 1; j < nums1.length; j++) {
                if (nums1[i] > nums1[j]) {
                    int tmp = nums1[i];
                    nums1[i] = nums1[j];
                    nums1[j] = tmp;
                }
            }
        }
        return nums1;
    }

    /**
     * 哈希表
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersect2(int[] nums1, int[] nums2) {
        int[] result = new int[Math.min(nums1.length, nums2.length)];
        Map<Integer, Integer> hashMap = new HashMap<>();
        for (int item : nums1) {
            if (hashMap.containsKey(item)) {
                int value = hashMap.get(item) + 1;
                hashMap.put(item, value);
            } else {
                hashMap.put(item, 1);
            }
        }
        int k = 0;
        for (int item : nums2) {
            if (hashMap.containsKey(item) && hashMap.get(item) > 0) {
                result[k++] = item;
                int value = hashMap.get(item) - 1;
                hashMap.put(item, value);
            }
        }
        int[] result2 = new int[k];
        System.arraycopy(result, 0, result2, 0, k);
        return result2;
    }

    /**
     * 哈希表 - 优化
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersect3(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return intersect3(nums2, nums1);
        }
        int[] result = new int[nums1.length];
        Map<Integer, Integer> hashMap = new HashMap<>();
        for (int item : nums1) {
            int value = hashMap.getOrDefault(item, 0) + 1;
            hashMap.put(item, value);
        }
        int k = 0;
        for (int item : nums2) {
            if (hashMap.containsKey(item) && hashMap.get(item) > 0) {
                result[k++] = item;
                int value = hashMap.get(item) - 1;
                hashMap.put(item, value);
            }
        }
        return Arrays.copyOfRange(result, 0, k);
    }

    public static void main(String[] args) {
        problem350 problem350 = new problem350();
        int[] nums1 = new int[]{1, 2, 2, 1};
        int[] nums2 = new int[]{2, 2};
        problem350.intersect2(nums1, nums2);
    }
}
