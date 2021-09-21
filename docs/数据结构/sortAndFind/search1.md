# 查找



# 一. 二分查找  

- 二分查找针对的是一个有序的数据集合，查找思想有点类似分治思想。每次都通过跟区间的中间元素对比，将待查找的区间缩小为之前的一半，直到找到要查找的元素，或者区间被缩小为 0。

- 时间复杂度 `O(logn)`

## 1.1 代码示例

```java
   public int search(int[] nums, int search) {
        int start = 0;
        int end = nums.length - 1;
        // 注意 退出条件包含等于
        while (start <= end) {
            int tmp = start + ((end - start) / 2);
//            int tmp = start + ((end - start) >> 1);
            if (nums[tmp] > search) {
                end = tmp - 1;
            } else if (nums[tmp] < search) {
                start = tmp + 1;
            } else {
                return tmp;
            }

        }
        return -1;
    }

    public int search_2(int[] nums, int search, int start, int end) {
        if (start > end) {
            return -1;
        }
        int mid = start + ((end - start) / 2);
        if (search == nums[mid]) {
            return mid;
        } else if (search > nums[mid]) {
            return search_2(nums, search, mid + 1, end);
        } else {
            return search_2(nums, search, start, mid - 1);
        }
    }
```

## 1.2 二分查找局限性

- 二分查找依赖的是顺序表结构，简单点说就是数组。
- 二分查找针对的是有序数据。所以更适合于插入、删除操作不频繁，一次排序多次查找的场景中.
- 数据量太小不适合二分查找,数据特别少时候不如直接遍历.
- 如果数据之间的比较操作非常耗时，不管数据量大小，都推荐使用二分查找
- 数据量太大不适合二分查找。二分查找的底层需要依赖数组这种数据结构，而数组为了支持随机访问的特性，要求内存空间连续，对内存的要求比较苛刻.

