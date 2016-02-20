package com.study.shenxing.caesar;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.study.shenxing.caesar.shader.ShaderActivity;
import com.study.shenxing.caesar.work.ListDemoActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ListActivity {
    public static final String STUDY_DEMO = "android.intent.category.STUDY_DEMO";
    private Map<String, String> mItems = new HashMap<String, String>() ;
    private List<String> mTitle = new ArrayList<String>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER);
        setContentView(R.layout.activity_main);

        addItems() ;
        ArrayAdapter<String> activityList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTitle) ;
        setListAdapter(activityList);
    }

    private void addItem(String title, String activityName) {
        if (mItems.containsValue(activityName)) {
            throw new IllegalArgumentException("activityName was repeated") ;
        }
        mItems.put(title, activityName);
        mTitle.add(title) ;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (position >= 0 && position < mItems.size()) {
            String key = mTitle.get(position) ;
            String activityName = mItems.get(key) ;
            startActivity(activityName);
        }
    }

    private void startActivity(String activityName) {
        try {
            String fullName = getFullActivityName(activityName) ;
            Class activity = Class.forName(fullName) ;
            Intent it = new Intent(this, activity) ;
            startActivity(it);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getFullActivityName(String activityName) {
        Intent it = new Intent(Intent.ACTION_MAIN) ;
        it.addCategory(STUDY_DEMO) ;
        PackageManager packageManager = getPackageManager() ;
        List<ResolveInfo> list = packageManager.queryIntentActivities(it, 0) ;
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                ResolveInfo resolveInfo = list.get(i) ;

                String activityInfoStr = resolveInfo.activityInfo.name ;
                String[] temp = activityInfoStr.split("\\.") ;
                if (!TextUtils.isEmpty(temp[temp.length - 1])) {
                    if (temp[temp.length - 1].equals(activityName)) {
                        return activityInfoStr ;
                    }
                }
            }

        }
        return "" ;
    }

    /**
     * 在此处添加要显示到列表的activity
     * 注意：activity名称不能重复
     */
    private void addItems() {
        addItem("Activity LifeCycle", "ActivityA") ;
        addItem("Fragment", "FragmentDemoActivity");
        addItem("Share Data", "ShareDataActivity");
        addItem("Matrix", "MatrixDemo");
        addItem("Round Image", "RoundImageActivity");
        addItem("Sticky ListView", "StickyListViewActivity");
        addItem("Theme Wallpaper", "ThemeWallpaperActivity");
        addItem("Custom Animation", "CusAnimationActivity");
        addItem("Temp Demo", "TempActivity");
        addItem("Custom FlowLayout", "CusFlowLayoutActivity");
        addItem("Wave View", "WaveViewActivity");
        addItem("Sava data", "SaveDataActivity") ;
        addItem("50 Hacks", "Hacks50Activity");
        addItem("SharePreference", "SharePreferencesActivity");
        addItem("双曲螺线动画", "HyperbolicAnimationActivity");
        addItem("知识点测试", "TestActivity");
        addItem("ViewFlipper", "ViewFlipperActivity");
        addItem("悬浮窗demo", "WindowManagerActivity");
        addItem("Shader", "ShaderActivity");
        addItem("Work demo", "ListDemoActivity");
        addItem("DataBase", "DatabaseDemoActivity");
        addItem("Intent的常用用法", "IntentCommonUseActivity");
        addItem("ListView用法", "ListViewActivity");
    }
}