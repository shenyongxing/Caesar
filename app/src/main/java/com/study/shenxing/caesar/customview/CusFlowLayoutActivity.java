package com.study.shenxing.caesar.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.study.shenxing.caesar.R;

public class CusFlowLayoutActivity extends AppCompatActivity {
    private Button mReset;
    private SeekBar mSeekBar;
//    private WaveView mGraphicView;
//    private PaintApiTestView mPaintApiTestView;
    private ColorMatrixView mColorMatrixView;
    private float mAngle;
    private CircleProgressBar mCircleProgressBar;
    private int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.custom_flow_layout);
        setContentView(R.layout.slide_view_pager_layout) ;

        mCircleProgressBar = (CircleProgressBar) findViewById(R.id.test);

        mReset = (Button) findViewById(R.id.reset);
        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count % 2 == 0) {
                    mCircleProgressBar.startForwardAnim();
                } else {
                    mCircleProgressBar.startSecondaryForwardAnim();
                }
                count++;
            }
        });

//        mSeekBar = (SeekBar) findViewById(R.id.sb_seek_bar);
//        mSeekBar.setProgress(50);
//        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                mReset.setText((progress - 50) / 50.f * 180 + " ");
//                mAngle = (float) ((progress - 50) / 50.f * 180 * Math.PI  / 180);
//                Log.i("sh", "mAngle:" + mAngle);
//                mColorMatrixView.setAngle(mAngle);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//        mColorMatrixView = (ColorMatrixView) findViewById(R.id.graphic);
    }
}
