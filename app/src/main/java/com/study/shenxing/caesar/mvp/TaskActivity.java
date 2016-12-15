package com.study.shenxing.caesar.mvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.study.shenxing.caesar.R;

public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);


        TaskFragment taskFragment = TaskFragment.newInstance();
        ActivityUtils.addFragmentToActivity(getFragmentManager(), taskFragment, R.id.activity_task);




    }
}
