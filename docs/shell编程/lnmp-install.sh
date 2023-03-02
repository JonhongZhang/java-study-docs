#!/bin/bash
# 一键部署 LNMP(RPM 包版本)
# 使用  yum 安装部署 LNMP, 需要提前配置好，yum 源，否则该脚本会失效
#本脚本使用与 centos.7.2 或者 Rhel7.2
yum -y install httpd
yum -y install mariadb-devel mariadb-server
yum -y install php php-mysql

systemctl start httpd mariadb
systemctl enable httpd mariadb