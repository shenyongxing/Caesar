package com.study.shenxing.caesar.pathanimation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.study.shenxing.caesar.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author shenxing
 * @Date 2017/9/26
 * @Email shen.xing@zyxr.com
 * @Description
 */
public class Target {
    public static final int DURATION = 10000;
    private float mLoc[] = new float[2];

    private Path mPath; // 每个target对应的path对象

    private Drawable mDrawable;

    private Paint mPaint;
    private PathMeasure mPathMeasure = new PathMeasure();
    private View mHostView;
    /**
     * @param context
     * @param width 屏幕宽度
     * @param height 屏幕高度
     */
    public Target(View host, Context context, int width, int height, int range) {
        mDrawable = context.getResources().getDrawable(R.drawable.hot_word_search_next_batch);
        mWidth = width;
        mHeight = height;
        mRange = range;
        initPaint();
        buildPath();
        mHostView = host;
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1.0f);
    }

    private int mControlPoint = 10;     // 曲线高度个数分割

    private int mWidth;     // 屏幕宽度
    private int mHeight;    // 屏幕高度

    /**
     * 绘制
     * @param canvas
     */
    public void draw(Canvas canvas) {
        mDrawable.draw(canvas);
    }

    /**
     * 更新位置
     * @param fractionValue 取值0-1
     */
    public void update(float fractionValue) {
        int currlen = (int) (fractionValue * mPathMeasure.getLength());
        mPathMeasure.getPosTan(currlen, mLoc, null);
        mDrawable.setBounds((int) (mLoc[0] - 50), (int) mLoc[1], (int) (mLoc[0] + 50), (int) (mLoc[1] + 100));
        mHostView.invalidate();
    }

    private int mRange; // 曲线左右宽度的随机幅度
    private float mIntensity = 0.2f;     // 曲度

    /**
     * 构建路径
     */
    private void buildPath() {
        mPath = new Path();
        int min = mWidth / 4;
        int max = mWidth * 3 / 4;
        // 将path限制在min和max的宽度区域内
        Random random = new Random();
        int sx = random.nextInt(max) % (max - min + 1) + min;   // 起始点，确保x落在min和max之间
        int sy = 0;
        mDrawable.setBounds(sx - 50 , sy, sx + 50, sy + 100);
        List<Point> pointList = new ArrayList<>();
        pointList.add(new Point(sx, sy));
        for (int i = 1; i < mControlPoint; i++) {
            int tmpX;
            if (random.nextInt(100) % 2 == 0) {
                tmpX = sx + random.nextInt(mRange);
            } else {
                tmpX = sx - random.nextInt(mRange);
            }
            int tmpY = (int) (1.0f * i / mControlPoint * mHeight);
            pointList.add(new Point(tmpX, tmpY));
        }

        for (int i = 0; i < pointList.size(); i++) {
            Point point = pointList.get(i);
            if (i == 0) {
                Point nextPoint = pointList.get(i + 1);
                point.dx = (nextPoint.x - point.x) * mIntensity;
                point.dy = (nextPoint.y - point.y) * mIntensity;
            } else if (i == pointList.size() - 1) {
                Point prevPoint = pointList.get(i - 1);
                point.dx = (point.x - prevPoint.x) * mIntensity;
                point.dy = (point.y - prevPoint.y) * mIntensity;
            } else {
                Point nextPoint = pointList.get(i + 1);
                Point prevPoint = pointList.get(i - 1);
                point.dx = (nextPoint.x - prevPoint.x) * mIntensity;
                point.dy = (nextPoint.y - prevPoint.y) * mIntensity;
            }

            if (i == 0) {
                mPath.moveTo(point.x, point.y);
            } else {
                Point prevPoint = pointList.get(i - 1);
                mPath.cubicTo(prevPoint.x + prevPoint.dx, prevPoint.y + prevPoint.dy, point.x - point.dx, point.y - point.dy, point.x, point.y);
            }
        }
        mPathMeasure.setPath(mPath, false);
    }

    /**
     * 点
     */
    public class Point {
        public float x;
        public float y;

        public float dx;
        public float dy;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
