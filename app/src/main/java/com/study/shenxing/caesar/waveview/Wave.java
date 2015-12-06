package com.study.shenxing.caesar.waveview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shenxing on 15/12/6.
 *  y=Asin(ωx+φ)+k
 */
public class Wave extends View {

    private Paint mAboveWavePaint = new Paint() ;
    private Paint mBlowWavePaint = new Paint() ;

    private Path mAbovePath = new Path() ;
    private Path mBlowPath = new Path() ;

    private int mAboveWaveColor ;
    private int mBlowWaveColor ;

    private float mAboveOffset ;
    private float mBlowOffset ;

    private int mLeft ;
    private int mTop ;
    private int mRight ;
    private int mBottom ;

    private float mMaxRight ;
    private float mWaveLength ;

    private int mWaveHeight ; // 控制波浪的振幅
    private float mWaveHz ; // 控制移动的距离
    private float mWaveMultiple ;   // 控制波浪的长度

    // ω
    private double mOmega ;

    private static float X_SPACE = 20.f ;
    private static int DEFAULT_ABOVE_WAVE_ALPHA = 50 ;
    private static int DEFAULT_BLOW_WAVE_ALPHA = 30 ;

    private RefreshProgressRunnable mRefreshProgressRunnable;


    public Wave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Wave(Context context) {
        super(context);
    }

    public Wave(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initWaveConf(int waveHeight, float waveHz, float waveMultiple) {
        mWaveHeight = waveHeight ;
        mWaveHz = waveHz;
        mWaveMultiple = waveMultiple ;
        mBlowOffset = mWaveHeight * 0.4f;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                mWaveHeight * 2);
        setLayoutParams(params);
    }

    public void setAboveWaveColor(int aboveWaveColor) {
        this.mAboveWaveColor = aboveWaveColor;
    }

    public void setBlowWaveColor(int blowWaveColor) {
        this.mBlowWaveColor = blowWaveColor;
    }


    public void initializePainters() {
        mAboveWavePaint.setColor(mAboveWaveColor);
        mAboveWavePaint.setAlpha(DEFAULT_ABOVE_WAVE_ALPHA);
        mAboveWavePaint.setStyle(Paint.Style.FILL);
        mAboveWavePaint.setAntiAlias(true);

        mBlowWavePaint.setColor(mBlowWaveColor);
        mBlowWavePaint.setAlpha(DEFAULT_BLOW_WAVE_ALPHA);
        mBlowWavePaint.setStyle(Paint.Style.FILL);
        mBlowWavePaint.setAntiAlias(true);
    }

    private void startWave() {
        if (getWidth() != 0) {
            int width = getWidth();
            mWaveLength = width * mWaveMultiple;
            mLeft = getLeft();
            mRight = getRight();
            mBottom = getBottom() + 2;
            mMaxRight = mRight + X_SPACE;
            mOmega = 2 * Math.PI / mWaveLength;
        }
    }

    private void getWaveOffset() {
        if (mBlowOffset > Float.MAX_VALUE - 100) {
            mBlowOffset = 0;
        } else {
            mBlowOffset += mWaveHz;
        }

        if (mAboveOffset > Float.MAX_VALUE - 100) {
            mAboveOffset = 0;
        } else {
            mAboveOffset += mWaveHz;
        }
    }

    /**
     * 计算波浪路径
     */
    private void calculatePath() {
        mAbovePath.reset();
        mBlowPath.reset();

        getWaveOffset();

        float y = 0.f ;
        mAbovePath.moveTo(mLeft, mBottom);
        for (int x = 0; x < mMaxRight; x += X_SPACE) {
            y = (float) (mWaveHeight * Math.sin(mOmega * x + mAboveOffset) + mWaveHeight) ;
            mAbovePath.lineTo(x, y);
        }
        mAbovePath.lineTo(mRight, mBottom);

        mBlowPath.moveTo(mLeft, mBottom);
        for (int x = 0; x < mMaxRight; x += X_SPACE) {
            y = (float) (mWaveHeight * Math.sin(mOmega * x + mBlowOffset) + mWaveHeight) ;
            mBlowPath.lineTo(x, y);
        }
        mBlowPath.lineTo(mRight, mBottom);

    }

    private class RefreshProgressRunnable implements Runnable {
        @Override
        public void run() {
            long start = System.currentTimeMillis() ;

            calculatePath();
            invalidate() ;

            long end = System.currentTimeMillis() ;
            long gap = 16 - (end - start) ;

            postDelayed(this, gap > 0 ? gap : 0) ;

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            if (mWaveLength == 0) {
                startWave();
            }
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (getVisibility() == GONE) {
            removeCallbacks(mRefreshProgressRunnable) ;
        } else {
            removeCallbacks(mRefreshProgressRunnable) ;
            mRefreshProgressRunnable = new RefreshProgressRunnable() ;
            post(mRefreshProgressRunnable);
        }
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mBlowPath, mBlowWavePaint) ;
        canvas.drawPath(mAbovePath, mAboveWavePaint);
    }

    public Paint getmBlowWavePaint() {
        return mBlowWavePaint;
    }

    public void setmBlowWavePaint(Paint mBlowWavePaint) {
        this.mBlowWavePaint = mBlowWavePaint;
    }

    public Paint getmAboveWavePaint() {
        return mAboveWavePaint;
    }

    public void setmAboveWavePaint(Paint mAboveWavePaint) {
        this.mAboveWavePaint = mAboveWavePaint;
    }
}
