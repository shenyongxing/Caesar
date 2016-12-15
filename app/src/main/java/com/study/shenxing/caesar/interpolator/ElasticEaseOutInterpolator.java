package com.study.shenxing.caesar.interpolator;

import android.view.animation.Interpolator;

/**
 * @author shenxing
 * @description
 * @date 2016/12/14
 */

public class ElasticEaseOutInterpolator implements Interpolator {

    private static final float PI = (float) Math.PI;
    private static final float PI2 = PI * 2;

    float mAmplitude;
    float mPeriod;
    float mPhase;

    /**
     * @param period	越小则弹的次数越多，弹出部分越多，[0..1]，推荐值0.25
     * @param overshoot	过冲部分的比例，也会受<var>period<var>的限制，推荐值0.25
     */
    public ElasticEaseOutInterpolator(float period, float overshoot) {
			/*
				f(t) = pow(2, -10 * t) * a * sin(pi2 * (t / p - s)) + 1
				0 < p < 1 越小则弹的次数越多，弹出部分越多（横向缩放比例）
				a >= 1 越大弹出部分越大
				令 s = asin(1/a) / pi2 使得 f(他、
				overshoot = pow(2 , -5 * p) * a  （当p=0.3,a=1时，exceed=0.35）
				解得 a = overshoot * pow(2, 5 * p)
			*/

        mPeriod = Math.max(0, Math.min(period, 1));
        mAmplitude = Math.max(1, overshoot * (float) Math.pow(2, 10 * mPeriod * 0.5));	// CHECKSTYLE IGNORE
        mPhase = (float) Math.asin(1 / mAmplitude) / PI2;
    }

    @Override
    public float getInterpolation(float t) {
        if (t <= 0) {
            return 0;
        }
        if (t >= 1) {
            return 1;
        }
        return (float) (Math.pow(2, -10 * t) * mAmplitude * Math.sin(PI2 * (t / mPeriod - mPhase)) + 1);	// CHECKSTYLE IGNORE
    }

}
