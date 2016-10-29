package com.study.shenxing.caesar.customdrawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.study.shenxing.caesar.R;

public class CustomDrawableActivity extends AppCompatActivity {
    private ImageView mTestImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_drawable);

        mTestImageView = (ImageView) findViewById(R.id.iv_test_cusdrawable);
        Bitmap testBitmp = BitmapFactory.decodeResource(getResources(), R.drawable.gallery_photo_5);
//        mTestImageView.setImageDrawable(new RoundDrawable(testBitmp, 30));
        mTestImageView.setImageDrawable(new HexagonDrawable(testBitmp));
    }


}
