package code.problems1501_1600;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/9/9 19:24
 */
public class Problem1598 {

    public int minOperations(String[] logs) {
        int result = 0;
        for (String log : logs) {
            if ("../".equals(log)) {
                if (result > 0) {
                    result--;
                }
            } else if ("./".equals(log)) {
            } else {
                result++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int i = new Problem1598().minOperations(new String[]{"./", "../", "./"});
        System.out.println(i);
    }
}
