package code.problems001_100;

/**
 * @author chengzhe yan
 * @description
 * @date 2023/2/12 14:00
 */
public class Problem28 {

    public int strStr(String haystack, String needle) {
        if (needle == null || needle.isEmpty()) {
            return 0;
        }
        int n = haystack.length();
        int m = needle.length();

        // 增加哨兵节点，让j从0开始
        char[] s = (" " + haystack).toCharArray();
        char[] p = (" " + needle).toCharArray();

        int[] next = new int[m + 1];
        // 对模式串求next 数组
        for (int i = 2, j = 0; i <= m; i++) {
            // 不匹配 j跳转到前一个匹配的位置
            while (j > 0 && p[i] != p[j + 1]) {
                j = next[j];
            }
            // 匹配 ， j和i都往后走
            if (p[i] == p[j + 1]) {
                j++;
            }
            next[i] = j;
        }

        // 进行字符串匹配
        for (int i = 1, j = 0; i <= n; i++) {
            while (j > 0 && s[i] != p[j + 1]) {
                j = next[j];
            }
            if (s[i] == p[j + 1]) {
                j++;
            }
            if (j == m) {
                return i - m;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int i = new Problem28().strStr("sadbutsad", "sad");
        System.out.println(i);
    }
}
