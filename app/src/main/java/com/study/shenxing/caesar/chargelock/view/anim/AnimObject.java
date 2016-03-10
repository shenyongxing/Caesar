package com.study.shenxing.caesar.chargelock.view.anim;

import android.graphics.RectF;
import android.view.animation.Transformation;

/**
 * 动画物体<br>
 * 
 * @author laojiale
 */
public abstract class AnimObject extends AnimLayer {

	protected final RectF mRectF = new RectF();
	protected float mAngle;
	protected float mAlpha;
	
	protected final Transformation mTransformation = new Transformation();

	public RectF getRectF() {
		return mRectF;
	}

	public float getAngle() {
		return mAngle;
	}

	public void setmAngle(float angle) {
		this.mAngle = angle;
	}

	public float getAlpha() {
		return mAlpha;
	}

	public void setAlpha(float alpha) {
		this.mAlpha = alpha;
	}

	public AnimObject(AnimScene context) {
		super(context);
	}
	
	/**
	 * 动画是否已完成
	 * @return
	 */
	public boolean isEnd() {
		return false;
	}

}
