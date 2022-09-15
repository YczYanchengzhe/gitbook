package code.problems601_700;


/**
 * @author chengzhe yan
 * @description
 * @date 2022/9/15 12:27
 */
public class Problem672 {

    /**
     * 因为操作顺序不影响结果，且开关1+开关2=开关3，开关1+开关3=开关2，开关2+开关3=开关1，
     * 还有相同两次操作等于没有操作，通过这种抵消方式，可以得到，当n大于等于3，presses大于等于3时，最终状态只有以下8种：
     * 初始状态，开关1，开关2，开关3，开关4，开关1+开关4，开关2+开关4，开关3+开关4。
     * n小于3的情况也可以简单推算出来。
     *
     * @param n
     * @param presses
     * @return
     */
    public int flipLights(int n, int presses) {
        if (presses == 0) {
            return 1;
        }
        if (n <= 2) {
            return n == 1 ? 2 : presses == 1 ? 3 : 4;
        }
        return presses <= 2 ? (presses == 1 ? 4 : 7) : 8;
    }
}
