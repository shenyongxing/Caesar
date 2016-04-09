package com.study.shenxing.caesar.customview;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shenxing on 16/4/3.
 */
public class CircleBar extends View {
    private Paint mBigCirclePaint ;
    private RectF mBigCircleRectF ;
    private Paint mSmallCirclePaint ;
    public CircleBar(Context context) {
        this(context, null);
    }

    public CircleBar(Context context, AttributeSet attrs) {
        this(context, null, 0) ;
    }

    public CircleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化绘制准备工作
     */
    private void init() {
        mBigCirclePaint = new Paint();
        mBigCirclePaint.setAntiAlias(true);
        mBigCirclePaint.setStyle(Paint.Style.STROKE);
        mBigCirclePaint.setStrokeWidth(1.0f);

        mSmallCirclePaint = new Paint();
        mSmallCirclePaint.setAntiAlias(true);
        mSmallCirclePaint.setStyle(Paint.Style.FILL);
    }

    /**
     * 绘制大圆
     */
    private void drawBigCircle() {

    }

    /**
     * 绘制小圆
     */
    private void drawSamllCircle() {

    }
}
