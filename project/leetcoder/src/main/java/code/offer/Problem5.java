package code.offer;

/**
 * @author chengzhe yan
 * @description
 * @date 2023/1/2 18:41
 */
public class Problem5 {

    public static void main(String[] args) {
        String s = new Problem5().replaceSpace2("We are happy.");
        System.out.println(s);
    }

    /**
     * 双指针法 : 省空间
     *
     * @param s
     * @return
     */
    public String replaceSpace2(String s) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                result.append("  ");
            }
        }
        int i = s.length() - 1;
        s += result;
        int j = s.length() - 1;
        char[] chars = s.toCharArray();
        while (i >= 0) {
            if (chars[i] == ' ') {
                chars[j--] = '0';
                chars[j--] = '2';
                chars[j--] = '%';
                i--;
            } else {
                chars[j--] = chars[i--];
            }
        }
        return new String(chars);
    }

    /**
     * 直接替换 : 空间换时间
     * @param s
     * @return
     */
    public String replaceSpace(String s) {
        char[] arr = s.toCharArray();
        StringBuilder result = new StringBuilder();
        for (char c : arr) {
            if (c == ' ') {
                result.append("%20");
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
