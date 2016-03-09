package com.study.shenxing.caesar.chargelock.view.anim;

/**
 * 动画时间维计时时钟.<br>
 * @author laojiale
 *
 */
public interface AnimClock {
	
	/**
	 * 时钟启动(首次启动或暂停后再开始)<br>
	 */
	public void start();
	
	/**
	 * 暂停流逝<br>
	 */
	public void pause();
	
	/**
	 * 重置, 将所有状态还原到初始态<br>
	 */
	public void reset();
	
	/**
	 * 返回相邻两帧之间流逝的时间<br>
	 * 在{@link #tickFrame()} 后获取其实时值
	 * @return
	 */
	public long getDeltaTime();
	
	/**
	 * 返回自计时开始({@link #start()})后流逝的总时间<br>
	 * 在{@link #tickFrame()} 后获取其实时值
	 * @return
	 */
	public long getCurrentTime();
	
	/**
	 * 每执行一帧时调用<br>
	 */
	public void tickFrame();
	
	/**
	 * 设置时间流逝的缩放
	 * @param scale
	 */
	public void setTimeScale(float scale);

}
