## type: 约瑟夫问题
采用逆向思考的方法，倒退出最后这个元素在最开始所在的位置
假设：size代表当前有几个元素，pos代表最后剩下的元素在当前环里面所在的下标，k代表每k个元素删除一个，n代表最开始有几个元素

1. 当最后只剩一个元素的时候，它的下标是0，即size=1，pos=0，我们称这个元素为r；
2. 当剩两个元素的时候，r的下标应该为：（0+k）% 2，size为2；
0是上一轮r的位置下标

![约瑟夫环](https://img-blog.csdnimg.cn/4adae7f855354bdf90303f44e8c84b88.png)

所以依次递推，当剩n-1个元素的时候，r的下标为pos，那么最开始有n个元素的时候，r的下标应该为：(pos+k)%size

```java
class Solution {
    public int findTheWinner(int n, int k) {
        return levelOrder(1,0,k,n)+1;
    }
    public int levelOrder(int size,int pos,int k, int n) {
            size++;
            pos=(pos+k)%size;
            if (size==n) return pos;
            else  return levelOrder(size,k,pos,n);
        }
}

```

### question
![](https://img-blog.csdnimg.cn/10ddc17f6f0646a6acc574a62d60eaca.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA56CU56m25YOnLeW9rOW9rA==,size_20,color_FFFFFF,t_70,g_se,x_16)

### answer
方法一：经典接法，用环形链表模拟圆圈
注意题目的要求是圆圈中，所以我们创建一个共有n个节点的环形链表，然后每次在链表当中删除第m个节点。这种方法可以称作模拟法，就是按照人家的玩法一步步做。

C++有一个顺序容器std::list，可以用来模拟环形链表。但是std::list本身并没有环形结构，所以当迭代器扫描到链表末尾时，就把迭代器移动到链表的头部，这样就人为地生成一个“环形链表”。

```C++
class Solution {
public:
    int findTheWinner(int n, int k) {
        list<int> nums;

        //将数据填入链表，这里需要的空间是O（n）
        for (int i = 1; i <= n; ++i) 
            nums.push_back(i);

        auto cur = nums.begin();    //迭代器指向头部
        while (nums.size() > 1) {
            for (int i = 1; i < k; ++i) {
                cur++;                  //迭代器向前移动k-1个人，因为自己也算一个人
                if (cur == nums.end())
                    cur = nums.begin();
            }

            /*准备淘汰第l个人*/
            auto temp = ++cur;          //保留被淘汰人顺时针的下一位，不然待会找不到
            if (temp == nums.end())
                temp = nums.begin();    //每次移动都要特别注意会不会移动到末尾

            --cur;                      //迭代器刚才指向temp了，现在退回来
            nums.erase(cur);            //淘汰
            cur = temp;
        }     
        return *cur;

    }
};
```
因为每淘汰一个人就需要k次，所以程序的时间效率为O（nm），而程序需要一个长度为n的辅助链表，所以空间效率为O（n）。


### 易错点
1. 题目明确计数时需要 包含 起始时的那位小伙伴，所以会发现数人时i是从1开始的。比如k=2，代表每数两个人淘汰该小伙伴，cur已经指向了一名小伙伴，只需要再往前数一个就行了，i=1符合这种情况，而i=0则不符合。
```
 for (int i = 1; i < k; ++i) {}
```
2. cur++ 和 ++cur的效果是相同的，但是效率却不同。++cur返回的是引用，cur++返回的是临时对象。临时对象必须要先创建，然后用完再销毁，会增加很多无用的负担。
```
auto temp = ++cur;
```