package com.study.shenxing.caesar.chargelock.view.anim;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by wangying on 15/11/26.
 */
public class AlphaAnim extends Animation {

    private float mStartAlpha;
    private float mEndAlpha;

    private float mCurrentAlpha;

    public AlphaAnim(float startAlpha, float endAlpha) {
        super();
        mStartAlpha = startAlpha;
        mEndAlpha = endAlpha;
        reset();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        mCurrentAlpha = mStartAlpha + (mEndAlpha - mStartAlpha) * interpolatedTime;
        if (t != null) {
            t.setAlpha(mCurrentAlpha);
        }
    }

    /**
     * 获取alpha
     *
     * @return
     */
    public float getCurrentAlpha() {
        return mCurrentAlpha;
    }

    @Override
    public void reset() {
        super.reset();
        mCurrentAlpha = mStartAlpha;
    }
}
