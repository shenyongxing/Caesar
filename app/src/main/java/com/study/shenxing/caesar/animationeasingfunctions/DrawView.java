package com.study.shenxing.caesar.animationeasingfunctions;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.study.shenxing.caesar.R;

/**
 * 根据插值动画绘制相应的曲线
 */
public class DrawView extends View {

    private Paint mLinePaint = new Paint();
    private Paint mBackgroundPaint = new Paint();
    private Path mPath = new Path();
    private boolean mIsStart;

    public DrawView(Context context) {
        super(context);
        init(null, 0);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attributeSet, int defStyle) {
        mBackgroundPaint.setColor(Color.WHITE);
        mLinePaint.setColor(Color.rgb(77,83,96));
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth((float) 3.0);
        mLinePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(), mLinePaint);
        canvas.drawPath(mPath, mLinePaint);
    }

    /**
     * @param time 当前动画时刻
     * @param duration 动画时长
     * @param delta 偏移量
     */
    public void drawPoint(float time, float duration, float delta) {
        float x = time / duration * getWidth();
        float y1 = getHeight() + delta;

        if (!mIsStart) {
            mPath.moveTo(x, y1);
            mIsStart = true;
        }
        mPath.lineTo(x, y1);
        invalidate();
    }

    public void clear() {
        mIsStart = false;
        mPath.reset();
        invalidate();
    }
}
