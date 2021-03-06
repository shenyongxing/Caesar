package com.study.shenxing.caesar.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 绘制工具类
 * @author luopeihuan
 *
 */
public class DrawUtils {

	public static float sDensity = 1.0f;
	public static int sWidthPixels;
	public static int sHeightPixels;
	public static float sFontDensity;

	public static int sRealWidthPixels;
	public static int sRealHeightPixels;
	private static Class sClass = null;
	public static int sNavBarWidth; // 虚拟键宽度
	public static int sNavBarHeight; // 虚拟键高度
	public static final int NAVBAR_LOCATION_RIGHT = 1;
	public static final int NAVBAR_LOCATION_BOTTOM = 2;
	public static int sNavBarLocation;

	// 在某些机子上存在不同的density值，所以增加虚拟值
	public static float sVirtualDensity = -1;
	
	// 点击的最大识别距离，超过即认为是移动
	public static int sTouchSlop = 15;  //CHECKSTYLE IGNORE
	
	/**
	 * 屏幕现实占用比
	 * 屏幕区域 ／ (屏幕区域 ＋ 导航栏区域)
	 */
	public static float sDisplayRate;
	
	/**
	 * dip/dp转像素
	 * @param dipValue dip或 dp大小
	 * @return 像素值
	 */
	public static int dip2px(float dipVlue) {
		return (int) (dipVlue * sDensity + 0.5f); //CHECKSTYLE IGNORE
	}

	/**
	 * 像素转dip/dp
	 * @param pxValue 像素大小
	 * @return dip值
	 */
	public static int px2dip(float pxValue) {
		final float scale = sDensity;
		return (int) (pxValue / scale + 0.5f); //CHECKSTYLE IGNORE
	}

	/**
	 * sp 转 px
	 * @param spValue sp大小
	 * @return 像素值
	 */
	public static int sp2px(float spValue) {
		final float scale = sDensity;
		return (int) (scale * spValue);
	}

	/**
	 * px转sp
	 * @param pxValue 像素大小
	 * @return sp值
	 */
	public static int px2sp(float pxValue) {
		final float scale = sDensity;
		return (int) (pxValue / scale);
	}

	public static void resetDensity(Context context) {
		if (context != null && null != context.getResources()) {
			DisplayMetrics metrics = context.getResources().getDisplayMetrics();
			sDensity = metrics.density;
			sFontDensity = metrics.scaledDensity;
			sWidthPixels = metrics.widthPixels;
			sHeightPixels = metrics.heightPixels;
			initVirtureDensity(context.getResources());
			resetNavBarHeight(context);
		}
	}
	
	private static void initVirtureDensity(Resources resources) {
		Resources res = new Resources(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
		try {
			Field field = Resources.class.getDeclaredField("mCompatibilityInfo");
			if (field == null) {
				return;
			}
			field.setAccessible(true);
			Object object = field.get(resources);
			if (null != object) {
				field.set(res, object);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		final DisplayMetrics metrics = resources.getDisplayMetrics();
		final DisplayMetrics localMetrics = res.getDisplayMetrics();
		if (metrics.density != localMetrics.density) {
			sVirtualDensity = localMetrics.density;
		}
	}

	private static void resetNavBarHeight(Context context) {
		if (context != null) {
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			try {
				if (sClass == null) {
					sClass = Class.forName("android.view.Display");
				}
				Point realSize = new Point();
				Method method = sClass.getMethod("getRealSize", Point.class);
				method.invoke(display, realSize);
				sRealWidthPixels = realSize.x;
				sRealHeightPixels = realSize.y;
				sNavBarWidth = realSize.x - sWidthPixels;
				sNavBarHeight = realSize.y - sHeightPixels;
			} catch (Exception e) {
				sRealWidthPixels = sWidthPixels;
				sRealHeightPixels = sHeightPixels;
				sNavBarHeight = 0;
			}
		}
		sNavBarLocation = getNavBarLocation();
	}

	public static int getNavBarLocation() {
		if (sRealWidthPixels > sWidthPixels) {
			return NAVBAR_LOCATION_RIGHT;
		}
		return NAVBAR_LOCATION_BOTTOM;
	}

}