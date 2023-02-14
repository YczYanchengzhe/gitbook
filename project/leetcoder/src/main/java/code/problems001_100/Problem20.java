package code.problems001_100;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author chengzhe yan
 * @description
 * @date 2023/2/13 23:42
 */
public class Problem20 {

    public boolean isValid(String s) {
        Deque<Character> deque = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                deque.push(')');
            } else if (c == '[') {
                deque.push(']');
            } else if (c == '{') {
                deque.push('}');
            } else if (deque.isEmpty() || c != deque.peek()) {
                return false;
            } else {
                deque.pop();
            }
        }
        return deque.isEmpty();
    }

    public static void main(String[] args) {
        boolean valid = new Problem20().isValid("");
        System.out.println(valid);
    }
}
