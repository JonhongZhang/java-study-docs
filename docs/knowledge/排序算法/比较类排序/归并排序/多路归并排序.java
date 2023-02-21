package 排序算法.比较类排序.归并排序;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 归并排序是建立在归并操作上的一种有效的排序算法。
 * 该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。
 * 将已有序的子序列合并，得到完全有序的序列；即先使每个子序列有序，再使子序列段间有序。若将两个有序表合并成一个有序表，称为2-路归并。
 * 
 * 算法描述:
 * 把长度为n的输入序列分成两个长度为n/2的子序列；
 * 对这两个子序列分别采用归并排序；
 * 将两个排序好的子序列合并成一个最终的排序序列。
 * 
 */
public class 多路归并排序 {
 
    // private static int[] arr;
    // int[] mergeSort(int[] arr) {
    //     int len = arr.length;
    //     if (len < 2) {
    //         return arr;
    //     }
    //     int middle = (int)Math.floor(len / 2);
    //     int[] left = Arrays.copyOfRange(arr, 0, middle);
    //     int[] right = Arrays.copyOfRange(arr, middle, len);
    //     return merge(mergeSort(left), mergeSort(right));
    // }
     
    // int[] merge(int[] left, int[] right) {
    //     int[] result;
     
    //     while (left.length>0 && right.length>0) {
    //         if (left[0] <= right[0]) {
    //             result.push(left.());
    //         } else {
    //             result.push(right.shift());
    //         }
    //     }
     
    //     while (left.length > 0)
    //         result.push(left.shift());
     
    //     while (right.length > 0)
    //         result.push(right.shift());
     
    //     return result;
    // }



    public static int[] sort(int[] a,int low,int high){
        int mid = (low+high)/2;
        if(low<high){
            sort(a,low,mid);
            sort(a,mid+1,high);
            //左右归并
            merge(a,low,mid,high);
        }
        return a;
    }
     
    public static void merge(int[] a, int low, int mid, int high) {
        int[] temp = new int[high-low+1];
        int i= low;
        int j = mid+1;
        int k=0;
        // 把较小的数先移到新数组中
        while(i<=mid && j<=high){
            if(a[i]<a[j]){
                temp[k++] = a[i++];
            }else{
                temp[k++] = a[j++];
            }
        }
        // 把左边剩余的数移入数组 
        while(i<=mid){
            temp[k++] = a[i++];
        }
        // 把右边边剩余的数移入数组
        while(j<=high){
            temp[k++] = a[j++];
        }
        // 把新数组中的数覆盖nums数组
        for(int x=0;x<temp.length;x++){
            a[x+low] = temp[x];
        }
    }
}
