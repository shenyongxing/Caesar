package com.study.shenxing.caesar;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.study.shenxing.caesar.binder.AidlService;
import com.study.shenxing.caesar.desktopanimation.DesktopIconAnimationActivity;
import com.study.shenxing.caesar.hellojni.JniTestActivity;
import com.study.shenxing.caesar.progressbar.ProgressBarActivity;
import com.study.shenxing.caesar.utils.DrawUtils;
import com.study.shenxing.caesar.vectoranimation.VectorAniamtionActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.PathClassLoader;

public class MainActivity extends ListActivity {
    public static final String STUDY_DEMO = "android.intent.category.STUDY_DEMO";
    private Map<String, String> mItems = new HashMap<String, String>() ;
    private List<String> mTitle = new ArrayList<String>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER);
        setContentView(R.layout.activity_main);

        addItems() ;
        ArrayAdapter<String> activityList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTitle) ;
        setListAdapter(activityList);

        DrawUtils.resetDensity(this);

        // test plugin
        useDexClassLoader();
        // start service
//        startAidlTestService();
//        AidlService service = new AidlService();
//        try {
//            invokeRemoteMethod();
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }


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
//        addItem("Activity LifeCycle", "ActivityA") ;
//        addItem("Fragment", "FragmentDemoActivity");
//        addItem("Share Data", "ShareDataActivity");
//        addItem("Matrix", "MatrixDemo");
//        addItem("Round Image", "RoundImageActivity");
//        addItem("Sticky ListView", "StickyListViewActivity");
//        addItem("Theme Wallpaper", "ThemeWallpaperActivity");
//        addItem("Custom Animation", "CusAnimationActivity");
//        addItem("Temp Demo", "TempActivity");
//        addItem("Custom View", "CusFlowLayoutActivity");
//        addItem("Wave View", "WaveViewActivity");
//        addItem("Sava data", "SaveDataActivity") ;
//        addItem("50 Hacks", "Hacks50Activity");
//        addItem("SharePreference", "SharePreferencesActivity");
//        addItem("双曲螺线动画", "HyperbolicAnimationActivity");
//        addItem("知识点测试", "TestActivity2");
//        addItem("ViewFlipper", "ViewFlipperActivity");
//        addItem("悬浮窗demo", "WindowManagerActivity");
//        addItem("Shader", "ShaderActivity");
//        addItem("Work demo", "ListDemoActivity");
//        addItem("DataBase", "DatabaseDemoActivity");
//        addItem("Intent的常用用法", "IntentCommonUseActivity");
//        addItem("ListView用法", "ListViewActivity");
//        addItem("ScrollView与ListView滑动冲突", "ScrollAndListViewActivity");
//        addItem("网络请求", "NetWorkActivity");
//        addItem("常用排序", "AlgorithmDemoActivity");
//        addItem("xml解析", "XmlParserActivity");
//        addItem("gridview消失动画", "GridAnimActivity");
//        addItem("View Measure实践", "MeasureTestActivity");
//        addItem("RecycleView实践", "RecyclerViewActivity2");
//        addItem("MeterialDesign", "MeterialDesignDemo");
//        addItem("Transition", "SceneTransitionsActivity");
//        addItem("Scale 锚点问题", "ScalePivotActivity");
//        addItem("EventBus demo", "EventBusDemo");
//        addItem("aidl test", "AidlTestActivity");
//        addItem("test", "BatteryMainPageActivity");
//        addItem("permission test", "PermissionTestActivity");
//        addItem("custom Drawable", "CustomDrawableActivity");
//        addItem("mvp demo", "TaskActivity");
//        addItem("status Height", "StatusHeightActivity");
//        addItem("system ui visibility", "SystemUiActivity");
//        addItem("interpolator", "InterpolatorActivity");
//        addItem("流量统计demo", "TrafficStatActivity");
//        addItem("DeskTop icon animtion", "DesktopIconAnimationActivity");
//        addItem("custom progressbar", "ProgressBarActivity");
//        addItem("jni test", "JniTestActivity");
        addItem("Vector Animation", "VectorAniamtionActivity");
    }

    private boolean isValidate(String name) {
        if ("shenxing".equals(name)) {
            return true;
        }
        return false;
    }

    /**
     * 测试:调用其他apk的方法
     */
    public void useDexClassLoader() {
        // 注意第一个参数为action,所以在插件的activity intent-filter中必须添加action和categroy
        Intent it = new Intent("com.study.shenxing.plugintest", null);
        PackageManager pm = getPackageManager();
        List<ResolveInfo> plugIns = pm.queryIntentActivities(it, 0);
        if (plugIns != null && !plugIns.isEmpty()) {
            ResolveInfo resolveInfo = plugIns.get(0);
            ActivityInfo activityInfo = resolveInfo.activityInfo;

            String pkgName = activityInfo.packageName;
            String dexPath = activityInfo.applicationInfo.sourceDir;
            String dexOutputDir = activityInfo.applicationInfo.dataDir;
            String libPath = activityInfo.applicationInfo.nativeLibraryDir;
            // DexClassLoader 报错:
            // java.lang.IllegalArgumentException: optimizedDirectory not readable/writable: /data/data/com.study.shenxing.plugintest
//            DexClassLoader classLoader = new DexClassLoader(dexPath, dexOutputDir, libPath, getClass().getClassLoader());
            PathClassLoader classLoader = new PathClassLoader(dexPath, libPath, getClass().getClassLoader());
            try {
                Class<?> clazz = classLoader.loadClass(pkgName + ".PluginClass");
                Object obj = clazz.newInstance();
                Class[] params = new Class[2];
                params[0] = Integer.TYPE;
                params[1] = Integer.TYPE;
                Method method = clazz.getMethod("add", params);
                Integer result = (Integer) method.invoke(obj, 12, 34);
                Log.i("plugin", "result :" + result);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("plugin", "There is no plugin exist ");
        }
    }

    // start service
    public void startAidlTestService() {
        Intent it = new Intent(this, AidlService.class);
        startService(it);
    }

    private void invokeRemoteMethod() throws RemoteException {
        IBinder mRemote = null; // Binder驱动中的binder对象
        String filePath = "/sdcard/love.mp3";
        int code = 1000;
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        data.writeInterfaceToken("AidlService");
        data.writeString(filePath);
        mRemote.transact(code, data, reply, 0);     // Binder驱动挂起当前线程,并向服务端发送消息, 0表示双向IPC模式
        IBinder binder = reply.readStrongBinder();
        reply.recycle();
        data.recycle();
    }


    private static final BroadcastReceiver BROADCAST_RECEIVER = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    private static void unregisterBroadcastSafely(Context context) {
        try {
            context.unregisterReceiver(BROADCAST_RECEIVER);
        } catch (Exception e) {

        }
    }

    /**
     * 获取手机电量
     * 经过验证可以正确获取
     * @param context
     * @return
     */
    public static int getLevelPercent(Context context) {
        Intent intent = null;
        try {
            intent = context.registerReceiver(BROADCAST_RECEIVER, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        } catch (Exception e) {
        }

        if (intent == null) {
            return 0;
        }

        int level = intent.getIntExtra("level", 0);
        if (level == 0) {
            return 0;
        }

        int scale = intent.getIntExtra("scale", 0);
        if (scale == 0) {
            return 0;
        }

        unregisterBroadcastSafely(context);
        return level * 100 / scale;
    }
}