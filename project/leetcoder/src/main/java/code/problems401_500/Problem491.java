package code.problems401_500;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * className : problem491
 * description : 求递增子序列 - 没算明白
 *
 * @author : yanchengzhe
 * date : 2020/8/25 0:16
 **/
public class Problem491 {

    List<Integer> temp = new ArrayList<Integer>();
    List<List<Integer>> ans = new ArrayList<List<Integer>>();

    public List<List<Integer>> findSubsequences(int[] nums) {
        dfs(0, Integer.MIN_VALUE, nums);
        return ans;
    }

    public void dfs(int cur, int last, int[] nums) {
        if (cur == nums.length) {
            if (temp.size() >= 2) {
                ans.add(new ArrayList<Integer>(temp));
            }
            return;
        }
        if (nums[cur] >= last) {
            temp.add(nums[cur]);
            dfs(cur + 1, nums[cur], nums);
            temp.remove(temp.size() - 1);
        }
        if (nums[cur] != last) {
            dfs(cur + 1, last, nums);
        }
    }


//    public static List<Integer> temp = new ArrayList<Integer>();
//    public static List<List<Integer>> ans = new ArrayList<List<Integer>>();
//    public static Set<Integer> set = new HashSet<Integer>();
//    public static int n;
//
//    public static List<List<Integer>> findSubsequences(int[] nums) {
//        n = nums.length;
//        // (1 << n) 一共有这些种情况 2^n
//        for (int i = 0; i < (1 << n); ++i) {
//            //取到每一种排列组合
//            findSubsequences(i, nums);
//            //10^9+7 是一个质数
//            int hashValue = getHash(263, (int) 1E9 + 7);
//            if (check() && !set.contains(hashValue)) {
//                ans.add(new ArrayList<Integer>(temp));
//                set.add(hashValue);
//            }
//        }
//        return ans;
//    }
//
//    public static void findSubsequences(int mask, int[] nums) {
//        temp.clear();
//        for (int i = 0; i < n; ++i) {
//            if ((mask & 1) != 0) {
//                temp.add(nums[i]);
//            }
//            mask >>= 1;
//        }
//    }
//
//    public static int getHash(int base, int mod) {
//        int hashValue = 0;
//        for (int x : temp) {
//            hashValue = hashValue * base % mod + (x + 101);
//            hashValue %= mod;
//        }
//        return hashValue;
//    }
//
//    /**
//     * 检测是不是严格递增
//     * @return
//     */
//    public static boolean check() {
//        for (int i = 1; i < temp.size(); ++i) {
//            if (temp.get(i) < temp.get(i - 1)) {
//                return false;
//            }
//        }
//        return temp.size() >= 2;
//    }
}
