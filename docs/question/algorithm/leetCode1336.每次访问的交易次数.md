### 1336 每次访问到的交易次数
题目: Visits

| Column Name | Type |
|-------------|------|
| user_id     | int  |
| visit_date  | date |
(user_id, visit_date) 是该表主建
表的每一行表示, user_id 在visit_date访问了银行

Transactions

| Column           | Type |
|------------------|------|
| user_id          | int  |
| transaction_date | date |
| amount           | int  |

该表没有主建, 所以有可能存在重复行
该表的每一行表示user_id 在transaction_date 完成了一笔amount数额的交易
可以保证用户user在transaction_date 访问了银行(也就是说 Visits 表报包含(user_id, transaction_date)行)

银行想要得到银行客户在一次访问时的交易次数和相应的在一次访问时交易次数的客户数量的图表

### QUESTION
    写一条sql 查询多少客户访问了银行但是没有任何的交易记录, 多少客户访问了银行进行了一次交易等等
包含结果:
    transactions_count: 客户在一次访问的交易次数
    visits_count: 在transactions_count交易次数下的一次访问时的客户数量
    transactions_count的值从0到所有用户一次访问中的max(transactions_count)
    通过transactions_count排序

### init
```sql
create table visits(
    user_id int not NULL comment '用户ID',
    visit_date datetime not NULL DEFAULT CURRENT_TIMESTAMP COMMENT '访问时间',
    primary key uid_visit_date_idx('user_id', 'visit_date')
)comment '访问信息表';
    
create table transactions(
    user_id int default 0 comment '用户ID',
    transaction_date datetime not NULL DEFAULT CURRENT_TIMESTAMP COMMENT '访问时间',
    amount int default -1 comment '交易金额'
)comment '交易信息';
```

### ANSWER
```sql


```