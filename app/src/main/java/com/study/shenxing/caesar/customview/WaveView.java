package com.study.shenxing.caesar.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

/**
 * Created by shenxing on 16/7/31.
 * Path绘制波浪实践
 */
public class WaveView extends View {
    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private float mLevel = 300;
    private int mWaveLength = 400;
    private int mHalfWaveLength = mWaveLength >> 1;
    private int mAmplitude = 50; // 波高
    private int mOffsetX;
    public WaveView(Context context) {
        super(context);
        initPaint();
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint.setStrokeWidth(2);  // 设置空心画笔的宽度
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED); // 画笔不设置颜色,则默认是黑色
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        createWavePath();
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 创建波浪path
     * path.rQuadTo()其中的值是dx,dy即相对于前一个坐标的增量.
     */
    private void createWavePath() {
        mPath.reset();
        mPath.moveTo(-mWaveLength + mOffsetX, mLevel);
        for (int i = -mWaveLength; i < getWidth() + mWaveLength; i += mWaveLength) {
            mPath.rQuadTo(mHalfWaveLength / 2, mAmplitude, mHalfWaveLength, 0);
            mPath.rQuadTo(mHalfWaveLength / 2, -mAmplitude, mHalfWaveLength, 0);
        }

        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();
    }

    /**
     * 开始波浪动画
     */
    public void startWave() {
        ValueAnimator va = ValueAnimator.ofInt(0, mWaveLength);
        va.setDuration(1000);
        va.setRepeatCount(Animation.INFINITE);
        // 需要主动设置线性插值器,否则会有稍微停顿的问题, 默认是加速减速插值器
        va.setInterpolator(new LinearInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffsetX = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        va.start();
    }
}
