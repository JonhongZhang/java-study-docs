### question
table: followers

| Column      | Type |
|-------------|------|
| user_id     | int  |
| follower_id | int  |

(user_id,follower_Id) 联合主键
该表包含一个关注关系中关注者和用户的编号，其中关注者关注用户

1. 写出sql语句，对于每一个用户，反悔用户的关注者数量
2. 按照user_id 的顺序返回结果

### answer
```sql
select user_id, count(*) as followers_count 
from followers 
group by user_id 
order by user_id;
```