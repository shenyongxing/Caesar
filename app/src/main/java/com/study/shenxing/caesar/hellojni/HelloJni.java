package com.study.shenxing.caesar.hellojni;

/**
 * @author shenxing
 * @description jni test 命令行的形式实验
 * @date 06/02/2017
 */

public class HelloJni {

    static {
        System.loadLibrary("hellojni");
    }

    public static void main(String[] args) {
        HelloJni helloJni = new HelloJni();
        helloJni.sayHello();
    }

    native void sayHello();
}
