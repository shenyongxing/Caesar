package com.study.shenxing.caesar.chargelock.view.anim;

/**
 * 类似于{@link FrameClock}, 不同的地方在于这个会根据机子实际帧频调整帧部,<br>
 * 以保持在不同性能的机子动画运行速度一致<br>
 * @author laojiale
 *
 */
public class AutoFrameClock implements AnimClock {

	private long mDeltaTime = 16;
	private long mCurrentTime;
	private float mTimeScale = 1;

	private final FrameCounter mFrameCounter = new FrameCounter();

	public AutoFrameClock() {
		mDeltaTime = 16; // 默认以常规屏幕刷新帧率作标准,即60FPS
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
		mFrameCounter.updateFrame();
		int fps = mFrameCounter.getFPS();
		if (fps > 25) { // 限制最低为25
			mDeltaTime = 1000 / fps;
		}
		mCurrentTime = (long) (mCurrentTime + mDeltaTime * mTimeScale);
	}
	
	@Override
	public void setTimeScale(float scale) {
		mTimeScale = scale;
	}

	/**
	 * FrameCounter
	 * @author laojiale
	 *
	 */
	private class FrameCounter {

		int mFrameCount;
		long mStartTime;

		int mFps;

		/**
		 * 在每帧更新时调用<br>
		 */
		void updateFrame() {
			// 如是未开始计时, 或帧时间太长(可能是由于动画暂时停止了,需要忽略这次计数据)则重置开始
			if (mStartTime == 0 || System.currentTimeMillis() - mStartTime >= 1100) { // 暂取不能低于10帧
				mStartTime = System.currentTimeMillis();
				mFrameCount = 0;
			} else {
				mFrameCount++;
				if (System.currentTimeMillis() - mStartTime >= 1000) {
					mFps = mFrameCount;
					mStartTime = System.currentTimeMillis();;
					mFrameCount = 0;
				}
			}
		}

		int getFPS() {
			final int fps = mFps;
			mFps = 0;
			return fps;
		}

	}

}
