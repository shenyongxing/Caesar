package com.study.shenxing.caesar.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by shenxing on 16-2-2.
 */
public class XfermodeView extends View {
    private int mRectSize ;
    private Bitmap mSrc ;
    private Bitmap mDst ;
    private Paint mPaint ;
    public XfermodeView(Context context) {
        super(context);
        init() ;
    }

    public XfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init() ;
    }

    public XfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init() ;
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

    private void init() {
        mPaint = new Paint() ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /***********示例一***************/
        canvas.drawColor(0xffffff00);
        int canvasWidth = canvas.getWidth();
        int r = canvasWidth / 5;
        //绘制黄色的圆形
        mPaint.setColor(0xFFFFCC44);
        canvas.drawCircle(r, r, r, mPaint);
        //绘制蓝色的矩形
        mPaint.setColor(0xFF66AAFF);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)) ;
        canvas.drawRect(r, r, r * 2.5f, r * 2.5f, mPaint);
//        mPaint.setXfermode(null) ;




        /************示例二*******************/


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectSize = w / 4 ;
    }
}
