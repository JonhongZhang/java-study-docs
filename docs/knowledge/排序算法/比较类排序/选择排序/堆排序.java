package 排序算法.比较类排序.选择排序;


/**
 * 堆排序： 堆排序(Heap Sort)是指利用堆这种数据结构所设计的一种排序算法。
 *              堆是一个近似完全二叉树的结构，并同时满足堆积的性质：即子结点的键值或索引总是小于(或者大于)它的父节点。
 * 
 * 什么是堆：堆是一个树形结构，其实堆的底层是一棵完全二叉树。而完全二叉树是一层一层按照进入的顺序排成的。按照这个特性，我们可以用数组来按照完全二叉树实现堆。
 * 
 * 1. 构建初始堆，将待排序列构成一个大顶堆(或者小顶堆)，升序大顶堆，降序小顶堆；
 * 2. 将堆顶元素与堆尾元素交换，并断开(从待排序列中移除)堆尾元素。
 * 3. 重新构建堆。
 * 4. 重复2~3，直到待排序列中只剩下一个元素(堆顶元素)。
 */
public class 堆排序 {
    
    public static void main(String[] args) {
        //        int[] arr = {5, 1, 7, 3, 1, 6, 9, 4};
                int[] arr = {16, 7, 3, 20, 17, 8};
        
                heapSort(arr);
        
                for (int i : arr) {
                    System.out.print(i + " ");
                }
            }
        
        
            /**
             * 创建堆，
             * @param arr 待排序列
             */
            private static void heapSort(int[] arr) {
                //创建堆
                for (int i = (arr.length - 1) / 2; i >= 0; i--) {
                    //从第一个非叶子结点从下至上，从右至左调整结构
                    adjustHeap(arr, i, arr.length);
                }
        
                //调整堆结构+交换堆顶元素与末尾元素
                for (int i = arr.length - 1; i > 0; i--) {
                    //将堆顶元素与末尾元素进行交换
                    int temp = arr[i];
                    arr[i] = arr[0];
                    arr[0] = temp;
        
                    //重新对堆进行调整
                    adjustHeap(arr, 0, i);
                }
            }
        
            /**
             * 调整堆
             * @param arr 待排序列
             * @param parent 父节点
             * @param length 待排序列尾元素索引
             */
            private static void adjustHeap(int[] arr, int parent, int length) {
                //将temp作为父节点
                int temp = arr[parent];
                //左孩子
                int lChild = 2 * parent + 1;
        
                while (lChild < length) {
                    //右孩子
                    int rChild = lChild + 1;
                    // 如果有右孩子结点，并且右孩子结点的值大于左孩子结点，则选取右孩子结点
                    if (rChild < length && arr[lChild] < arr[rChild]) {
                        lChild++;
                    }
        
                    // 如果父结点的值已经大于孩子结点的值，则直接结束
                    if (temp >= arr[lChild]) {
                        break;
                    }
        
                    // 把孩子结点的值赋给父结点
                    arr[parent] = arr[lChild];
        
                    //选取孩子结点的左孩子结点,继续向下筛选
                    parent = lChild;
                    lChild = 2 * lChild + 1;
                }
                arr[parent] = temp;
            }
}
