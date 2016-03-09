package com.study.shenxing.caesar.chargelock.view.anim;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Context to do the animation job.<br>
 * 
 * @author laojiale
 * 
 */
public abstract class AnimScene extends ContextWrapper {

	private Handler mDrawThreadHandler;
	private final RootLayer mRootLayer;
	private AnimLayer mContentLayer;
	private View mDrawView;

	private volatile boolean mIsStart = false;

	private final List<Runnable> mPendingRunnables = new ArrayList<Runnable>();

	private AnimSceneCallback mAnimSceneCallback;

	private Handler mHandler = new Handler() ;

	public AnimScene(Context base) {
		super(base);
		mRootLayer = new RootLayer(this);
	}

	/**
	 * 设置使用于绘图的view
	 * 
	 * @param view
	 */
	void setDrawView(View view) {
		if (!AnimDrawView.class.isInstance(view)) {
			throw new IllegalAccessError("view must be AnimDrawView");
		}
		mDrawView = view;
	}

	protected AnimDrawView getDrawView() {
		return (AnimDrawView) mDrawView;
	}

	/**
	 * 初始化mDrawThreadHandler
	 * 
	 * @param drawThreadLooper
	 */
	void initDrawThreadHandler(Looper drawThreadLooper) {
		mDrawThreadHandler = new Handler(drawThreadLooper);
	}

	/**
	 * get the RootLayer.<br>
	 * 
	 * @return
	 */
	RootLayer getRootLayer() {
		return mRootLayer;
	}

	/**
	 * 获取场景的宽.<br>
	 * 在 {@link #onStart()}回调用后才有效.<br>
	 * 
	 * @return
	 */
	int getSceneWidth() {
		return mDrawView.getWidth();
	}

	/**
	 * 获取场境的高.<br>
	 * 在 {@link #onStart()}回调用后才有效.<br>
	 * 
	 * @return
	 */
	int getSceneHeight() {
		return mDrawView.getHeight();
	}

	/**
	 * 设置内容视图.<br>
	 * 在 {@link #onStart()} 中调用.<br>
	 */
	protected final void setContentLayer(AnimLayer layer) {
		if (mContentLayer != null) {
			mRootLayer.removeAnimaLayer(mContentLayer);
		}
		mContentLayer = layer;
		mRootLayer.addAnimaLayer(mContentLayer, 0);
	}

	final boolean isStart() {
		return mIsStart;
	}

	final void start() {
		if (!mIsStart) {
			mIsStart = true;
			runPendingRunnables();
			onStart();
			notifyOnStart();
		}
	}

	final void stop() {
		if (mIsStart) {
			mIsStart = false;
			onStop();
			notifyOnStop();
		}
	}

	private void notifyOnStart() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (null != mAnimSceneCallback) {
					mAnimSceneCallback.onAnimSceneStart();
				}
			}
		});
	}

	private void notifyOnStop() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (null != mAnimSceneCallback) {
					mAnimSceneCallback.onAnimSceneStop();
				}
			}
		});
	}

	/**
	 * 开始.<br>
	 * 在绘图线程中运行.<br>
	 */
	protected void onStart() {

	}

	/**
	 * 结束.<br>
	 * 在绘图线程中运行.<br>
	 */
	protected void onStop() {

	}

	/**
	 * post on Draw Thread
	 * 
	 * @param r
	 */
	public void post(Runnable r) {
		if (mIsStart) {
			mDrawThreadHandler.post(r);
		} else {
			mPendingRunnables.add(r);
		}
	}

	public void setAnimSceneCallback(AnimSceneCallback callback) {
		mAnimSceneCallback = callback;
	}

	/**
	 * RootLayer
	 */
	static class RootLayer extends AnimLayerGroup {

		public RootLayer(AnimScene context) {
			super(context);
			attach();
		}
	}

	private void runPendingRunnables() {
		final List<Runnable> pendingRunnables = new ArrayList<Runnable>(
				mPendingRunnables);
		for (Runnable runnable : pendingRunnables) {
			mDrawThreadHandler.post(runnable);
		}
	}

}
