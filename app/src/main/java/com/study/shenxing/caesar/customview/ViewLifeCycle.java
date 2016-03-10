package com.study.shenxing.caesar.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by shenxing on 16-2-29.
 * 验证view的生命周期方法回调问题
 *
 * 结论是： constructor -> onFinishInflate -> onMeasure -> onSizeChanged -> onLayout -> onDraw
 * 注意，其中onMeasure和onLayout会回调多次
 */
public class ViewLifeCycle extends View {
    private static final String TAG = "shenxing" ;
    public ViewLifeCycle(Context context) {
        super(context);
        Log.i(TAG, "ViewLifeCycle(Context context)") ;
    }

    public ViewLifeCycle(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "ViewLifeCycle(Context context, AttributeSet attrs)") ;
    }

    public ViewLifeCycle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i(TAG, "ViewLifeCycle(Context context, AttributeSet attrs, int defStyleAttr)") ;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i(TAG, "onFinishInflate") ;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "onLayout") ;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure") ;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged") ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path() ;
        path.reset();
        path.addRoundRect(0f, 0f, 200f, 200f, 50f, 50f, Path.Direction.CW);
        canvas.clipPath(path) ;
        super.onDraw(canvas);
        Log.i(TAG, "onDraw") ;
        canvas.drawText("hello world", 100, 100, new Paint());
    }
}
