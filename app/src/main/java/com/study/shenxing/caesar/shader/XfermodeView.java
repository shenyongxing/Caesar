package com.study.shenxing.caesar.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sx on 16-2-2.
 */
public class XfermodeView extends View {
    private int mRectSize ;
    private Bitmap mSrc ;
    private Bitmap mDst ;
    public XfermodeView(Context context) {
        super(context);
    }

    public XfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private static Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm) ;
        Paint paint = new Paint() ;
        paint.setAntiAlias(true);
        paint.setColor(0xff00ff00);
        canvas.drawRect(w / 3, h / 3, w * 19 / 20, h * 19 / 20, paint);
        return bm ;
    }

    private static Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm) ;
        Paint paint = new Paint() ;
        paint.setAntiAlias(true);
        paint.setColor(0xff00ffff);
        canvas.drawOval(new RectF(0, 0, 3 * w / 4, 3 * h / 4), paint);
        return bm ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectSize = w / 4 ;
    }
}
