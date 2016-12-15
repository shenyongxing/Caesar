package com.study.shenxing.caesar.systemui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.study.shenxing.caesar.MainActivity;
import com.study.shenxing.caesar.R;

public class SystemUiActivity extends Activity {
    private Button mOne;
    private Button mTwo;
    private Button mThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_ui);

        mOne = (Button) findViewById(R.id.one);
        mOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFullImmersive();
            }
        });
        mTwo = (Button) findViewById(R.id.two);
        mTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHalfImmersive();
            }
        });

        mThree = (Button) findViewById(R.id.three);
        mThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(SystemUiActivity.this);
                dialog.show();
            }
        });
    }

    /**
     * 全沉浸式布局
     */
    private void setFullImmersive() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    /**
     * 半沉浸式布局
     */
    private void setHalfImmersive() {
        //半沉浸式布局
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
