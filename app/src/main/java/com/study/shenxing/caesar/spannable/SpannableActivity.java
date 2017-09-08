package com.study.shenxing.caesar.spannable;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import com.study.shenxing.caesar.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpannableActivity extends AppCompatActivity {
    public static final String TAG = "sh_sp";

    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable);

        mTextView = (TextView) findViewById(R.id.tv_spannable_test);
        String text = getResources().getString(R.string.spannable_test_txt);
        Spannable spannable = getSpecificText(this, text);
        mTextView.setText(spannable);
    }


    public Spannable getSpecificText(Context context, String text) {
        Pattern AT_PATTERN = Pattern.compile("@[\\u4e00-\\u9fa5\\w\\-]+");
        Pattern TAG_PATTERN = Pattern.compile("#([^\\#|.]+)#");

        SpannableString str = new SpannableString(text);
        Matcher tag = AT_PATTERN.matcher(str);
        while (tag.find()) {
            String user = tag.group();
            int start = tag.start();
            Log.i(TAG, "getSpecificText: " + user);
            str.setSpan(new ForegroundColorSpan(Color.BLUE), start, start + user.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        return str;
    }
}
