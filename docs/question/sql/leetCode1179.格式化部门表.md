### question
table: department

| Column  | type    |
|---------|---------|
| id      | int     |
| revenue | int     |
| month   | varchar |

(id, month) 联合主键

该表有关每一个部门每月收入信息，
月份（month）枚举为: ["Jan","Feb","Mar","Apr","May"
,"Jun","Jul","Aug","Sep","Oct","Nov","Dec"]


1. sql 查询来重新格式化表格，使新的表中有一个部门id列和一些对应每个月的收入（revenue）列


### answer
```sql
select id, 
       sum(case when month='Jan' then revenue end) as Jan_Revenue,
       sum(case when month='Feb' then revenue end) as Feb_Revenue,
       sum(case when month='Feb' then revenue end) as Feb_Revenue,
       sum(case when month='Mar' then revenue end) as Mar_Revenue,
       sum(case when month='Apr' then revenue end) as Apr_Revenue,
       sum(case when month='May' then revenue end) as May_Revenue,
       sum(case when month='Jul' then revenue end) as Jul_Revenue,
       sum(case when month='Aug' then revenue end) as Aug_Revenue,
       sum(case when month='Sep' then revenue end) as Sep_Revenue,
       sum(case when month='Oct' then revenue end) as Oct_Revenue,
       sum(case when month='Nov' then revenue end) as Nov_Revenue,
       sum(case when month='Dec' then revenue end) as Dec_Revenue
from department
group by id
order by id;
```