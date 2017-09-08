package com.study.shenxing.caesar.customview;

import android.content.Context;
import android.icu.util.Measure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author shenxing
 * @description 测试自定义View的的setMeasure与onMeasure()方法。
 * @date 22/04/2017
 */

public class FixedRatioView extends View {
    public static final float mRatio = 0.5f;         // 高/宽 = 0.5

    public FixedRatioView(Context context) {
        super(context);
    }

    public FixedRatioView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedRatioView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        setMeasuredDimension(width, height);
//        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width * mRatio);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }
}
