package com.study.shenxing.caesar.unsorted;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 广告ImageView
 * Created by sx on 15-8-27.
 */
public class AdImageView extends ImageView {
    private float mScale = 1.0f * 630 / 354 ; // 保持一定的宽高比
    public AdImageView(Context context) {
        super(context);
    }

    public AdImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec) ;
        int heightSize = (int) (widthSize * mScale) ;

        // 保持该ImageView的宽高比
        setMeasuredDimension(widthSize, heightSize);
    }

    public void setmScale(float scale) {
        mScale = scale ;
        requestLayout();
    }
}
