package com.study.shenxing.caesar.windowmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.study.shenxing.caesar.R;

public class WindowManagerActivity extends AppCompatActivity {
    private Button mButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_manager);

        mButton = (Button) findViewById(R.id.start);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(WindowManagerActivity.this, WindowManagerService.class) ;
                startService(it) ;
            }
        });
    }
}
