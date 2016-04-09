package com.study.shenxing.caesar.test;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.study.shenxing.caesar.R;

/**
 * 用于验证各种知识点
 */
public class TestActivity2 extends Activity {
    private TextView mTextView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mTextView = (TextView) findViewById(R.id.test_textview);
        mTextView.setText(Html.fromHtml(String.format(getResources().getString(R.string.test_html), 10)));
    }

    // 这个是系统回调的
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
