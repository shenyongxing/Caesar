package com.study.shenxing.caesar.picossa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.study.shenxing.caesar.R;

public class PicossaActivity extends AppCompatActivity {

    private ImageView mIvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picossa);

        mIvTest = (ImageView) findViewById(R.id.iv_test_picossa);


        Picasso.with(this).load(R.drawable.ic_launcher)
                .resize(10, 10).into(mIvTest);
    }
}
