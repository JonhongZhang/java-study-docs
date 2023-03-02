#!/bin/bash

# 通过位置变量创建 Linux 系统账户以及密码

# $1 是执行脚本的第一个参数， $2是执行脚本的第二个参数

useradd       "$1"

echo "$2" | password -stdin "$1"