package com.study.shenxing.caesar.chargelock.view.anim;

/**
 * mark interface as AnimDrawView
 * 
 * @author laojiale
 * 
 */
public interface AnimDrawView {

	/**
	 * 在AnimSurfaceView初始化后调用, 设置对应的AnimScene.<br>
	 * 
	 * @param context
	 */
	public void setAnimScene(AnimScene context);

	/**
	 * 设置帧频.<br>
	 * 
	 * @param fps
	 */
	public void setFPS(int fps);
	
	/**
	 * 设置动画的时间维度流逝的缩放量.<br>
	 * @param scale
	 */
	public void setAnimTimeScale(float scale);

	/**
	 * 执行销毁
	 */
	public void onDestroy();
	
	/**
	 * 执行onPause
	 */
	public void onPause();
	
	/**
	 * 执行onResume
	 */
	public void onResume();
	
	/**
	 * 设置动画时钟
	 * @param clock
	 */
	public void setAnimaClock(AnimClock clock);

}
