#!/bin/zsh
echo "begin create and write our own file"
if [ $# -lt 1 ]; then
  filename=$(date +%Y-%m-%d)
else
  filename=$1
fi

touch "$filename".md

#cat>>"$filename".md<<EOF    追加
cat>"$filename".md<<EOF
### Objectives
  1.

### Daily record
  1.
EOF