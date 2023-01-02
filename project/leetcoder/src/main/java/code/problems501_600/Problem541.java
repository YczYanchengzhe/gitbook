package code.problems501_600;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/12/27 18:12
 */
public class Problem541 {

    public static void main(String[] args) {
        String awed = new Problem541().reverseStr("abcd", 0);
        System.out.println(awed);
    }

    public String reverseStr(String s, int k) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i += 2 * k) {
            int left = i;
            int right = Math.min(chars.length - 1, i + k - 1);
            while (left < right) {
                char tmp = chars[left];
                chars[left] = chars[right];
                chars[right] = tmp;
                left++;
                right--;
            }
        }
        return new String(chars);
    }
}
