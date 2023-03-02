1. 需求
    我们可以以shll 的方式来维护和管理Hbase. 
    例如： 执行建表语句、执行增删改查，操作等等；
    有以下订单数据，我们想要将这样的一些数据保存到Hbase
    接下来，我们将使用HBase shell 来进行一下操作：
    1. 创建表
    2. 添加数据
    3. 更新数据
    4. 删除数据
    5. 查询数据

2. 创建表
    在HBase中，所有的数据也都是保存在表中的。要将订单数据保存到HBase中，首先需要将表创建出来。


    2.1 启动Hbase Shell HBase的shell其实JRuby的IRB(交互式的Ruby), 但在其中添加了一下HBase的命令。启动HBase shell:  
            hbase shell
    
    2.2 创建表
        语法： 
            create `表名`,`列簇名`...
            创建订单表， 表名为ORDER_INFO， 该表有一个列簇为C1
            create 'ORDER_INFO','C1';
            注意： 
            · create要写成小写
            · 一个表可以包含若干个列簇
            · 命令解析： 调用hbase提供的ruby脚本的create方法，传递两个字符串参数
            · 通过下面的链接可以看到每个命令都是一个ruby脚本
            https://github.com/apache/hbase/tree/branch-2.1/hbase-shell/src/main/ruby/shell/commands
    
    2.3 查看表
        ```
            hbase(main):055:0> list
            TABLE
            ORDER_INFO
            1 row(s)
            Toook 0.0378 seconds
            => ["ORDER_INFO"]
        ```

    2.4 删除表
        要删除某个表， 必须要先禁用表

    2.4.1 禁用表
        语法：disable “表名”

    2.4.2 删除表
        语法： drop "表名"

    2.4.3 删除ORDER_INFO表名
        disable "ORDER_INFO"
        drop "ORDER_INFO"

    3 数据添加
    3.1 需求
    接下来，我们需要往订单中添加一下数据。

    3.2 PUT操作
        HBase中的put命令，可以用来将数据保存到表中。但put一次只能保存一个列的值。以下是put的
            语法结构：
            put '表名','ROWKEY','列簇名:列名','值'
            要添加以上数据，需要进行7次put操作。如
            put 'ORDER_INFO', '000001', 'C1:ID','000001'
            put 'ORDER_INFO', '000001', 'C1:STATUS','已提交'
            put 'ORDER_INFO', '000001', 'C1:pay_money', 4070
            put 'ORDER_INFO', '000001', 'C1:PAYWAY',1
            put 'ORDER_INFO', '000001', 'C1:USER_ID',4944191
            put 'ORDER_INFO', '000001', 'C1:OPERATION_DATE','2021-12-25 12:09:00'
            put 'ORDER_INFO','000001', 'C1:CATEGORY','手机;'

    4 查看添加的数据
    4.1 需求
    要求将rowkey为： 000001对应的数据查询出来   
    4.2 get命令
    在HBase中，可以使用get命令来获取单独的一行数据。
    语法：get '表名', 'rowkey'
    4.3 查询指定订单ID的数据

    get 'ORDER_INFO', '000001'

    4.4 显示中文
    在HBase shell中，如果在数据中出现了一下中文，默认HBase shell中显示出来的是十六进制编码。要想将这些编码显示为中文，我们需要在get命令后添加一个属性： {FORMATTER => 'toString'}

    4.4.1 查看订单的数据
    get 'ORDER_INFO', '000001', {FORMATTER => 'toString'}
    注：
        - { key => value }, 这个是Ruby语法，表示定义一个HASH结构
        - get是一个HBase Ruby方法， 'ORDER_INFO', '000001', {FORMATTER=>'toString'} 是put方法的三个参数
        - FORMATTER要使用大写
        - 在Ruby中用{}表示一个字典，类似于hashtable, FORMATTER表示key, 'toString'表示值
    5 更新操作
    5.1 需求
        将订单ID为000001的状态，更改为「已付款」
    5.2 使用put来更新数据
        同样， 在HBase中，也使用put命令来进行数据的更新，语法与之前的添加数据一摸一样
    5.3 更新指定的列
        put 'ORDER_INFO', '000001', 'C1:STATUS', '已付款'
        注意：
            HBase中会自动维护数据的版本
            每当执行一次put后，都会重新生成新的时间戳
                C:STATUS timestamp=A, value=已提交
                C:STATUS timestamp=B, value=已付款
                C:STATUS timestamp=C, value=已付款
    6 删除操作
    6.1 删除状态列数据
    6.1.1 需求
    将订单ID为000001的状态列删除
    6.1.2 delete命令
    在HBase中，可以使用delelte命令来将一个单元格的数据删除。语法格式如下：
    delete ‘表名’， ‘rowkey’， ‘列簇:列’。
    注意： 此处HBASE默认保存多个时间戳的版本数据，所以这里的delete删除的是最新版本的列数据。
    6.1.3 删除指定的列
    delete ‘ORDER_INFO’, '000001', 'C1:STATUS'
    6.2删除整行数据
    6.2.1需求
    将订单ID为000001的信息全部删除（删除所有的列）
    6.2.2 deteteall命令
    deteteall 命令可以将rowkey对应的所有列全部删除。语法
    deleteall '表名'， ‘rowkey’

    6.2.3 删除指定的订单
    deteteall 'ORDER_INFO', '000001'

    6.3清空表
    6.3.1 需求
    将ORDER_INFO的数据全部删除
    6.3.2 truncate命令
    truncate命令用来清空某个表中的所有数据。语法：
    truncate '表名'
    6.3.3 清空ORDER_INFO的所有数据
    truncate 'ORDER_INFO'