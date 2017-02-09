//
// Created by 沈星 on 06/02/2017.
// 静态注册native方法
//


#include <stdio.h>
#include "com_study_shenxing_caesar_hellojni_HelloJni.h"


/*
 * Class:     com_study_shenxing_caesar_hellojni_HelloJni
 * Method:    sayHello
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_study_shenxing_caesar_hellojni_HelloJni_sayHello
        (JNIEnv * env, jobject obj) {

    printf("HelloJni! This is my first jni call");
}
