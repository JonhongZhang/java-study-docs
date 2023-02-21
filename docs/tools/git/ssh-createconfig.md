### 1. 在本地git base下 生成公钥
    命名： ssh-keygen -t rsa -C "公钥描述"

### 2. 密钥位置
    私钥 用户目录下 .ssh/id_rsa
    公钥 用户目录下 .ssh/id_rsa.pub

添加公钥的仓库： 点击头像 -> Manage account -> SSH keys
        -> Add key -> 将本机公钥添加进来

### 3. 找私钥和公钥的位置
    正常都在.ssh/下面， 所以只要切换到这里面 就可以查询到该密钥
    切换到该密钥目录下的命令： cd ~/.ssh
    正常存放着id_rsa(私钥)和 id_rsa.pub(公钥)
    查看指定的密钥，就可以把密钥信息粘贴到你想存放的地方。命令：cat id_rsa.pub
