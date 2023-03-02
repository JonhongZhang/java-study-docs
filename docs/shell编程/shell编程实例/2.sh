#!/bin/bash
# 监控目录，将新建的文件追加到日志中
MON_DIR=/opt
inotifywait -mq -formt %f -e create $MON_DIR |\
while read files;do
    each $files >> test.log
done