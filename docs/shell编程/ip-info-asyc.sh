#!/bin/bash

# 编写脚本测试， 198.168.4.0/24  整个网段中哪些主机处于开机状态，哪些主机处于开机，哪些处于关机
# 状态(多进程 版本)

#定义一个函数， ping 某一台主机，并检测主机的存活状态
myping(){
    ping -c 2 -i 0.3 -W 1 $1 &>/dev/null
    if [$? -eq 0]; then
        echo "$1 is up"
    else
        echo "$1 is down"
    fi
}

for i in {1..254}
do
    myping 192.168.4.$1 &
done
#使用&符合，将执行的函数放入后台执行
# 这样做的好处是不需要等待ping第一台主机的回应，就可以继续ping第二台主机，依次类推。