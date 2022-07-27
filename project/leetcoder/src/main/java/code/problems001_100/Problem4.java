package code.problems001_100;

/**
 * Created by yanchengzhe
 * Date 2020/10/8 14:05
 * Description 两个正序数组的中位数
 *
 * @author yanchengzhe
 */
public class Problem4 {

    /**
     * 借助二分查找
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        //定义第一个数组是长度较短的那个
        if (nums1.length > nums2.length) {
            int[] tmp = nums1;
            nums1 = nums2;
            nums2 = tmp;
        }
        //求出 , 总共长度 , 找到要满足条件的左半边数组长度 , 找到两个数组联合的中位线
        int m = nums1.length;
        int n = nums2.length;
        //如果合在一起的数组长度为奇数 :
        // 左边需要有 : (m+n+1)/2 ,偶数:(m+n)/2 ,
        // 但是因为整数向下取整 ,所以为了不关心奇偶性,偶数也采用(m+n+1)/2
//        int totalLength = m + (n - m + 1) / 2;
        int totalLength = (m + n + 1) / 2;
        //取第一个数组,进行二分 满足的条件 : 左1<=右2 && 左2<=右1
        //左右是针对第一个数组的
        int left = 0;
        int right = m;
        while (left < right) {
            //+1
            int i = left + (right - left + 1) / 2;
            //知道第二个数组需要在取多少个
            int flag2 = totalLength - i;
            //!左1>右2
            if (nums1[i - 1] > nums2[flag2]) {
                //往左找
                right = i - 1;
            } else {
                //往右找
                left = i;
            }
        }
        //left -> 第一个数组的下标
        int i = left;
        int j = totalLength - i;
        int num1Left = i == 0 ? Integer.MIN_VALUE : nums1[i - 1];
        int num1Right = i == m ? Integer.MAX_VALUE : nums1[i];
        int num2Left = j == 0 ? Integer.MIN_VALUE : nums2[j - 1];
        int num2Right = j == n ? Integer.MAX_VALUE : nums2[j];

        if ((m + n) % 2 == 1) {
            //左边中较大的
            return Math.max(num1Left, num2Left);
        } else {
            //左大,右小取平均
            return (float) (Math.max(num1Left, num2Left) + Math.min(num1Right, num2Right)) / 2;
        }
    }

    public static double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        int[] tmp = new int[nums1.length + nums2.length];
        int i = 0;
        int j = 0;
        int k = 0;
        int min = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] > nums2[j]) {
                min = nums2[j];
                j++;
            } else {
                min = nums1[i];
                i++;
            }
            tmp[k] = min;
            k++;
        }
        if (i == nums1.length) {
            while (j < nums2.length) {
                tmp[k] = nums2[j];
                k++;
                j++;
            }
        } else if (j == nums2.length) {
            while (i < nums1.length) {
                tmp[k] = nums1[i];
                k++;
                i++;
            }
        }
        if (k % 2 != 0) {
            return tmp[k / 2];
        } else {
            return (double) (tmp[k / 2] + tmp[k / 2 - 1]) / 2;
        }
    }

    public static void main(String[] args) {
        int[] i1 = new int[]{1, 3};
        int[] i2 = new int[]{2};
        double result = findMedianSortedArrays2(i1, i2);
        System.out.println(result);
    }
}
