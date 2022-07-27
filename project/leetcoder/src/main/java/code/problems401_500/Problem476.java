package code.problems401_500;

/**
 * @author chengzhe yan
 * @description
 * @date 2021/10/18 9:37 上午
 */
public class Problem476 {
	public int findComplement(int num) {
		// t 掩码
		int t = num;
		t |= t >> 1;
		t |= t >> 2;
		t |= t >> 4;
		t |= t >> 8;
		t |= t >> 16;
		// ~num & t  等同于 mask ^ num
		return ~num & t;
	}

	/**
	 * 找到最高位的 1 , 取出掩码,进行异或操作
	 *
	 * @param num
	 * @return
	 */
	public int findComplement2(int num) {
		int high = 0;
		for (int i = 0; i <= 30; i++) {
			if (num >= 1 << i) {
				high = i;
			} else {
				break;
			}
		}
		// 掩码
		int mask = high == 30 ? Integer.MAX_VALUE : (1 << (high + 1)) - 1;
		return mask ^ num;
	}


	public static void main(String[] args) {
		int value = new Problem476().findComplement(10);
		System.out.println(value);
	}
}
