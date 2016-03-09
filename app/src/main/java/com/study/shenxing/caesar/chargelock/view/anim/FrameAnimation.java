package com.study.shenxing.caesar.chargelock.view.anim;


/**
 * 帧动画<br>
 * 
 * @author laojiale
 * 
 */
public class FrameAnimation {

	private final int mFrameCount;
	private int mFPS = 20;
	private int mCurrentFrame = 0;

	public FrameAnimation(int frameCount) {
		if (frameCount < 1) {
			throw new IllegalArgumentException("why you do this...");
		}
		mFrameCount = frameCount;
	}

	public void setFPS(int fps) {
		mFPS = fps;
	}

	/**
	 * 总帧数<br>
	 * 
	 * @return
	 */
	public int getFrameCount() {
		return mFrameCount;
	}

	/**
	 * 获取当前帧.(0 base)<br>
	 * 
	 * @return
	 */
	public int getCurrentFrame(long currentTime) {
		mCurrentFrame = (int) ((currentTime * mFPS / 1000) % mFrameCount);
		return mCurrentFrame;
	}

}
