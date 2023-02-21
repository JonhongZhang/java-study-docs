### case when
```sql
select name,
       case
           when class = '语文'
               THEN score end as N'语文', case
            when class = '数学'
                then score end as N'数学'
FROM score
```

```sql
select name,
       MAX(CASE WHEN className='语文'
           THEN score end) as N'语文',
       MAX(CASE WHEN className='数学'
           THEN 分数 END)  as N'数学'
FORM score group by name
```
    
### 变量
1. 变量的定义
    SQLServer的变量就是一个参数， 可以对这个参数进行赋值
   1. 定义一个变量
    DECLARE @变量名 数据结构
   示例： 
      1. DECLARE @A INT;
2. 两种变量赋值方法
   /*
    set 变量名=值
    select 变量名1=值1，变量名2=值2
   */
   --变量赋值示例
   Declare @ID int
   DECLARE @NAME VARCHAR(50)
   DECLARE @ADDRESS VARCHAR(50)
   --用set 方法给变量赋值， 此方法一次只能给一个变量赋值
   select @NAME=姓名, @ADDRESS=地址 FROM Customers Where 客户ID=@ID
   --查询变量例的结果
   Select @NAME, @ADDRESS

3. 变量的优点
   1. 使用简便
   2. 可以提供查询效率

### 临时表
    /*
    1. 需要可以随时创建， 用完随时释放
    2. 临时存储大量数据
    3. 没有正式的表结构
    */
    select * into #temp From student;
    select * from #temp;

    /*
    没有正式的表结构，只有在使用过程中才能在tempdb下找到，断开连接就释放掉了（不存在了）
    */

### 定时任务
    --每天抽取源库中得到当年截止前一天的最新数据
    --插入最新数据前先清空目标数据库中的表
    TRUNCATE TABLE SQL_Dev.dbo.Orders;
    INSERT into SQL_Dev.dbo.ORders -- 目标数据库SQL_Dev中的表Orders
    Select 订单ID, 客户ID, 员工ID, 订单日期, 发货ID,
    CONVERT(VARCHAR(10), GETDATE(), 120) ETL_DATE
    FROM SQL_Road.dbo.Orders -- 源数据库sql_road 中的orders
    where 订单日期 >= concat(year(GETDATE()), '-01-01')
        AND 订单日期<CONVERT(VARCHAR(10), GETDATE(), 120);

### 什么是事物？
    事物是在数据库按照一定的逻辑顺序执行的任务序列， 即可以由用户手动执行
    也可以由某种数据库程序自动执行。

### 事物的属性ACID   
    A：原子性（Atomicity）: 保证任务中的所有操作都执行完毕；否则，事物会在出现错误时终止，并回滚之前所有操作到原始状态。
    C: 一致性（Consistency）: 几个并行执行的事物，其执行结果必须与按某一顺序 串形执行的结果相一致。
    I: 隔离性（ISolation）: 事物与事物之间相互隔离， 事物的执行不受其他事物的干扰，，事物的中间结果对其他事物必须是透明的。
    D: 持久性（Durabillty）：对任意已提交的事物，系统必须保证该事物对数据库带来的改变是持久不会丢失，即使数据库出现故障。