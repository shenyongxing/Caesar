package com.study.shenxing.caesar.displaybitmap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by shenxing on 16/6/19.
 * display bitmaps demo from develop.com
 */
public class ImageGridActivity extends FragmentActivity {
    private static final String TAG = "ImageGridActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportFragmentManager().findFragmentByTag(TAG) != null) {

            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(android.R.id.content, new ImageGridFragment(), TAG);

        }
    }
}
