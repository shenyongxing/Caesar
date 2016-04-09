package com.study.shenxing.caesar.measuretest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by shenxing on 16/4/8.
 */
public class Title extends TextView {
    public Title(Context context) {
        super(context);
    }

    public Title(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Title(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Printer.printMeasureInfo("Title", widthMeasureSpec, heightMeasureSpec);
    }
}
