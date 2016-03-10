package com.study.shenxing.caesar.chargelock.view.anim;

import android.graphics.Canvas;

/**
 * 用于绘制动画的层<br>
 * 
 * @author laojiale
 * 
 */
public abstract class AnimLayer {

	protected AnimScene mContext;
	private int mSceneWidth = -1;
	private int mSceneHeight = -1;
	private boolean mIsVisible = true;
	@SuppressWarnings("unused")
	private boolean mIsAttach = false;

	public AnimLayer(AnimScene context) {
		mContext = context;
	}

	void detach() {
		mIsAttach = false;
	}

	void attach() {
		mSceneWidth = -1;
		mSceneHeight = -1;
		mIsAttach = true;
	}

	/**
	 * 检查绘图区域大小是否变化.<br>
	 * 在绘图线程中运行.<br>
	 * 
	 * @param sceneWidth
	 * @param sceneHeight
	 */
	void checkDrawRectChanged(int sceneWidth, int sceneHeight) {
		if (sceneWidth != mSceneWidth || sceneHeight != mSceneHeight) {
			mSceneWidth = sceneWidth;
			mSceneHeight = sceneHeight;
			onDrawRectChanged(mSceneWidth, mSceneHeight);
		}
	}

	/**
	 * 获取场景的宽度.<br>
	 * 
	 * @return
	 */
	protected int getSceneWidth() {
		return mSceneWidth;
	}

	/**
	 * 获取场景的高度.<br>
	 * 
	 * @return
	 */
	protected int getSceneHeight() {
		return mSceneHeight;
	}

	/**
	 * 绘图区域大小改变时回调.至少会回调一次, 第一次是大小初始化完成.<br>
	 * 在绘图线程中运行.<br>
	 * 
	 * @param sceneWidth
	 * @param sceneHeight
	 */
	protected void onDrawRectChanged(int sceneWidth, int sceneHeight) {

	}

	/**
	 * 更新绘制一帧<br>
	 * 在绘图线程中运行.<br>
	 * 
	 * @param canvas
	 * @param sceneWidth
	 *            可视区域宽度
	 * @param sceneHeight
	 *            可视区域高度
	 * @param currentTime
	 *            流逝的总时间
	 * @param deltaTime
	 *            自上一帧到现在流逝的时间
	 */
	protected abstract void drawFrame(Canvas canvas, int sceneWidth,
			int sceneHeight, long currentTime, long deltaTime);

	public boolean isVisible() {
		return mIsVisible;
	}

	public void setIsVisible(boolean isVisible) {
		this.mIsVisible = isVisible;
	}

}
