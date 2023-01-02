package code.offer;

/**
 * @author chengzhe yan
 * @description
 * @date 2023/1/2 18:41
 */
public class Problem5 {

    public static void main(String[] args) {
        String s = new Problem5().replaceSpace("We are happy.");
        System.out.println(s);
    }

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
