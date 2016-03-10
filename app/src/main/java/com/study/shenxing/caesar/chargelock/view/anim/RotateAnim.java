package com.study.shenxing.caesar.chargelock.view.anim;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 简易旋转动画.<br>
 * 
 * @author laojiale
 * 
 */
public class RotateAnim extends Animation {

	private float mStartAngle;
	private float mDeltaAngle;

	private float mCurrentAngle;

	private float mPivotX;
	private float mPivotY;

	public RotateAnim(float pivotX, float pivotY, float startAngle,
			float deltaAngle) {
		super();
		this.mStartAngle = startAngle;
		this.mDeltaAngle = deltaAngle;
		this.mPivotX = pivotX;
		this.mPivotY = pivotY;
		reset();
	}

	public RotateAnim(float pivotX, float pivotY, float deltaAngle) {
		this(pivotX, pivotY, 0, deltaAngle);
	}

	public float getCurrentAngle() {
		return mCurrentAngle;
	}

	@Override
	public void reset() {
		super.reset();
		mCurrentAngle = mStartAngle;
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		mCurrentAngle = mStartAngle + mDeltaAngle * interpolatedTime;
		if (t != null) {
			float scale = 1.0f;
			if (mPivotX == 0.0f && mPivotY == 0.0f) {
				t.getMatrix().setRotate(mCurrentAngle);
			} else {
				t.getMatrix().setRotate(mCurrentAngle, mPivotX * scale,
						mPivotY * scale);
			}
		}
	}

}
