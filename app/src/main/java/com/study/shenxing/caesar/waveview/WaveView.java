package com.study.shenxing.caesar.waveview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.LineNumberReader;

/**
 * Created by shenxing on 15/12/6.
 */
public class WaveView extends LinearLayout {

    private int mAboveWaveColor ;
    private int mBlowWaveColor ;

    private Wave mWave ;
    private Solid mSolid ;

    private int mWaveToTop ;
    private int mProgress = 80 ;

    public WaveView(Context context) {
        super(context);
        initWaveView(context);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWaveView(context);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWaveView(context);
    }

    private void initWaveView(Context context) {
        setOrientation(VERTICAL);
        mAboveWaveColor = Color.WHITE ;
        mBlowWaveColor = Color.WHITE ;

        mWave = new Wave(context) ;
        mWave.initWaveConf(16, 0.09f, 0.5f);    // 可修改为通过xml文件配置
        mWave.setAboveWaveColor(mAboveWaveColor);
        mWave.setBlowWaveColor(mBlowWaveColor);
        mWave.initializePainters();

        mSolid = new Solid(context) ;
        mSolid.setAboveWavePaint(mWave.getmAboveWavePaint());
        mSolid.setBlowWavePaint(mWave.getmBlowWavePaint());

        addView(mWave);
        addView(mSolid);

        setProgress(mProgress);
    }

    private void computeWaveToTop() {
        mWaveToTop = (int) ((1 - mProgress / 100.0f) * getHeight())  ;
        ViewGroup.LayoutParams params = mWave.getLayoutParams();
        if (params != null) {
            ((LayoutParams) params).topMargin = mWaveToTop;
        }
        mWave.setLayoutParams(params);
    }

    public void setProgress(int progress) {
        mProgress = progress > 100 ? 100 : progress ;
        computeWaveToTop();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            computeWaveToTop();
        }
    }
}
