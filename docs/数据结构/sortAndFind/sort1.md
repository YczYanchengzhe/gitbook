# 排序

> 算法的稳定性 : 这个概念是说，如果待排序的序列中存在值相等的元素，经过排序之后，相等元素之间原有的先后顺序不变。
> 		原地排序 : 空间复杂度是 O(1) 的排序算法







# 排序算法的优化

以C语言的qsort() 的底层实现原理为例 : 

- 优先使用归并排序,在数据量少的时候,多占用的空间可以接受
- 数据量大采用快排,使用三点法进行快排的优化
- 为了避免栈溢出,快排采用自己模拟的堆上栈来实现.
- 在快排过程中,对于数据量少的元素,使用插入排序







| 排序算法 | 是否是原地排序 | 是否稳定 | 最好时间复杂度 | 最差时间复杂度 | 平均时间复杂度 |
| -------- | -------------- | -------- | -------------- | -------------- | -------------- |
| 冒泡排序 | 是             | 是       | `O(n)`         | `O(n^2)`       | `O(n^2)`       |
| 插入排序 | 是             | 是       | `O(n)`         | `O(n^2)`       | `O(n^2)`       |
| 选择排序 | 是             | 不是     | `O(n^2)`       | `O(n^2)`       | `O(n^2)`       |



- 冒泡排序和插入排序比较 : 冒泡排序在进行交换时候需要进行三次赋值操作,插入排序只需要一次



# 一. 冒泡排序

```java
	private void sort(int[] nums, int start, int end) {
		for (int i = 0; i < nums.length; i++) {
			// 优化点 如果这一次遍历没有交换 ,可以提前退出
			boolean flag = false;
			for (int j = i; j < nums.length; j++) {
				if (nums[i] > nums[j]) {
					swap(nums, i, j);
					flag = true;
				}
			}
			if (!flag) {
				break;
			}
		}
	}


	private void swap(int[] nums, int n1, int n2) {
		int tmp = nums[n1];
		nums[n1] = nums[n2];
		nums[n2] = tmp;
	}
```



# 二. 插入排序

```java
	public void sort(int[] nums, int start, int end) {
		if (end < 1) {
			return;
		}
		for (int i = 1; i < nums.length; i++) {
			int value = nums[i];
			// 前j 个元素都排序好了
			int j = i - 1;
			for (; j >= 0; j--) {
				// 寻找应该处于的位置
				if (nums[j] > value) {
					nums[j + 1] = nums[j];
				} else {
					break;
				}
			}
			// 找到位置之后放入
			nums[j+1] = value;
		}
	}
```



# 三. 选择排序

```java
	public void sort(int[] nums, int start, int end) {
		if (nums == null || nums.length == 1) {
			return;
		}
		for (int i = 0; i < nums.length; i++) {
			int tmp = i;
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[j] < nums[tmp]) {
					tmp = j;
				}
			}
			// swap tmp 和 i
			if (tmp != i) {
				swap(nums, tmp, i);
			}
		}
	}
```

