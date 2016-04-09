package com.study.shenxing.caesar.measuretest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.study.shenxing.caesar.others.Consts;

/**
 * Created by shenxing on 16/4/8.
 * print measure log
 */
public class ProfilePhoto extends ImageView {
    public ProfilePhoto(Context context) {
        super(context);
    }

    public ProfilePhoto(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProfilePhoto(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Printer.printMeasureInfo("ProfilePhoto", widthMeasureSpec, heightMeasureSpec);
    }
}
