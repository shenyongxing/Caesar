package com.study.shenxing.caesar.graphic;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.study.shenxing.caesar.R;

public class MatrixDemo extends Activity implements View.OnClickListener {
    private ImageView mTestImageview1;
    private ImageView mTestImageview2;
    private ImageView mTestImageview3;
    private ImageView mTestImageview4;
    private ImageView mTestImageview5;
    private ImageView mTestImageview6;
    private ImageView mTestImageview7;
    private ImageView mTestImageview8;
    private ImageView mTestImageview9;
    private ImageView mTestImageview10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_demo);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Log.i("shenxing", "screenWidth : " + dm.widthPixels + ", screenHeight : " + dm.heightPixels);

        mTestImageview1 = (ImageView) findViewById(R.id.testImageView1);
        mTestImageview1.setOnClickListener(this);
        mTestImageview2 = (ImageView) findViewById(R.id.testImageView2);
        mTestImageview3 = (ImageView) findViewById(R.id.testImageView3);
        mTestImageview4 = (ImageView) findViewById(R.id.testImageView4);
        mTestImageview5 = (ImageView) findViewById(R.id.testImageView5);
        mTestImageview6 = (ImageView) findViewById(R.id.testImageView6);
        mTestImageview7 = (ImageView) findViewById(R.id.testImageView7);
        mTestImageview8 = (ImageView) findViewById(R.id.testImageView8);
        mTestImageview9 = (ImageView) findViewById(R.id.testImageView9);
        mTestImageview10 = (ImageView) findViewById(R.id.testImageView10);
        mTestImageview10.getScaleType();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.testImageView1) {
            Toast.makeText(this, "you click imageview", Toast.LENGTH_SHORT).show();

            testTranslate(mTestImageview1);
            testScale(mTestImageview2);
            testRotate(mTestImageview3);
            testSkewX(mTestImageview4);
            testSkewY(mTestImageview5);
            testSkewXY(mTestImageview6);
            testSymmetryX(mTestImageview7);
            testSymmetryY(mTestImageview8);
            testSymmetryXY(mTestImageview9);
        }
    }

    //平移
    private void testTranslate(ImageView imageView){
        Matrix matrix = new Matrix();
        int width = imageView.getWidth() / 2;
        int height = imageView.getHeight() / 2;
        matrix.postTranslate(width, height);
        imageView.setImageMatrix(matrix);
    }

    //围绕图片中心点旋转
    private void testRotate(ImageView imageView){
        Matrix matrix = new Matrix();
        int width = imageView.getWidth() ;
        int height = imageView.getHeight() ;
        matrix.postRotate(45f, width / 2, height / 2);
//        matrix.postTranslate(width, height);
        imageView.setImageMatrix(matrix);
    }

    //缩放
    private void testScale(ImageView imageView) {
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        imageView.setImageMatrix(matrix);
    }

    //水平倾斜  
    private void testSkewX(ImageView imageView) {
        Matrix matrix = new Matrix();
        matrix.setSkew(0.5f, 0);
        imageView.setImageMatrix(matrix);
    }

    // 垂直倾斜  
    private void testSkewY(ImageView imageView) {
        Matrix matrix = new Matrix();
        matrix.setSkew(0, 0.5f);
        imageView.setImageMatrix(matrix);
    }

    // 水平且垂直倾斜  
    private void testSkewXY(ImageView imageView) {
        Matrix matrix = new Matrix();
        matrix.setSkew(0.5f, 0.5f);
        imageView.setImageMatrix(matrix);
    }

    // 水平对称--图片关于X轴对称
    private void testSymmetryX(ImageView imageView) {
        Matrix matrix = new Matrix();
        int height = imageView.getHeight() / 2;
        float matrixValues[] = { 1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f };
        matrix.setValues(matrixValues);
        //若是matrix.postTranslate(0, height);
        //表示将图片上下倒置
        matrix.postTranslate(0, height * 2);
        imageView.setImageMatrix(matrix);
    }

    // 垂直对称--图片关于Y轴对称
    private void testSymmetryY(ImageView imageView) {
        Matrix matrix = new Matrix();
        int width = imageView.getWidth() / 2;
        float matrixValues[] = {-1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f};
        matrix.setValues(matrixValues);
        //若是matrix.postTranslate(width,0);
        //表示将图片左右倒置
        matrix.postTranslate(width * 2, 0);
        imageView.setImageMatrix(matrix);

    }

    // 关于X=Y对称--图片关于X=Y轴对称
    private void testSymmetryXY(ImageView imageView) {
        Matrix matrix = new Matrix();
        int width = imageView.getWidth() / 2;
        int height = imageView.getHeight() / 2;
        float matrixValues[] = { 0f, -1f, 0f, -1f, 0f, 0f, 0f, 0f, 1f };
        matrix.setValues(matrixValues);
        matrix.postTranslate(width + height, width + height);
        imageView.setImageMatrix(matrix);
    }
}
