package com.study.shenxing.caesar.graphic.animation;

import android.animation.ObjectAnimator;
import android.graphics.Matrix;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.study.shenxing.caesar.R;

public class CusAnimationActivity extends AppCompatActivity {
    private ImageView mTestImageView ;
    private ImageView mTestImageView1 ;
    private ImageView mTestImageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_animation);

        mTestImageView = (ImageView) findViewById(R.id.test_image);
        mTestImageView1 = (ImageView) findViewById(R.id.test_image1);
        mTestImageView2 = (ImageView) findViewById(R.id.test_image2);

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

        Matrix m1 = new Matrix();
        m1.setSkew(0.5f, 0.5f);
        Log.i("shenxing", "m1 : " + m1.toShortString());
        mTestImageView1.setImageMatrix(m1);



        mTestImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Path path = new Path();
                path.lineTo(200, 200);
                path.moveTo(1000, 200);
                path.moveTo(1000, 1000);
                path.moveTo(200, 1000);
                path.close();

                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mTestImageView2, View.X, View.Y, path);
                objectAnimator.setDuration(4000);
                objectAnimator.start();
            }
        });


    }
}
