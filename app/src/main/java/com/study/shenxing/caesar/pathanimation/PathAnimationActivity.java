package com.study.shenxing.caesar.pathanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.study.shenxing.caesar.R;

public class PathAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_animation);
        assignViews();
    }

    private FlowerAnimationView mTest;
    private Button mBtnButton;
    private PathTestView mPathTestView;

    private void assignViews() {
        mTest = (FlowerAnimationView) findViewById(R.id.test);
        mBtnButton = (Button) findViewById(R.id.btn_button);
        mPathTestView = (PathTestView) findViewById(R.id.path_testview);
        mBtnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathTestView.start();
            }
        });
    }

}
