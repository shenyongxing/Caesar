package com.study.shenxing.caesar.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.study.shenxing.caesar.R;

/**
 * 用于验证各种知识点
 */
public class TestActivity2 extends Activity {
    private ImageView mTestImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        mTestImg = (ImageView) findViewById(R.id.iv_scale_test);
        mTestImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAnimation();
            }
        });
    }

    // 这个是系统回调的
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void doAnimation() {
        ScaleAnimation sa = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
        sa.setDuration(10000);
        mTestImg.startAnimation(sa);
    }
}