package com.study.shenxing.caesar.customdrawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.shenxing.caesar.R;

public class CustomDrawableActivity extends AppCompatActivity {
    private ImageView mTestImageView;
    private TextView mTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_drawable);

//        mTestImageView = (ImageView) findViewById(R.id.iv_test_cusdrawable);
        Bitmap testBitmp = BitmapFactory.decodeResource(getResources(), R.drawable.gallery_photo_5);
//        mTestImageView.setImageDrawable(new RoundDrawable(testBitmp, 30));
//        mTestImageView.setImageDrawable(new HexagonDrawable(testBitmp));

        mTest = (TextView) findViewById(R.id.tv_test_handler);
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                mTest.setText("I have changed! Did you see it?");
                int width = mTest.getWidth();
            }
        }, 3000);

    }


}
