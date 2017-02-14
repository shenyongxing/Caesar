package com.study.shenxing.caesar.dynamictest;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.study.shenxing.caesar.R;

import java.io.File;

import dalvik.system.DexClassLoader;

public class DynamicLoadActivity extends AppCompatActivity {

    private TextView mTvDynamicLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_load);

        mTvDynamicLoad = (TextView) findViewById(R.id.tv_dynamic_load);
        mTvDynamicLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicLoad();
            }
        });
    }

    private void dynamicLoad() {
        // 将/Java/目录下的文件生成的包含dex文件的类放入到/sdcard/output.jar里
        File dexOutputDir = getDir("dex1", Context.MODE_PRIVATE);   // getDir("dex1", 0)会在/data/data/**package/下创建一个名叫”app_dex1“的文件夹，其内存放的文件是自动生成output.dex 摘自博客，由于没有真机还没验证。
        String dexpath = Environment.getExternalStorageDirectory().toString() + File.separator + "output.jar";
        DexClassLoader classLoader = new DexClassLoader(dexpath, dexOutputDir.getAbsolutePath(), null, getClassLoader());

        try {
            Class cls = classLoader.loadClass("com.study.shenxing.caesar.dynamictest.ShowToastImpl");
            IShowToast showToast = (IShowToast) cls.newInstance();
            showToast.showToast(this);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
