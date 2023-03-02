#!/bin/bash

# 编写脚本测试， 198.168.4.0/24  整个网段中哪些主机处于开机状态，哪些主机处于开机，哪些处于关机
# 状态(for 版本)

for i in {1..254}
do
        # 每隔0.3秒ping一次， 一共ping2次，并且1毫秒为单位设置ping的超时时间
        ping -c 2 -i 0.3 1 192.168.4.$i &>/dev/null
    if [$? -eq 0];
    then
        echo "192.168.4.$i is up"
    else
        echo " 192.168.4.$i is down"
    fi
done