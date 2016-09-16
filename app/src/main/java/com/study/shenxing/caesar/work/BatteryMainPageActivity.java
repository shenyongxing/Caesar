package com.study.shenxing.caesar.work;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.study.shenxing.caesar.R;

public class BatteryMainPageActivity extends Activity {

    private LinearLayout mLl_header;
    private FrameLayout mFl_content_bg;
    private RelativeLayout mRl_content;
    private FrameLayout mFl_mask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_main_page_another);

        bindViews();
    }

    private void bindViews() {
        mLl_header = (LinearLayout) findViewById(R.id.ll_header);
        mLl_header.setOnClickListener(mHeaderClick);
        mFl_content_bg = (FrameLayout) findViewById(R.id.fl_content_bg);
        mRl_content = (RelativeLayout) findViewById(R.id.rl_content);
        mFl_mask = (FrameLayout) findViewById(R.id.fl_mask);
    }

    private View.OnClickListener mHeaderClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(BatteryMainPageActivity.this, "hello world", Toast.LENGTH_SHORT).show();
        }
    };
}
