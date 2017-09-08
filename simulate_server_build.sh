#! /bin/bash
# 模拟服务器打包过程

echo "start build"

python after_build.py

#gradle build -x lint

echo "build successfully"