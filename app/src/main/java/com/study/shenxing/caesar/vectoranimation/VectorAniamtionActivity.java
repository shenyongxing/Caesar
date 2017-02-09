package com.study.shenxing.caesar.vectoranimation;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.study.shenxing.caesar.R;

/**
 * 矢量动画学习demo
 */
public class VectorAniamtionActivity extends AppCompatActivity {
    public static final String TAG = "sh";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_aniamtion);
    }

    /**
     * image的点击事件 xml中定义的
     * @param v
     */
    public void anim(View v) {
        Log.i(TAG, "anim: ");
        ImageView imageView = (ImageView) v;
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }
}
