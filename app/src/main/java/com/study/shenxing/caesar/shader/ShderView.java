package com.study.shenxing.caesar.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.study.shenxing.caesar.R;

/**
 * shader demo view
 * Created by shenxing on 16/1/31.
 * 自定义View如不实现onMeasure方法, 那么其宽高都有一个默认值.所以超出了部分的内容则不会显示。 需要注意。
 */
public class ShderView extends View {
    private Drawable mShaderDrawable ;

    private BitmapShader mBitmapShader ;
    private LinearGradient mLinearShader ;
    private RadialGradient mRadialShader ;
    private SweepGradient mSweepShader ;
    private ComposeShader mComposeShader ;
    private LinearGradient mLinearGradient ;
    private Matrix mMatrix ;
    private float mAngle ;
    private float mTranslate ;
    private float mViewWidth ;

    private Paint mPaint ;

    public ShderView(Context context) {
        super(context);
        init();
    }

    public ShderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mShaderDrawable = getResources().getDrawable(R.drawable.optimization_greentake) ;
        Bitmap bitmap = ((BitmapDrawable) mShaderDrawable).getBitmap() ;
        // Shader.TileMode.CLAMP重复延伸边缘的颜色.
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.CLAMP) ;
        mLinearShader = new LinearGradient(0, 0, 1000, 500, new int[]{Color.RED, Color.GREEN, Color.BLUE}, new float[]{0.5f, 0.2f, 1.0f}, Shader.TileMode.REPEAT);
        mLinearGradient = new LinearGradient(-200, 0, 0, 0,
                new int[] { 0x33ffffff, 0xffffffff, 0x33ffffff },
                new float[] { 0, 0.5f, 1 }, Shader.TileMode.CLAMP);
//        mRadialShader = new RadialGradient(50, 50, 100, new int[]{Color.RED, Color.YELLOW, Color.BLUE}, null, Shader.TileMode.REPEAT);
        mRadialShader = new RadialGradient(500, 500, 500, new int[]{Color.RED, Color.YELLOW, Color.BLUE}, new float[]{0.5f, 0.8f, 1.0f}, Shader.TileMode.REPEAT);
        mSweepShader = new SweepGradient(500, 500, new int[]{Color.RED, Color.BLUE, Color.GREEN}, null);
        mComposeShader = new ComposeShader(mBitmapShader, mLinearShader, PorterDuff.Mode.DST_IN);

        mPaint = new Paint() ;
        mMatrix = new Matrix() ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        mMatrix.setRotate(mAngle, 500, 500);
        mMatrix.setTranslate(mTranslate, 0);
//        mMatrix.setTranslate(-20, -20);
//        mBitmapShader.setLocalMatrix(mMatrix);
        /*mPaint.setShader(mBitmapShader) ;
        canvas.drawRect(new Rect(0, 0, 1000, 500), mPaint);*/
        mLinearGradient.setLocalMatrix(mMatrix);
//        mPaint.setShader(mLinearGradient);
//        mPaint.setTextSize(48);
//        canvas.drawText("Slide to unlock", 100, 100, mPaint);
//        canvas.drawRect(new Rect(0, 0, 1000, 500), mPaint);

        mPaint.setShader(mRadialShader) ;
        canvas.drawRect(new Rect(0, 0, 1000, 1000), mPaint);
        /*mSweepShader.setLocalMatrix(mMatrix);
        mPaint.setShader(mSweepShader);
        canvas.drawRect(new Rect(0, 0, 1000, 1000), mPaint);*/

        /*mPaint.setShader(mComposeShader) ;
        canvas.drawRect(new Rect(0, 1020, 1000, 1200), mPaint);*/
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w ;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHandler.post(mRefresh) ;
    }

    private Handler mHandler = new Handler() ;

    private Runnable mRefresh = new Runnable() {
        @Override
        public void run() {
//            mAngle++ ;
            mTranslate += mViewWidth / 10;
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = -mViewWidth;
            }
            invalidate();
            mHandler.postDelayed(this, 10) ;
        }
    } ;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getScreenWidth(), getScreenHeight());
    }

    public int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics() ;
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels ;
    }

    public int getScreenHeight() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics() ;
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels ;
    }
}
