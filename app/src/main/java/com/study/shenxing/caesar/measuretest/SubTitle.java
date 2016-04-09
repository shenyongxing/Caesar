package com.study.shenxing.caesar.measuretest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by shenxing on 16/4/8.
 */
public class SubTitle extends TextView {
    public SubTitle(Context context) {
        super(context);
    }

    public SubTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SubTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Printer.printMeasureInfo("SubTitle", widthMeasureSpec, heightMeasureSpec);
    }
}
