### leet Code 334: 递增三元子序列

给你一个整数数组 nums ，判断这个数组中是否存在长度为 3 的递增子序列。
如果存在这样的三元组下标 (i, j, k) 且满足 i < j < k ，使得 nums[i] < nums[j] < nums[k] ，返回 true ；否则，返回 false 。

1. 思路: 空间o(n)的做法很简单，我要说的是o(1)的做法，在说明具体方法之前，先定义一下“爸爸”这个词，如果说数组中的一个数是一个“爸爸”，就表示它左侧存在一个数比他小。再定义一下“爷爷”这个词，如果说数组中的一个数是一个“爷爷”，就表示它是一个爸爸，并且它有至少一个儿子也是一个爸爸。这样原题就变成了一个找爷爷的问题。
2. 具体做法：从左往右遍历，维护一个最小值min，即从nums[i]往左看最小的数，再维护一个最小的爸爸minBaba，即从nums[i]往左看最小的爸爸，min的维护是很简单的，就比较nums[i]和min取小的就行。有了min就很容易判断nums[i]是不是一个爸爸，只需比较nums[i]和min，如果nums[i]>min，那么nums[i]就是爸爸，因为min是它的儿子，判断出nums[i]是不是爸爸之后，再维护一个最小的爸爸就简单了，只需比较nums[i]和minBaba取小的就行了。最后判断nums[i]是不是爷爷，只需比较nums[i]和minBaba，大的话就是，否则就不是。一直循环结束都没有找到爷爷的话，就没有爷爷了。


这题可以一般化，递增的四元五元子序列都一样的解，再维护个最小爷爷、最小曾爷爷就是了。

顺便，考虑一下序列[1,2,3]，2和3都是1的爸爸，所以2和3肯定有一个头上是绿的。

```javascript
var increasingTriplet = function(nums) {
    let min = Number.MAX_VALUE
    let minBaba = Number.MAX_VALUE

    for (let i = 0; i < nums.length; i++) {
        const cur = nums[i]

        if (cur > minBaba) return true
        // 下一次迭代的准备工作
        if (cur > min && cur < minBaba) {
            minBaba = cur
        }
        if (cur < min) {
            min = cur
        }
    }
    return false
};
```