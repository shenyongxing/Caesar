package com.study.shenxing.caesar.desktopanimation;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.study.shenxing.caesar.R;

public class DesktopIconLauncherActivity extends AppCompatActivity {

    private ImageView mShadowIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desk_icon_launcher);
        mShadowIcon = (ImageView) findViewById(R.id.iv_icon_shadow);

        Intent it = getIntent();
        if (it != null) {
            Rect rect = it.getSourceBounds();
            relocateIconShadow(rect);
        }
        Log.i("sh", "onCreate: ");
    }


    private void relocateIconShadow(Rect rect) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mShadowIcon.getLayoutParams();
        lp.topMargin = rect.top - getStatusBarHeight();
        lp.leftMargin = rect.left;
        mShadowIcon.setLayoutParams(lp);
    }

    private int getStatusBarHeight() {
        int height;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//134217728
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//67108864
            height = 0;
        } else {
            int identifier = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (identifier > 0) {
                height = this.getResources().getDimensionPixelSize(identifier);
            } else {
                height = 0;
            }
        }
        return height;
    }
}
