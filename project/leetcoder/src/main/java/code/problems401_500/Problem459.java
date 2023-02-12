package code.problems401_500;

/**
 * @author chengzhe yan
 * @description：study
 * @date 2023/2/12 16:10
 */
public class Problem459 {

    public boolean repeatedSubstringPattern(String s) {
        String newString = s.substring(1) + s.substring(0, s.length() - 1);
        return newString.contains(s);
    }

    public static void main(String[] args) {
        System.out.println(new Problem459().repeatedSubstringPattern("abcabcabcabc"));
    }

}
