package com.study.shenxing.caesar.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.study.shenxing.caesar.R;

/**
 * Created by shenxing on 16/8/7.
 * ColorMatrix api 实践
 *
 * red,    0,     0,    0,    0
 * 0,      green, 0,    0,    0
 * 0,      0,     blue, 0,    0
 * 0,      0,     0,    alpha,0
 */
public class ColorMatrixView extends View {
    private Paint mPaint = new Paint();
    private Bitmap mTestBitmap;
    private float mAngle;
    public ColorMatrixView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ColorMatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ColorMatrixView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mPaint.setAntiAlias(true);
        // 该方法的影响很大,如果没有设置则不会正确的相应的颜色
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mTestBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gallery_photo_3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        float cos = (float) Math.cos(mAngle);
//        float sin = (float) Math.sin(mAngle);
        // 色相旋转矩阵
//        ColorMatrix matrix = new ColorMatrix(new float[] {
//                cos, sin, 0, 0, 0,
//                -sin, cos, 0, 0, 0,
//                0, 0 ,1, 0, 0,
//                0, 0, 0, 1, 0
//        });

        // 黑白矩阵
//        ColorMatrix matrix = new ColorMatrix(new float[] {
//                0.213f, 0.715f, 0.072f, 0, 0,
//                0.213f, 0.715f, 0.072f, 0, 0,
//                0.213f, 0.715f, 0.072f, 0, 0,
//                0,       0,    0, 1, 0,
//        });

        // 色彩翻转矩阵
//        ColorMatrix matrix = new ColorMatrix(new float[] {
//                1, 0, 0, 0, 0,
//                0, 0, 1, 0, 0,  // 蓝色值代替绿色
//                0, 1, 0, 0, 0,  // 绿色值代替蓝色
//                0, 0, 0, 1, 0,
//        });

        // 老相片效果
        ColorMatrix matrix = new ColorMatrix(new float[]{
                1/2f,1/2f,1/2f,0,0,
                1/3f,1/3f,1/3f,0,0,
                1/4f,1/4f,1/4f,0,0,
                0,0,0,1,0
        });


//        mPaint.setARGB(255, 200, 100, 100);
//
//        canvas.drawRect(0, 0, 500, 600, mPaint);
//
//        canvas.translate(550, 0);
//        mPaint.setColorFilter(new ColorMatrixColorFilter(matrix));
//        canvas.drawRect(0, 0, 500, 600, mPaint);


        canvas.drawBitmap(mTestBitmap, null, new Rect(0, 0, 500, 500 * mTestBitmap.getHeight() / mTestBitmap.getWidth()), mPaint);
        canvas.translate(510, 0);
        mPaint.setColorFilter(new ColorMatrixColorFilter(matrix));
        canvas.drawBitmap(mTestBitmap, null, new Rect(0, 0, 500, 500 * mTestBitmap.getHeight() / mTestBitmap.getWidth()), mPaint);
        mPaint.setColorFilter(null);
    }

    public void setAngle(float angle) {
        mAngle = angle;
        invalidate();
    }
}
