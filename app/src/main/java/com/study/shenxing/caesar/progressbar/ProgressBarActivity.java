package com.study.shenxing.caesar.progressbar;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.study.shenxing.caesar.R;

public class ProgressBarActivity extends AppCompatActivity {

    private ImageView mProgressBar01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);

        initView();
        mTestHanlder.postDelayed(mTestRunnable, 2000);
    }

    private void initView() {
        mProgressBar01 = (ImageView) findViewById(R.id.iv_progress01);
    }

    private int mLevel;
    private Handler mTestHanlder = new Handler();
    private Runnable mTestRunnable = new Runnable() {
        @Override
        public void run() {
            if (mLevel <= 10000) {
                // @param level The new level, from 0 (minimum) to 10000 (maximum).
                mProgressBar01.setImageLevel(mLevel);
                mLevel += 100;
                mTestHanlder.postDelayed(this, 20);
            }
        }
    };
}
