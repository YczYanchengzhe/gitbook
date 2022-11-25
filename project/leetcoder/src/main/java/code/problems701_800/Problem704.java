package code.problems701_800;

/**
 * @author chengzhe yan
 * @description
 * @date 2022/11/24 21:43
 */
public class Problem704 {
	public int search(int[] nums, int target) {
		if (nums.length == 0) {
			return -1;
		}
		int start = 0;
		int end = nums.length - 1;
		int index = 0;
		while (start <= end) {
			index = (end + start) / 2;
			if (nums[index] > target) {
				end = index - 1;
			} else if (nums[index] < target) {
				start = index + 1;
			} else {
				return index;
			}
		}
		if (nums[index] == target) {
			return index;
		}
		return -1;
	}

	public int search2(int[] nums, int target) {
		if (nums.length == 0) {
			return -1;
		}
		int start = 0;
		int end = nums.length;
		while (start < end) {
			int index = (start + end) / 2;
			if (nums[index] > target) {
				end = index;
			} else if (nums[index] < target) {
				start = index + 1;
			} else {
				return index;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		Problem704 problem704 = new Problem704();
		int search = 0;
		search = problem704.search2(new int[]{}, 9);
		System.out.println(search);
		search = problem704.search2(new int[]{-1, 0, 3, 5, 9, 12}, 9);
		System.out.println(search);
		search = problem704.search2(new int[]{-1, 0, 3, 5, 9, 12}, 2);
		System.out.println(search);
		search = problem704.search2(new int[]{2, 5}, 5);
		System.out.println(search);

	}
}
