package com.study.shenxing.caesar.chargelock.view.anim;

/**
 * 基于帧步调的动画计时时钟, 以帧步调作为时间计时, 固定一个帧时间.<br>
 * 运行的结果就是在性能好的机子上动画快,性能机子上动画慢, 但不会跳帧<br>
 * @author laojiale
 *
 */
public class FrameClock implements AnimClock {

	private long mDeltaTime = 16;
	private long mCurrentTime;
	
	private float mTimeScale = 1;

	/**
	 * FrameClock
	 * @param fps 所采用的帧频<br>
	 */
	public FrameClock(int fps) {
		mDeltaTime = 1000 / fps;
	}

	/**
	 * 以常规屏幕刷新帧率作标准,即60FPS<br>
	 * @see #FrameClock(int)
	 */
	public FrameClock() {
		this(60);
	}

	@Override
	public void start() {
		// 依赖于 tickFrame 
	}

	@Override
	public void pause() {
		// 依赖于 tickFrame 
	}

	@Override
	public void reset() {
		mCurrentTime = 0;
	}

	@Override
	public long getDeltaTime() {
		return (long) (mDeltaTime * mTimeScale);
	}

	@Override
	public long getCurrentTime() {
		return mCurrentTime;
	}

	@Override
	public void tickFrame() {
		mCurrentTime = (long) (mCurrentTime + mDeltaTime * mTimeScale);
	}
	
	@Override
	public void setTimeScale(float scale) {
		mTimeScale = scale;
	}

}
