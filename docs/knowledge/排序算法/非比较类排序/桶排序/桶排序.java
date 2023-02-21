package 排序算法.非比较类排序.桶排序;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * 什么是桶排序:
 *      桶排序也是一种线性时间的排序算法。类似于计数排序所创建的统计数组，桶排序需要创建若干个桶来协助排序。
 *      每一个桶（bucket）代表一个区间范围，里面可以承载一个或多个元素。
 * 桶排序的原理：
 *        假设有一个非整数数列：（4.5，0.84，3.25，2.18，0.5），现在我们要对其进行排序，步骤如下：
 *      1. 创建桶，并确定每一个桶的区间范围。
 *      具体创建多少个桶，如何确定桶的区间范围，有很多种不同的方式。一般采用创建桶的数量等于原始数列的元素数量，除最后一个桶只包含数列最大值外，前面各个桶的区间按照比例来确定。
 *      区间跨度=（最大值-最小值）/（桶的数量-1）
 *      2. 遍历原始数列，把元素对号入座放入各个桶中
 *      3. 对每个桶中的元素分别进行排序，
 *      4. 遍历所有的桶，输出所有元素
 */
public class 桶排序 {

    public static double[] bucketSort(double[] array){
        //得到数列的最大值和最小值，并计算出差值d
        double max=array[0];
        double min=array[0];
        for (int i=1;i<array.length;i++){
            if (array[i]>max){
                max=array[i];
            }
            if (array[i]<min){
                min=array[i];
            }
        }
        double d=max-min;

        //初始化桶
        int bucketNum=array.length;
        ArrayList<LinkedList<Double>> bucketList=new ArrayList<LinkedList<Double>>(bucketNum);
        for (int i=0;i<bucketNum;i++){
            bucketList.add(new LinkedList<Double>());
        }

        //遍历原始数组将每个元素放入桶中
        for (int i=0;i<array.length;i++){
            int num=(int)((array[i]-min)*(bucketNum-1)/d);
            bucketList.get(num).add(array[i]);
        }

        //对每个桶内部进行排序
        for(int i=0;i<bucketList.size();i++){
            // 使用Collections.sort，其底层实现基于归并排序或归并排序的优化版本
            Collections.sort(bucketList.get(i));
        }

        //输出全部元素
        double[] sortedArray=new double[array.length];
        int index=0;
        for (LinkedList<Double> list:bucketList) {
            for (double element:list){
                sortedArray[index]=element;
                index++;
            }
        }
        return sortedArray;
    }

}