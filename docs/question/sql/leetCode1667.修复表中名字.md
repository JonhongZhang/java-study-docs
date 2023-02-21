### question
table:users

| column  | type    |
|---------|---------|
| user_id | int     |
| name    | varchar |

user_id 主键
该表包含用户的ID 和名字，名字仅由小写和大写字符组成

1. sql查询修复名字， 使得只有第一个字符大写，其余全是小写。
2. 按照user_id排序；

### answer
```sql
select user_id, concat(upper(left(name,1)), lower(substring(name,2))) as name from users order by user_id;
```