package com.study.shenxing.caesar.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by shenxing on 16/7/31.
 * Paint与Canvas api接口使用实践
 */
public class PractiseView extends View {
    private Paint mPaint = new Paint();
    public PractiseView(Context context) {
        super(context);
        initPaint();
    }

    public PractiseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public PractiseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint.setStrokeWidth(2);  // 设置空心画笔的宽度
        mPaint.setTextSize(30); // 设置绘制文字的宽度
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED); // 画笔不设置颜色,则默认是黑色
        mPaint.setStyle(Paint.Style.STROKE);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制阴影层
//        mPaint.setShadowLayer(10, 15, 15, Color.GREEN);
        canvas.drawText("Hello world", 100, 100, mPaint);

        /**
         * 以下是paint.setShadowLayer()的解释, 从中可以看到,对于其他图形的阴影必须要通过软绘制才可以实现.
         * 实践也如此.
         * Can be used to create a blurred shadow underneath text. Support for use
         * with other drawing operations is constrained to the software rendering
         * pipeline.
         */

        // 绘制圆圈
        canvas.drawCircle(200, 200, 100, mPaint);

        // 绘制圆弧
        RectF rect1 = new RectF(100, 10, 300, 100);
        canvas.drawArc(rect1, 0, 90, true, mPaint);

        RectF rect2 = new RectF(400, 10, 600, 100);
        canvas.drawArc(rect2, 0, 90, false, mPaint);

        // 绘制椭圆
        RectF rect = new RectF(300, 600, 600, 1000);
        canvas.drawRect(rect, mPaint);//画矩形

        mPaint.setColor(Color.GREEN);//更改画笔颜色
        canvas.drawOval(rect, mPaint);//同一个矩形画椭圆

        // path绘制图形
        Path path = new Path();
        path.moveTo(200, 200);
        path.lineTo(500, 500);
        path.lineTo(200, 500);
        path.close();
        canvas.drawPath(path, mPaint);

        // clock wise 和 counter clock wise
        Path CCWRectpath = new Path();
        RectF ccw_rect =  new RectF(50, 50, 240, 200);
        CCWRectpath.addRect(ccw_rect, Path.Direction.CCW); // 逆时针

        Path CWRectpath = new Path();
        RectF cw_rect =  new RectF(290, 50, 480, 200);
        CWRectpath.addRect(cw_rect, Path.Direction.CW);

        canvas.drawPath(CCWRectpath, mPaint);
        canvas.drawPath(CWRectpath, mPaint);

        // 根据路径绘制文字
        String testText = "假如生活欺骗了你,不要悲伤,不要心急";
        canvas.drawTextOnPath(testText, CCWRectpath, 0, 20, mPaint);
        canvas.drawTextOnPath(testText, CWRectpath, 0, 20, mPaint);

        // 区域region
        Region region = new Region(350, 350, 600, 600);
        mPaint.setColor(Color.BLUE);
        drawRegion(canvas, region, mPaint);
        Path ovalPath = new Path();
        RectF ovalRectF = new RectF(350, 350, 600, 600);
        ovalPath.addOval(ovalRectF, Path.Direction.CW);
        // path 与 region的交集
        region.setPath(ovalPath, new Region(350, 350, 600, 500));
        drawRegion(canvas, region, mPaint);

        // region的合并操作
        Rect unionRect1 = new Rect(100,100,400,200);
        Rect unionRect2 = new Rect(200,0,300,300);
        //构造一个画笔，画出矩形轮廓
        Paint unionPaint = new Paint();
        unionPaint.setColor(Color.RED);
        unionPaint.setStyle(Paint.Style.STROKE);
        unionPaint.setStrokeWidth(2);

        canvas.drawRect(unionRect1, unionPaint);
        canvas.drawRect(unionRect2, unionPaint);

        Region unionRegion1 = new Region(unionRect1);
        Region unionRegion2 = new Region(unionRect2);
        unionRegion1.op(unionRegion2, Region.Op.XOR);

        Paint paint_fill = new Paint();
        paint_fill.setColor(Color.GREEN);
        paint_fill.setStyle(Paint.Style.FILL);
        drawRegion(canvas, unionRegion1, paint_fill);

//         region裁剪
//        canvas.clipRegion(unionRegion1);

        // translate test
        canvas.drawRect(new RectF(800, 0, 1000, 200), mPaint);
        canvas.save();
        canvas.translate(100, 100);
        canvas.drawRect(new RectF(500, 0, 800, 200), mPaint);
        canvas.drawRect(new RectF(510, 10, 810, 210), mPaint);
        canvas.restore();

        // drawText 实践
        int baseLineX = 0;
        int baseLineY = 1400;
        Paint textpaint = new Paint();
        textpaint.setColor(Color.LTGRAY);
        canvas.drawLine(baseLineX, baseLineY, 1200, baseLineY, textpaint);
        textpaint.setColor(Color.RED);
        textpaint.setTextSize(120);
        textpaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Shenxing", baseLineX, baseLineY, textpaint);

        // 关于fontMetrics的字段含义参看印象笔记
        Paint.FontMetrics fontMetrics = textpaint.getFontMetrics();
        float ascent = fontMetrics.ascent + baseLineY;
        float descent = fontMetrics.descent + baseLineY;
        float top = fontMetrics.top + baseLineY;
        float bottom = fontMetrics.bottom + baseLineY;

        // ascent
        textpaint.setColor(Color.RED);
        canvas.drawLine(baseLineX, ascent, 1200, ascent, textpaint);

        // descent
        textpaint.setColor(Color.YELLOW);
        canvas.drawLine(baseLineX, descent, 1200, descent, textpaint);

        // top
        textpaint.setColor(Color.BLUE);
        canvas.drawLine(baseLineX, top, 1200, top, textpaint);

        // bottom
        textpaint.setColor(Color.BLACK);
        canvas.drawLine(baseLineX, bottom, 1200, bottom, textpaint);

        // baseLine
        textpaint.setColor(Color.RED);
        canvas.drawLine(baseLineX, baseLineY, 1200, baseLineY, textpaint);
    }

    /**
     * 绘制region
     */
    private void drawRegion(Canvas canvas, Region region, Paint paint) {
        RegionIterator iterator = new RegionIterator(region);
        Rect r = new Rect();

        while (iterator.next(r)) {
            canvas.drawRect(r, paint);
        }
    }

}
