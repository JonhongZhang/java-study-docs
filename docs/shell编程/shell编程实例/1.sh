#!/bin/bash
#判断用户输入是否位数字
if [[ $1 =~ ^[0-9]+$ ]]; then
    echo "Is Number."
else
    echo "No Number."
fi