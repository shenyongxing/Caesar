package com.study.shenxing.caesar.hyperbolicspiral;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.study.shenxing.caesar.R;
import com.study.shenxing.caesar.graphic.animation.CusAnimation;

public class HyperbolicAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mTestImg ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hyperbolic_animation);

        mTestImg = (ImageView) findViewById(R.id.test_image) ;
        mTestImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Animation animation = new HyperbolicSpiralAnimation((float) Math.PI * 2) ;
        animation.setDuration(3000);
        v.startAnimation(animation); ;
    }
}
