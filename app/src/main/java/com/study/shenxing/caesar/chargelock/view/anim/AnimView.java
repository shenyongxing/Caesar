package com.study.shenxing.caesar.chargelock.view.anim;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.study.shenxing.caesar.chargelock.view.anim.AnimScene.* ;

/**
 * 用于动画绘图的View(主线程绘图)<br>
 * 
 * @author laojiale
 * 
 */
public class AnimView extends View implements AnimDrawView {

	@SuppressWarnings("unused")
	private final static String LOG_TAG = "AnimView";

	private AnimScene mContext;
	private AnimClock mAnimaClock;

	private boolean mIsStop = true;
	private boolean mIsPause = true;

	private boolean mHadLayout = false;

	private Handler mDrawThreadHandler;

	private final static int FPS = 30;
	private long mIntervalTime = 1000 / FPS;
	private volatile long mLastDrawTime;

	public AnimView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public AnimView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public AnimView(Context context) {
		super(context);
		init();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		stop();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mHadLayout = true;
		start();
	}

	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
		if (visibility == View.VISIBLE) {
			if (mHadLayout) {
				start();
			}
		} else {
			pause();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (null == mContext) {
			return;
		}
		final AnimClock animaClock = mAnimaClock;
		animaClock.tickFrame();
		mContext.start();
		final long currentTime = animaClock.getCurrentTime();
		final long deltaTime = animaClock.getDeltaTime();
		final int width = getWidth();
		final int height = getHeight();
		final RootLayer rootLayer = mContext.getRootLayer();

		long start = System.currentTimeMillis() ;
		rootLayer.checkDrawRectChanged(width, height);
		rootLayer.drawFrame(canvas, width, height, currentTime, deltaTime);
		long end = System.currentTimeMillis();
		Log.i("shenxing", "drawTime : " + (end - start) + ", frame time : " + (end - mLastDrawTime)) ;
		mLastDrawTime = System.currentTimeMillis();
	}

	@Override
	public void onDestroy() {
		stop();
	}

	private void init() {
		mAnimaClock = new AutoFrameClock();
	}
	
	@Override
	public void setAnimaClock(AnimClock clock) {
		mAnimaClock = clock;
	}

	/**
	 * 在AnimSurfaceView初始化后调用, 设置对应的AnimScene
	 * 
	 * @param context
	 */
	@Override
	public void setAnimScene(AnimScene context) {
		mContext = context;
		mContext.setDrawView(this);
	}

	@Override
	public void setFPS(int fps) {
		if (fps < 1) {
			fps = FPS;
		}
		mIntervalTime = 1000 / fps;
	}

	@Override
	public void setAnimTimeScale(float scale) {
		mAnimaClock.setTimeScale(scale);
	}

	/**
	 * start Render thread
	 */
	protected void start() {
		if (null == mContext) {
			return;
		}
		if (mIsStop) {
			mIsStop = false;
			mAnimaClock.reset();
			mDrawThreadHandler = new Handler(Looper.getMainLooper());
			mContext.initDrawThreadHandler(Looper.getMainLooper());
		}
		if (mIsPause) {
			mIsPause = false;
			mAnimaClock.start();
			mDrawThreadHandler.post(mUpdateFrame);
		}
	}

	/**
	 * stop Render thread
	 */
	private void stop() {
		if (mIsStop) {
			return;
		}
		mIsStop = true;
	}

	private void pause() {
		if (mIsStop) {
			return;
		}
		if (mIsPause) {
			return;
		}
		mAnimaClock.pause();
		mIsPause = true;
	}
	
	private final Runnable mUpdateFrame = new Runnable() {

		@Override
		public void run() {
			if (mIsStop) {
				mContext.stop();
				return;
			}

			long passTime = System.currentTimeMillis() - mLastDrawTime;

			invalidate();

			final long delayTime = mIntervalTime - passTime;
			// Loger.d(LOG_TAG, "delayTime: " + delayTime);

			if (!mIsPause) {
				if (delayTime > 0) {
					mDrawThreadHandler.postDelayed(mUpdateFrame, delayTime);
				} else {
					mDrawThreadHandler.post(mUpdateFrame);
				}
			}
		}

	};
	
	public AnimScene getAnimScene() {
		return mContext;
	}

	@Override
	public void onPause() {
		pause();
		stop();
	}

	@Override
	public void onResume() {
		start();
	}
	
}
