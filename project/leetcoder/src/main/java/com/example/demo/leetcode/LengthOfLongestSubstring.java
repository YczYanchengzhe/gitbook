package com.example.demo.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chengzhe yan
 * @description 无重复字符的最长子串
 * @date 2021/7/10 12:30 下午
 */
public class LengthOfLongestSubstring {

    /**
     * 字符串无重复子串最大长度 : 暴力解法
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring1(String s) {
        if ("".equals(s) || s == null) {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }
        int max = 0;
        Set<Character> characterSet = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            int tmpMax = 0;
            for (int j = i; j < s.length(); j++) {
                if (!characterSet.contains(s.charAt(j))) {
                    characterSet.add(s.charAt(j));
                    tmpMax++;
                    if (j + 1 == s.length()) {
                        max = Math.max(max, tmpMax);
                        characterSet.clear();
                    }
                } else if (characterSet.contains(s.charAt(j))) {
                    max = Math.max(max, tmpMax);
                    characterSet.clear();
                    break;
                }
            }
        }
        return max;
    }

    /**
     * 哈希方法
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring2(String s) {
        if ("".equals(s)) {
            return 0;
        }
        //采用哈西的方法，根据下标，记录出来是否出现过，便利一便，找到最大值
        boolean[] arr = new boolean[256];
        Arrays.fill(arr, false);
        int nMax = 0, nTmp = 0;
        //遍历一遍，把每一个元素都当做起点
        for (int j = 0; j < s.length(); j++) {
            //对于每一个起点，进行查找，看是否有重复
            for (int i = j; i < s.length(); i++) {
                //出现重复，比较最大值，退出本次循环
                if (arr[s.charAt(i)]) {
                    nMax = Math.max(nMax, nTmp);
                    nTmp = 0;
                    Arrays.fill(arr, false);
                    break;
                } else {
                    arr[s.charAt(i)] = true;
                    nTmp++;
                }
            }
        }
        nMax = Math.max(nMax, nTmp);
        return nMax;
    }

    /**
     * 滑动窗口思想
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring3(String s) {
        //使用sunny 进行下表跳转 省去无用的便利
        int n = s.length(), ans = 0;
        //使用了字符映射，对于每一个字符，在数组里存储他在字符串出现的位置+1，作为j 的下标 +1，便于i 的跳转 以及 max的 比较
        int[] index = new int[128];
        for (int j = 0, i = 0; j < n; j++) {
            //找到，这个字符上一次出现的位置，跳过他，作为新的起点
            i = Math.max(index[s.charAt(j)], i);
            ans = Math.max(ans, j - i + 1);
            //对于位置进行更改
            // 存储的是第j个元素对应的字符字符,该字符对应的下一个元素的下标
            index[s.charAt(j)] = j + 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        LengthOfLongestSubstring lengthOfLongestSubstring = new LengthOfLongestSubstring();
        int result = lengthOfLongestSubstring.lengthOfLongestSubstring3("abcabcsasd");
        System.out.println(result);
    }
}
