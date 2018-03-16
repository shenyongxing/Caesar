package com.study.shenxing.caesar.transition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.shenxing.caesar.R;

public class SceneTransitionsActivity extends AppCompatActivity {
    private ViewGroup mRootContainer;
    private Scene mScene1;
    private Scene mScene2;
    private Transition mTransition;

    private TextView mTextView;
    private TextView mTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_scene_transitions);

        mRootContainer = (ViewGroup) findViewById(R.id.rootContainer);

        mTransition = TransitionInflater.from(this).inflateTransition(R.transition.cus_transition);
        // require api 19
        mScene1 = Scene.getSceneForLayout(mRootContainer, R.layout.scene1_layout, this);
        mScene2 = Scene.getSceneForLayout(mRootContainer, R.layout.scene2_layout, this);
        mScene1.enter();

        mTextView = (TextView) findViewById(R.id.tv_helloworld);
        mTextView2 = (TextView) findViewById(R.id.tv_helloworld2);
    }

    public void goToScene2 (View view) {
        TransitionManager.go(mScene2, mTransition);
    }

    public void goToScene1 (View view) {
        TransitionManager.go(mScene1, mTransition);
    }

    public void test(View v) {
        TransitionManager.beginDelayedTransition(mRootContainer, new Slide());
        toggleVisibility(mTextView, mTextView2);
    }


    private static void toggleVisibility(View... views) {
        for (View view : views) {
            if (view != null) {
                boolean isVisible = view.getVisibility() == View.VISIBLE;
                view.setVisibility(isVisible ? View.INVISIBLE : View.VISIBLE);
            }
        }
    }

}
