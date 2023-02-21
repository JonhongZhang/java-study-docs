package 排序算法.比较类排序.交换排序;


/**
 * 快速排序的基本思想：
 *              通过一趟排序将待排记录分隔成独立的两部分，其中一部分记录的关键字均比另一部分的关键字小，
 *              则可分别对这两部分记录继续进行排序，以达到整个序列有序。
 * 
 * 算法描述：快速排序使用分治法来把一个串（list）分为两个子串（sub-lists）。具体算法描述如下：
 * 1. 从数列中挑出一个元素，称为 “基准”（pivot）
 * 2. 重新排序数列，所有元素比基准值小的摆放在基准前面，
 *      所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。在这个分区退出之后，该基准就处于数列的中间位置。这个称为分区（partition）操作；
 * 3. 递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序。
 * 
 * 
 * 
 * 算法分析: 基本思想：快速排序是一种分治的排序算法。它通过partition函数将待排序的数组区间切分成两部分，其中一部分的值比另一部分值都要小，所以可以对这两部分数组区间分别独立地排序，直到整个数组有序。
 *   partition函数（快速排序的关键）：在待排序的数组区间内选择一个数，将它放到一个位置，使得它左边的数都比它小，右边的数都比它大，这个位置叫做枢轴(pivot)。
 *   平均时间复杂度：O ( n l o g n ) O(nlogn)O(nlogn)
 *   平均空间复杂度：O ( l o g n ) O(logn)O(logn)
 */
public class 快速排序 {
    
    //快速排序函数体
    public static void quickSort(int[] a, int low, int high) {
        int pivot;
        for(int i = 0; i < a.length ; i++){
            System.out.println(a[i]);
        }
        System.out.println("--------------1---------");

        if (low < high) {
            System.out.println("----------2-------------low:"+a[low]+"---high:"+a[high]);
            pivot = partition(a, low, high);
            // for(int i = 0; i < a.length ; i++){
            //     System.out.println(a[i]);
            // }
            System.out.println("----------2-------------low:"+a[low]+"---high:"+a[high]);
            quickSort(a, low, pivot - 1);	//对[low, pivot-1]区间排序
            quickSort(a, pivot + 1, high);	//对[pivot+1, high]区间排序
        }
    }
	
	//切分函数
    private static int partition(int[] a, int low, int high) {
        int pivotVal = a[low];
        while (low < high) {
            while (low < high && a[high] >= pivotVal)
                --high;
            exch(a, low, high);
            while (low < high && a[low] <= pivotVal)
                ++low;
            exch(a, low, high);
        }
        return low;
    }

	//交换数组中下标为i和j的两个元素的值
    private static void exch(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        int[] a = {10,5,2,3,6,11,3,7,1};
        quickSort(a,0,a.length-1);
        for(int i = 0; i < a.length ; i++){
            System.out.println(a[i]);
        }
    }

    public static void main(String[] args) {
        quickSort();
    }
}
