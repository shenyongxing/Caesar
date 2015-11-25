package com.study.shenxing.caesar.roundimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆角ImageView
 * @author sx
 * 来源:学习自eoeandroid
 * 备注:经过实验得知,在使用时必须要设置android:scaleType,否则不会出现圆角.
 *     // 后来知道,如果不设置scaleType时,若将round值设置得非常大的时候也会出现,但是圆角不太美观
 * 	   同时,利用ShapeDrawable作为background是 不能 实现圆角图片的.
 */
public class RoundImageView extends ImageView {
	public RoundImageView(Context context) {
		super(context);
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	private Paint mPaint = new Paint() ; {
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setColor(Color.GRAY);
		mPaint.setAntiAlias(true); 
	}
	private Canvas mMyCanvas = new Canvas() ;
	private Canvas mCanvasRound = new Canvas() ;
	private RectF mRect = null ;
	private float mRound = 60 ; // 圆角值
	private PorterDuffXfermode mSrc = new PorterDuffXfermode(PorterDuff.Mode.SRC) ;
	private PorterDuffXfermode mSrc_in = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN) ;
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mRect = new RectF(0, 0, w, h) ;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		/**
		 * 步骤可以理解为如下:
		 * 1.将ImageView的内容画在bitmap上
		 * 2.在另外一个画布上,以bitmap1为底,然后在该画布上绘制圆角矩形  
		 * 3.将Iamge的内容,即bitmap以mSrc_in的方式绘制到另外的画布上
		 * 4.将另外一个画布的内容,即bitmap1绘制到系统的canvas上,从而在IamgeView中显示出来
		 * 总结:利用此思路可以实现其他的形状,或者在其他控件上实现此效果,注意发散思考.
		 */
		if (mRect == null) {
			mRect = new RectF(0, 0, getWidth(), getHeight()) ;
		}
		Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        mMyCanvas.setBitmap(bitmap);
        // 将ImageView的src内容绘制到bitmap中
        super.onDraw(mMyCanvas);
        
        Bitmap bitmap1= Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvasRound.setBitmap(bitmap1);
        
        mPaint.setXfermode(mSrc) ;
        // 以SRC方式绘制圆角矩形,即当前绘画的若与屏幕上的内容有交集,则显示当前要绘制的内容
        mCanvasRound.drawRoundRect(mRect, mRound, mRound, mPaint);
        mPaint.setXfermode(mSrc_in) ;
        // 以SRC_IN方式绘制bitmap,即当前绘制的若与屏幕有交集,则交集部分显示当前要绘制的图片相应部分,其余部分丢弃
        // 下面两行代码是获取圆角图片
        mCanvasRound.drawBitmap(bitmap, 0, 0, mPaint);
        mPaint.setXfermode(null) ;
        
        // 最后将带有图片内容的圆角bitmap绘制到画布
        canvas.drawBitmap(bitmap1, 0, 0, null);
        
        bitmap1.recycle();
        bitmap1 = null ;
        bitmap.recycle();
        bitmap = null ;
	}
	
	public void setRound(float round) {
		mRound = round ;
	}
}