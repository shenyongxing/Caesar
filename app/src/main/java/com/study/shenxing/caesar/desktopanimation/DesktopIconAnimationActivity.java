package com.study.shenxing.caesar.desktopanimation;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.study.shenxing.caesar.R;

public class DesktopIconAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desktop_icon_animation);

        Intent it = new Intent(this, DesktopIconLauncherActivity.class);
        it.setAction("com.shenxing.desktop.test");
        createShortCut(this, it);
    }

    private void createShortCut(Context context, Intent targetIntent) {
        Intent it = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        it.putExtra(Intent.EXTRA_SHORTCUT_NAME, "HelloWorld");
        it.putExtra(Intent.EXTRA_SHORTCUT_INTENT, targetIntent);
        it.putExtra(Intent.EXTRA_SHORTCUT_ICON, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        it.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, R.drawable.ic_launcher));
        it.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.sendBroadcast(it);
    }
}
