package com.study.shenxing.caesar.volleyokhttpfastjson;

/**
 *
 * @Author shenxing
 * @Date 2017/10/17
 * @Email shen.xing@zyxr.com
 * @Description bean类
 */
public class User {
    public String name;

    public int age;

    public String gender;

    public String location;


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
