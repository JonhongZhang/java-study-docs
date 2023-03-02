#!/bin/bash

# 编写脚本测试 192.168.4.0/24 整个网段中哪些主机处于开机状态，哪些主机处于关机
# 状态(while 版本)
i=1
while [$i -le 254]
do
    ping -c 2 -i 0.3 -W 1 198.168.4.$i  &>/dev/null
    if [$? -eq 0];then
        echo "192.168.4.$i is up"
    else
        echo "192.168.4.$i is down"
    fi
    let++
done