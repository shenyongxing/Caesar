package com.study.shenxing.caesar.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.study.shenxing.caesar.R;

public class CusFlowLayoutActivity extends AppCompatActivity {
    private Button mReset;
//    private WaveView mGraphicView;
    private PaintApiTestView mPaintApiTestView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.custom_flow_layout);
        setContentView(R.layout.slide_view_pager_layout) ;

        mReset = (Button) findViewById(R.id.reset);
        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPaintApiTestView != null) {
                    mPaintApiTestView.startAnim();
                }
            }
        });
        mPaintApiTestView = (PaintApiTestView) findViewById(R.id.graphic);
    }
}
