package com.study.shenxing.caesar.chargelock.view.anim;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 浮点数值动画.<br>
 * 
 * @author laojiale
 * 
 */
public class FloatAnim extends Animation {

	private float mValue;
	private float mStartValue;
	private float mEndValue;

	public FloatAnim(float startValue, float endValue) {
		super();
		mStartValue = startValue;
		mEndValue = endValue;
		mValue = mStartValue;
	}

	@Override
	public void reset() {
		super.reset();
		mValue = mStartValue;
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		if (interpolatedTime == 1f) {
			mValue = mEndValue;
		} else {
			mValue = mStartValue + (mEndValue - mStartValue) * interpolatedTime;
		}
	}

	public float getValue() {
		return mValue;
	}

}
