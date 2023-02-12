package code.problems301_400;

/**
 * Created by yanchengzhe
 * Date 2020/10/8 13:52
 * Description 翻转字符串
 *
 * @author yanchengzhe
 */
public class Problem344 {

    public static void reverseString(char[] s) {
        if (s.length == 0) {
            return;
        }
        char tmp;
        int j = s.length - 1;
        for (int i = 0; i < s.length / 2; i++) {
            tmp = s[i];
            s[i] = s[j];
            s[j] = tmp;
            j--;
        }
    }

    public static void main(String[] args) {
        String string  = "string";
        char[]  s = string.toCharArray();
        reverseString(s);
        System.out.println(s);
    }
}
