package com.study.shenxing.caesar.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.study.shenxing.caesar.R;
import com.study.shenxing.caesar.utils.DrawUtils;

/**
 *
 * @Author shenxing
 * @Date 2017/9/30
 * @Email shen.xing@zyxr.com
 * @Description 倒三角指示器
 */
public class TrianglePointer extends View {
    private int mPointerColor = Color.BLUE;

    private Paint mPaint;
    private Path mPath;

    public TrianglePointer(Context context) {
        super(context);
        init(null, 0);
    }

    public TrianglePointer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TrianglePointer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.TrianglePointer, defStyle, 0);

        mPointerColor = a.getColor(
                R.styleable.TrianglePointer_point_color,
                mPointerColor);

        a.recycle();
        initPaint();
        mPath = new Path();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mPointerColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        float x1 = 0f, y1 =0f;
        float x2 = width / 2, y2 = width / 2;
        float x3 = width, y3 = 0f;

        mPath.reset();
        mPath.moveTo(x1, y1);
        mPath.lineTo(x2, y2);
        mPath.lineTo(x3, y3);
        mPath.close();

        canvas.drawPath(mPath, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = DrawUtils.dip2px(20);
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = DrawUtils.dip2px(20);
        }

        setMeasuredDimension(widthSize, heightSize);
    }
}
