package com.study.shenxing.caesar.measuretest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by shenxing on 16/4/8.
 * print measure log
 */
public class Menu extends ImageView {
    public Menu(Context context) {
        super(context);
    }

    public Menu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Menu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Printer.printMeasureInfo("Menu", widthMeasureSpec, heightMeasureSpec);
    }
}
