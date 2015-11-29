package com.study.shenxing.caesar.graphic.animation;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.study.shenxing.caesar.R;

public class CusAnimationActivity extends AppCompatActivity {
    private ImageView mTestImageView ;
    private ImageView mTestImageView1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_animation);

        mTestImageView = (ImageView) findViewById(R.id.test_image);
        mTestImageView1 = (ImageView) findViewById(R.id.test_image1);

        CusAnimation anim = new CusAnimation();
        mTestImageView.startAnimation(anim);
//        float[] f = {1, 0, 100, 0, 1, 100, 0, 0, 1};
//        Matrix m = new Matrix();
//        m.setValues(f);

//        float[] f2 = {0, -1, 500, 1, 0, 500, 0, 0, 1};
//        m.setValues(f2);
//        mTestImageView.setImageMatrix(m);
//
//        float[] f3 = {1, 0, 500, 0, 1, 500, 0, 0, 1};
//        Matrix m1 = new Matrix();
//        m1.setValues(f3);
//        mTestImageView1.setImageMatrix(m1);
    }
}
