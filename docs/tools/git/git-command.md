### 基础命令
```shell
git status //查询状态
git add   // 添加文件到暂存前区

git commit -m "commit_message"   #提交暂存区
git push              # 推送到远程仓库
git pull              # 拉取远程仓库
git log               # 查询提交记录
git reset "版本号"     # 回退到指定版本号
git branch            # 查看分支
git branch "分支"     # 创建分支
git checkout "分支"   # 切换分支
git config --list     # 查看配置信息
git checkout -b branch_name
git config user.name "用户名"    #配置单个库
git config user.email "email"   #配置单个库
git config --global user.name "username"  #全局配置
git config --global user.email "email"    #全局配置
git stash             #先把修改文件放到缓存区
git stash list        #查看缓存区
git stash pop         #对缓存栈弹出一个缓存， 恢复其缓存状态
git checkout -b master-qa origin/master-qa   # 把远程分支映射到现在切换并创建的分支
git fetch master-qa:master-qa  #和上一个一样， 创建分支， 并把远程分支映射到它
git submodule update --init    #初始化子模块
git merge TD-XXX               # 把目标分支合并到当前分支
```

### 查看.git/文件下的某个哈希值属于什么类型
    命令： git cat-file -t <SHA-1哈希值>
    如查看commit 的节点的hash值： git cat-file -t xxxxxxxxxxxxxx
    输出： commit  (也就是该节点是commit对象类型)

### git的三大对象
    blob对象： 是git 用于存储文件内容的对象
    tree对象： 是git 用于存储文件目录结构的对象
    commit：  是git最终的提交版本对象
    总结： 而平时我们的版本控制管理，正是指的管理commit对象