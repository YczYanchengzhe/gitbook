package code.problems001_100;

/**
 * @author chengzhe yan
 * @description 通配符匹配
 * @date 2021/9/28 7:59 下午
 */
public class Problem44 {

    public static void main(String[] args) {
        System.out.println(new Problem44().isMatch_2("abceb", "a*b"));
    }

    /**
     * 给定一个字符串 (s) 和一个字符模式 (p) ，实现一个支持 '?' 和 '*' 的通配符匹配。
     * '?' 可以匹配任何单个字符。
     * '*' 可以匹配任意字符串（包括空字符串)
     * <p>
     * 动态规划 TODO 理解
     *
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch1(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int i = 1; i <= n; ++i) {
            if (p.charAt(i - 1) == '*') {
                dp[0][i] = true;
            } else {
                break;
            }
        }
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else if (p.charAt(j - 1) == '?' || s.charAt(i - 1) == p.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }
        return dp[m][n];
    }

    /**
     * 给定一个字符串 (s) 和一个字符模式 (p) ，实现一个支持 '?' 和 '*' 的通配符匹配。
     * '?' 可以匹配任何单个字符。
     * '*' 可以匹配任意字符串（包括空字符串)
     * <p>
     * 贪心算法
     *
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch_2(String s, String p) {
        // 1. 从后往前找到 p 中 最后一个 * 号的位置
        int pRight = p.length(), sRight = s.length();
        while (pRight > 0 && sRight > 0 && p.charAt(pRight - 1) != '*') {
            if (match(s.charAt(sRight - 1), p.charAt(pRight - 1))) {
                pRight--;
                sRight--;
            } else {
                return false;
            }
        }
        // 2. 如果此时已经匹配成功了 ,那么直接返回,
        // 这里要注意 : 首先,如果 p == 0 ,匹配到了头 , 此时如果成功,说明s 也匹配到头,没到头说明失败
        if (pRight == 0) {
            return sRight == 0;
        }
        // 3. 使用贪心算法进行匹配
        // 匹配的开始下标
        int pIndex = 0, sIndex = 0;
        // 匹配的记录下标
        int pRecode = -1, sRecode = -1;
        while (pIndex < pRight && sIndex < sRight) {
            // 3.1 如果遇到了 * ,记录此时的 s , p 下标 , p 向后走一位,表示 此时匹配 0 个字符
            if (p.charAt(pIndex) == '*') {
                pIndex++;
                pRecode = pIndex;
                // *匹配一位
                sRecode = sIndex;
            } else if (match(s.charAt(sIndex), p.charAt(pIndex))) {
                // 3.2 如果匹配成功,s,p 都向后走一位
                pIndex++;
                sIndex++;
            } else if (sRecode != -1 && sRecode + 1 < sRight) {
                // 3.3 如果匹配失败,判断此时是不是存在 * ,存在的话,回归到记录位置,s 在向后走一位(含义是 * 多匹配一位)
                // 注意 这里判断的是 s 字符串,因为 * 多匹配一位是针对 s 串来说的
                sRecode++;
                pIndex = pRecode;
                sIndex = sRecode;
            } else {
                // 3.4 还是匹配失败,说明匹配失败
                return false;
            }
        }
        return dealResult(p, pIndex, pRight);
    }

    public boolean dealResult(String p, int left, int right) {
        // 4. 处理结果
        // 4.1 如果p 先匹配完成了,那么没有问题,因为 p*结尾可以匹配任意多个字符
        // 4.2 如果s 先匹配完成了,那么就要保证 s 后面的所有字符都是*
        for (int i = left; i < right; i++) {
            if (p.charAt(i) != '*') {
                return false;
            }
        }
        return true;
    }

    public boolean match(char a, char b) {
        if (a == b || b == '?') {
            return true;
        }
        return false;
    }

}
