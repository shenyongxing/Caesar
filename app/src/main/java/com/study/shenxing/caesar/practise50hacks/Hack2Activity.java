package com.study.shenxing.caesar.practise50hacks;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

import com.study.shenxing.caesar.R;

/**
 * ViewStub demo
 * 可以利用ViewStub进行延迟加载
 * 在显示广告可以试试这个类
 */
public class Hack2Activity extends AppCompatActivity implements View.OnClickListener {
    private Button mShowViewStub;
    private Button mHideViewStub ;
    private ViewStub mViewStub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack2);

        mShowViewStub = (Button) findViewById(R.id.show_view_stub_button);
        mShowViewStub.setOnClickListener(this);
        mHideViewStub = (Button) findViewById(R.id.hide_view_stub_button);
        mHideViewStub.setOnClickListener(this);
        mViewStub = (ViewStub) findViewById(R.id.stub);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.show_view_stub_button) {
//            mViewStub.setVisibility(View.VISIBLE);
            mViewStub.inflate();
        }
        if (v.getId() == R.id.hide_view_stub_button) {
            mViewStub.setVisibility(View.INVISIBLE);
        }
    }
}
