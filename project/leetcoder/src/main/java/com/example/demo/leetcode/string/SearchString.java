package com.example.demo.leetcode.string;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chengzhe yan
 * @description TODO 需要复习
 * @date 2021/9/23 12:35 上午
 */
public class SearchString {

    public static void main(String[] args) {
        //        String des = "acaaaacecabcabaaacdbaaaaceaacb";
        String des = "dasdadadbcabcabcab";
        //        String search = "cabcdcabcdc";
        String search = "bcabcab";
        SearchString string = new SearchString();
        System.out.println(string.searchByKMP(des.toCharArray(), search.toCharArray()));
    }

    //-------------------------------------------------BF 算法 -------------------------------

    /**
     * BF 算法
     *
     * @param des    主串
     * @param search 模式串
     * @return
     */
    public int searchByBF(char[] des, char[] search) {
        int i = 0;
        int j = 0;
        // 遍历寻找
        while (i < des.length && j < search.length) {
            // 相等继续往后走
            if (des[i] == search[j]) {
                i++;
                j++;
            } else {
                // 不相等 i 回退 ,j 回退到 0
                i = i - j + 1;
                j = 0;
            }
        }
        // j 找到最后一个,说明匹配成功
        if (j == search.length) {
            return i - j;
        } else {
            return -1;
        }
    }

    //-------------------------------------------------RK 算法 -------------------------------

    /**
     * RK 算法 : 假设匹配的都是英文字母,字符集一共 26 个,设置进制为 26
     *
     * @param des
     * @param search
     * @return
     */
    public int searchByRK(char[] des, char[] search) {
        // 计算每一个search长度连续子串的哈希值,之后比较哈希值是否一致
        int[] hashCache = new int[search.length];
        // 提前计算出来每一位对应的数字
        for (int i = 0; i < hashCache.length; i++) {
            int saveNumber = 1;
            for (int j = 0; j < i; j++) {
                saveNumber = saveNumber * 26;
            }
            hashCache[i] = saveNumber;
        }
        // map 存储 hash -> 下标
        Map<Integer, Integer> mapInfo = new HashMap<>();
        for (int i = 0; i < des.length - search.length + 1; i++) {
            char[] tmp = new char[search.length];
            System.arraycopy(des, i, tmp, 0, search.length);
            int hash = calcHash(tmp, hashCache);
            mapInfo.put(hash, i);
        }
        // 计算需要比较的哈希值
        int cmp = calcHash(search, hashCache);
        // 查看是否存在
        return mapInfo.getOrDefault(cmp, -1);
    }

    /**
     * 根据指定字符计算哈希值
     *
     * @param chars
     * @param hashCache
     * @return
     */
    private int calcHash(char[] chars, int[] hashCache) {
        int hash = -1;
        for (int i = 0; i < chars.length; i++) {
            hash += (chars[i] - 'a') * hashCache[i];
        }
        return hash;
    }

    //-------------------------------------------------BM 算法 -------------------------------

    public int[] genericJump(char[] search) {
        int[] arr = new int[256];
        for (int i = 0; i < search.length; i++) {
            arr[search[i]] = i;
        }
        return arr;
    }

    /**
     * @param search 模式串
     * @param suffix : suffix 数组的下标 k，表示后缀子串的长度，如果 cabcab ,k=1 代表的是后缀子串 b ,k=2 代表的是后缀子串 ab,k=3 代表的是后缀子串 cab
     *               下标对应的数组值存储的是，在模式串中跟好后缀{u}相匹配的子串{u*}的起始下标值, suffix[1]=2, suffix[2]=1, suffix[3]=0
     *               存储的时候,存储的是公共子串最后一次出现的位置
     * @param prefix
     */
    private void generateGS(char[] search, int[] suffix, boolean[] prefix) {
        // 初始化
        for (int i = 0; i < search.length; ++i) {
            suffix[i] = -1;
            prefix[i] = false;
        }
        // des[0, i]
        for (int i = 0; i < search.length - 1; ++i) {
            int j = i;
            // 公共后缀子串长度
            int k = 0;
            // 与b[0, m-1]求公共后缀子串
            while (j >= 0 && search[j] == search[search.length - 1 - k]) {
                --j;
                // k ++ 实际上在 search[search.length - 1 - k]处理时候的含义是 : 如果当前字符相同比较前一个字符
                ++k;
                //j+1表示公共后缀子串在b[0, i]中的起始下标
                suffix[k] = j + 1;
            }
            // 如果公共后缀子串也是模式串的前缀子串
            if (j == -1) {
                prefix[k] = true;
            }
        }
    }

    /**
     * @param j      j表示坏字符对应的模式串中的字符下标;
     * @param m      m表示模式串长度
     * @param suffix
     * @param prefix
     * @return
     */
    private int moveByGS(int j, int m, int[] suffix, boolean[] prefix) {
        // 好后缀长度 :  cabcab
        int k = m - 1 - j;
        if (suffix[k] != -1) {
            return j - suffix[k] + 1;
        }
        // 当好后缀是模式串的前缀时候 :    bcabcab
        // 因为j表示坏字符的下标 好后缀的真实下标j+1
        // m-r : 表示剩下的好后缀的长度,按照--的方式,查看是否有与前缀匹配的
        for (int r = j + 2; r <= m - 1; ++r) {
            // 前缀子串与后缀子串匹配
            if (prefix[m - r]) {
                return r;
            }
        }
        return m;
    }

    public int searchByBM(char[] des, char[] search) {
        // 根据模式串 记录每个字符最后出现的下标位置 用于进行坏字符比较
        int[] jumpArr = genericJump(search);
        // 构建坏字符哈希表
        int[] suffix = new int[search.length];
        boolean[] prefix = new boolean[search.length];
        generateGS(search, suffix, prefix);
        int i = 0;
        // 如果 i 大于的话,那么长度就不匹配了,肯定不包含
        // 模式串从后往前匹配
        while (i <= des.length - search.length) {
            int j = 0;
            for (j = search.length - 1; j >= 0; j--) {
                // 比较直到出现坏字符
                if (des[i + j] != search[j]) {
                    break;
                }
            }
            if (j == -1) {
                return i;
            }
            // 这里是核心 : i 向后走到 坏字符最后一次出现的位置
            // 这里x可能会小于 0
            int x = j - jumpArr[des[i + j]];
            int y = 0;
            // 如果有好后缀的话
            if (j < search.length - 1) {
                y = moveByGS(j, search.length, suffix, prefix);
            }
            i = i + Math.max(x, y);
        }
        return -1;
    }

    //-------------------------------------------------KMP 算法 -------------------------------

    public int searchByKMP(char[] des, char[] search) {
        int[] next = getNexts(search, search.length);
        int j = 0;
        for (int i = 0; i < des.length; ++i) {
            // 一直找到a[i]和b[j]
            while (j > 0 && des[i] != search[j]) {
                j = next[j - 1] + 1;
            }
            if (des[i] == search[j]) {
                ++j;
            }
            // 找到匹配模式串
            if (j == search.length) {
                return i - search.length + 1;
            }
        }
        return -1;
    }

    /**
     * b表示模式串，m表示模式串的长度
     *
     * @param search
     * @param length
     * @return
     */
    private static int[] getNexts(char[] search, int length) {
        int[] next = new int[length];
        next[0] = -1;
        int k = -1;
        for (int i = 1; i < length; ++i) {
            while (k != -1 && search[k + 1] != search[i]) {
                k = next[k];
            }
            if (search[k + 1] == search[i]) {
                ++k;
            }
            next[i] = k;
        }
        return next;
    }

}
