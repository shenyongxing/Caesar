package com.study.shenxing.caesar.chargelock.view.anim;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * 包含多个动画层的AnimLayer<br>
 * 
 * @author laojiale
 * 
 */
public class AnimLayerGroup extends AnimLayer {

	private final List<AnimLayer> mAnimaLayers = new ArrayList<AnimLayer>();

	public AnimLayerGroup(AnimScene context) {
		super(context);
	}

	@Override
	void checkDrawRectChanged(int sceneWidth, int sceneHeight) {
		super.checkDrawRectChanged(sceneWidth, sceneHeight);
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			getChildAt(i).checkDrawRectChanged(sceneWidth, sceneHeight);
		}
	}

	@Override
	protected void drawFrame(Canvas canvas, int sceneWidth, int sceneHeight,
			long currentTime, long deltaTime) {
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			final AnimLayer child = getChildAt(i);
			if (child.isVisible()) {
				child.drawFrame(canvas, sceneWidth, sceneHeight, currentTime,
						deltaTime);
			}
		}
	}

	/**
	 * 添加一个视图层到最上层.<br>
	 * 只能在绘图线程中调用.<br>
	 * 
	 * @param layer
	 */
	public void addAnimaLayer(AnimLayer child) {
		addAnimaLayer(child, getChildCount());
	}

	/**
	 * 添加一个视图层到指定深度.<br>
	 * 只能在绘图线程中调用.<br>
	 * 
	 * @param layer
	 * @param 深度
	 *            (0 base)
	 */
	public void addAnimaLayer(final AnimLayer layer, final int index) {
		if (index < 0 || index > getChildCount()) {
			throw new IllegalArgumentException("illegal index");
		}
		if (mAnimaLayers.contains(layer)) {
			throw new IllegalStateException("layer had be added previous");
		}
		mAnimaLayers.add(index, layer);
		layer.attach();
	}

	/**
	 * 移除指定的视图层.<br>
	 * 只能在绘图线程中调用.<br>
	 * 
	 * @param layer
	 */
	public void removeAnimaLayer(AnimLayer layer) {
		mAnimaLayers.remove(layer);
		layer.detach();
	}

	/**
	 * 获取指定的子AnimLayer<br>
	 * 只能在绘图线程中调用.<br>
	 * 
	 * @param index
	 * @return
	 */
	public AnimLayer getChildAt(int index) {
		return mAnimaLayers.get(index);
	}

	/**
	 * 获取当前的子Layer个数.<br>
	 * 只能在绘图线程中调用.<br>
	 * 
	 * @return
	 */
	public int getChildCount() {
		return mAnimaLayers.size();
	}

	/**
	 * 是否存在该子Layer
	 */
	public boolean containLayer(AnimLayer layer) {
		return mAnimaLayers.contains(layer);
	}
}
