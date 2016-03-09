package com.study.shenxing.caesar.chargelock.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * 三角形
 * @author zhanghuijun
 *
 */
public class TriangleView extends View {
	
	private Path mPath = null;
	private Paint mPaint = null;

	public TriangleView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TriangleView(Context context) {
		super(context);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (mPath == null) {
			mPath = new Path();
			mPath.moveTo(0, 0);
			mPath.lineTo(getWidth(), 0);
			mPath.lineTo(getWidth() / 2, getHeight() / 2);
			mPath.close();
			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setColor(Color.BLACK);
		}
		canvas.drawPath(mPath, mPaint);
	}
}
