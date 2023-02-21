package 排序算法.非比较类排序.基数排序;

import java.util.Arrays;

/**
 * 基数排序是按照低位先排序，然后收集；再按照高位排序，然后再收集；依次类推，直到最高位。
 * 有时候有些属性是有优先级顺序的，先按低优先级排序，再按高优先级排序。最后的次序就是高优先级高的在前，高优先级相同的低优先级高的在前。
 * 将所有待比较数值（自然数）统一为同样的数位长度，数位较短的数前面补零。然后，从最低位开始，依次进行一次排序。
 * 这样从最低位排序一直到最高位排序完成以后, 数列就变成一个有序序列。
 * 
 *  基数排序的两种方式：
 *  高位优先，又称为最有效键(MSD),它的比较方向是由右至左；
 *  低位优先，又称为最无效键(LSD),它的比较方向是由左至右；
 * 算法描述： 
 *      1. 取得数组中的最大数，并取得位数；
 *      2. arr为原始数组，从最低位开始取每个位组成radix数组；
 *      3. 对radix进行计数排序（利用计数排序适用于小范围数的特点）；
 * 
 * 算法分析:
 *      计数排序是一个稳定的排序算法。当输入的元素是 n 个 0到 k 之间的整数时，时间复杂度是O(n+k)，
 *      空间复杂度也是O(n+k)，其排序速度快于任何比较排序算法。当k不是很大并且序列比较集中时，计数排序是一个很有效的排序算法。
 */
public class 基数排序 {
    

    public static void main(String[] args) {
        int[] arr = {63, 157, 189, 51, 101, 47, 141, 121, 157, 156,
                194, 117, 98, 139, 67, 133, 181, 12, 28, 0, 109};

        radixSort(arr);

        System.out.println(Arrays.toString(arr));
    }

    /**
     * 高位优先法
     *
     * @param arr 待排序列，必须为自然数
     */
    private static void radixSort(int[] arr) {
        //待排序列最大值
        int max = arr[0];
        int exp;//指数

        //计算最大值
        for (int anArr : arr) {
            if (anArr > max) {
                max = anArr;
            }
        }

        //从个位开始，对数组进行排序
        for (exp = 1; max / exp > 0; exp *= 10) {
            //存储待排元素的临时数组
            int[] temp = new int[arr.length];
            //分桶个数
            int[] buckets = new int[10];

            //将数据出现的次数存储在buckets中
            for (int value : arr) {
                //(value / exp) % 10 :value的最底位(个位)
                buckets[(value / exp) % 10]++;
            }

            //更改buckets[i]，
            for (int i = 1; i < 10; i++) {
                buckets[i] += buckets[i - 1];
            }

            //将数据存储到临时数组temp中
            for (int i = arr.length - 1; i >= 0; i--) {
                temp[buckets[(arr[i] / exp) % 10] - 1] = arr[i];
                buckets[(arr[i] / exp) % 10]--;
            }

            //将有序元素temp赋给arr
            System.arraycopy(temp, 0, arr, 0, arr.length);
        }

    }
}
