package com.study.shenxing.caesar.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by shenxing on 16-2-23.
 */
public class RoundRelativeLayout extends RelativeLayout {
    private Path mPath = new Path() ;
    private RectF mCanvasRect ;
    private boolean mIsPathValid;
    public RoundRelativeLayout(Context context) {
        super(context);
    }

    public RoundRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mCanvasRect == null) {
            mCanvasRect = new RectF(0, 0, getWidth(), getHeight());
        }

        canvas.save();
        canvas.drawColor(0xff00ffff);
        mPath.reset();
        mPath.addRoundRect(mCanvasRect, 50, 50, Path.Direction.CCW);
        mPath.close();
        canvas.clipPath(mPath);
        super.dispatchDraw(canvas);
        drawScene(canvas);
        canvas.restore();
    }

    public void drawScene(Canvas canvas) {
        canvas.drawColor(0xff00ff00);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int oldWidth = getMeasuredWidth();
        int oldHeight = getMeasuredHeight();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int newWidth = getMeasuredWidth();
        int newHeight = getMeasuredHeight();
        if (newWidth != oldWidth || newHeight != oldHeight) {
            mIsPathValid = false;
        }
    }

    private Path getRoundRectPath(float radiusX, float radiusY) {
        if (mIsPathValid) {
            return mPath;
        }
        mPath.reset();

        int width = getWidth();
        int height = getHeight();

        RectF bounds = new RectF(0, 0, width, height);
        mPath.addRoundRect(bounds, radiusX, radiusY, Path.Direction.CW);

        mIsPathValid = true;
        return mPath;
    }
}
