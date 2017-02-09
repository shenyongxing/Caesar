package com.study.shenxing.caesar.hellojni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.study.shenxing.caesar.R;

public class JniTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni_test);

        TextView textView = (TextView) findViewById(R.id.tv_jni);
        textView.setText(getStringFromNative());
    }

    static {
        System.loadLibrary("jnitest");
    }


    public native String getStringFromNative();

    /**
     * 第二步，需要在build.gradle文件配置ndk节点, 见build.gradle文件
     */

    /**
     * 第三步，生成.h头文件
     * 在终端利用javah 生成头文件
     *
     * javah -d jni com.study.shenxing.caesar.hellojni.JniTestActivity
     *
     * 利用ndk-build的方式走不下去，需要参考官网深入学习
     *
     * 利用gcc编译时将so文件复制到jniLibs下面，出现 .so is not a valid ELF object 错误
     * 参考 http://stackoverflow.com/questions/27704791/so-is-not-a-valid-elf-object
     */



}
