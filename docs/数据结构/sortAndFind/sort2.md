# 排序



> 分制思想 

| 排序算法 | 是否是原地排序 | 是否稳定 | 最好时间复杂度 | 最差时间复杂度 | 平均时间复杂度 | 空间复杂度 |
| -------- | -------------- | -------- | -------------- | -------------- | -------------- | ---------- |
| 归并排序 | 否             | 稳定     | `O(nlogn)`     | `O(nlogn)`     | `O(nlogn)`     | `O(n)`     |
| 快速排序 | 是             | 不稳定   | `O(nlogn)`     | `O(n^2)`       | `O(nlogn)`     | `O(1)`     |

- 归并排序的处理过程是由下到上的，先处理子问题，然后再合并。而快排正好相反，它的处理过程是由上到下的,先分区，然后再处理子问题.



# 一. 归并排序

```java
	public void sort(int[] nums, int start, int end) {
		nums = mergeSort(nums, start, end - 1);
		System.out.println(Arrays.toString(nums));
	}

	public int[] mergeSort(int[] nums, int start, int end) {
		if (start == end) {
			return new int[]{nums[start]};
		}
		int mid = start + (end - start) / 2;
		int[] left = mergeSort(nums, start, mid);
		int[] right = mergeSort(nums, mid + 1, end);
		//合并两个排序数组
		int i = 0, j = 0, k = 0;
		int[] result = new int[left.length + right.length];
		while (i < left.length && j < right.length) {
			result[k++] = left[i] < right[j] ? left[i++] : right[j++];
		}
		while (i < left.length) {
			result[k++] = left[i++];
		}
		while (j < right.length) {
			result[k++] = right[j++];
		}
		return result;
	}
```



# 二. 快速排序

```
public void sort(int[] nums, int start, int end) {
		if (start > end) {
			return;
		}
		// 分段 排序
        // 不停进行分区 , 分区左边都是小于分区点 , 分区右边都是大于分区点,不断分区,直到区间缩小到1
		int partition = divide(nums, start, end);
		sort(nums, start, partition - 1);
		sort(nums, partition + 1, end);
	}

	private int divide(int[] nums, int start, int end) {
		// 选择基准 按照最后一个为基准
		int base = nums[end];
		while (start < end) {
			while (start < end && nums[start] <= base) {
				start++;
			}
			if (start < end) {
				swap(nums, start, end);
				end--;
			}
			while (start < end && nums[end] >= base) {
				end--;
			}
			if (start < end) {
				swap(nums, start, end);
				start++;
			}
		}
		return start;
	}
```

## 2.1 分区点优化
- 三数取中法 : 从区间的首位中间分别取出一个数,对比大小,取中间值
- 随机法 : 每次都随机选择一个