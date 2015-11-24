package com.study.shenxing.caesar;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ListActivity {
    public static final String STUDY_DEMO = "android.intent.category.STUDY_DEMO";
    private Map<String, String> mItems = new HashMap<>() ;
    private List<String> mTitle = new ArrayList<>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addItems() ;
        ArrayAdapter<String> activityList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTitle) ;
        setListAdapter(activityList);
    }

    private void addItem(String title, String activityName) {
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
            Log.i("shenxing", "fullName is " + fullName);
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
                if (resolveInfo.activityInfo.name.endsWith(activityName)) {
                    return resolveInfo.activityInfo.name ;
                }
            }

        }
        return "" ;
    }

    /**
     * 在此处添加activity
     */
    private void addItems() {
        addItem("Fragment", "FragmentDemoActivity");
        addItem("Share Data", "ShareDataActivity");
        addItem("Matrix", "MatrixDemo");
    }
}