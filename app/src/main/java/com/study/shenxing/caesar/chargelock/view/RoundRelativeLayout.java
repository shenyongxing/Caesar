package com.study.shenxing.caesar.chargelock.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by wangying on 16/1/12.
 */
public class RoundRelativeLayout extends RelativeLayout {

    private Paint mMaskPaint;
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private Bitmap mMaskBitmap;
    private final Paint mLayerPaint = new Paint();

    public RoundRelativeLayout(Context context) {
        this(context, null);
    }

    public RoundRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        mMaskPaint = new Paint();
        mMaskPaint.setAntiAlias(true);
        mMaskPaint.setFilterBitmap(true);
        mMaskPaint.setColor(0xFFFFFFFF);
    }

    private void updateMask() {
		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}
        if (mMaskBitmap != null && !mMaskBitmap.isRecycled()) {
            mMaskBitmap.recycle();
            mMaskBitmap = null;
        }
        try {
            mMaskBitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                    Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError e) {

        }
        if (mMaskBitmap != null) {
            final float density = getResources().getDisplayMetrics().density;
            final float radius = 6; // 6
            final Canvas canvas = new Canvas(mMaskBitmap);
            canvas.drawRoundRect(new RectF(0, 0, mMaskBitmap.getWidth(),
                    mMaskBitmap.getHeight()), radius, radius, mMaskPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateMask();
    }

    @Override
    public void draw(Canvas canvas) {
        if (mMaskBitmap != null) {
            final int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(),
                    mLayerPaint, Canvas.ALL_SAVE_FLAG);
            super.draw(canvas);
            mMaskPaint.setXfermode(mXfermode);
            canvas.drawBitmap(mMaskBitmap, 0, 0, mMaskPaint);
            mMaskPaint.setXfermode(null);
            canvas.restoreToCount(sc);
        } else {
            super.draw(canvas);
        }
    }


}
