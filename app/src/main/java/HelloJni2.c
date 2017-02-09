//
// Created by 沈星 on 06/02/2017.
// 动态注册native方法
//


#include <stdio.h>
#include <jni.h>

const char *classPathName = "com/study/shenxing/caesar/hellojni/HelloJni";



// 写不下去了，需要补充一些基础知识


// 当java代码调用System.loadLibaray()时会调用c文件中的JNI_OnLoad（）方法，该方法需要自己在c文件中编写
// 然后在该方法中将C中编写的方法和java文件中编写的native方法简历关联。
// 利用结构体 JNINativeMethod 来保存native和java方法的一一对应关系
// 如下：
// static JNINativeMethod methods[] = {
//{native函数名，函数签名， C中函数实现名}
//{"add", "(II)I", (void*)myadd},
//};


