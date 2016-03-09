package com.study.shenxing.caesar.chargelock.view.anim;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 简易平移动画.<br>
 * 
 * @author laojiale
 * 
 */
public class TranslateAnim extends Animation {

	private float mStartX;
	private float mStartY;
	private float mEndX;
	private float mEndY;

	private float mCurrentX;
	private float mCurrentY;

	public TranslateAnim(float startX, float startY, float endX, float endY) {
		super();
		this.mStartX = startX;
		this.mStartY = startY;
		this.mEndX = endX;
		this.mEndY = endY;
		reset();
	}

	public float getCurrentX() {
		return mCurrentX;
	}

	public float getCurrentY() {
		return mCurrentY;
	}

	@Override
	public void reset() {
		super.reset();
		mCurrentX = mStartX;
		mCurrentY = mStartY;
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		mCurrentX = mStartX + (mEndX - mStartX) * interpolatedTime;
		mCurrentY = mStartY + (mEndY - mStartY) * interpolatedTime;
		if (t != null) {
			t.getMatrix().setTranslate(mCurrentX, mCurrentY);
		}
	}

}
