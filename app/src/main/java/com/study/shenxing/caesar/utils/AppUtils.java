package com.study.shenxing.caesar.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by masanbing on 15-11-2.
 */
public class AppUtils {

    /**
     * 获取指定包的版本号
     *
     * @param context
     * @param pkgName
     * @author huyong
     */
    public static int getVersionCodeByPkgName(Context context, String pkgName) {
        int versionCode = 0;
        if (pkgName != null) {
            PackageManager pkgManager = context.getPackageManager();
            try {
                PackageInfo pkgInfo = pkgManager.getPackageInfo(pkgName, 0);
                versionCode = pkgInfo.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                Log.i("AppUtils", "getVersionCodeByPkgName=" + pkgName + " has " + e.getMessage());
            }
        }
        return versionCode;
    }

    /**
     * 获取指定包的版本名称
     *
     * @param context
     * @param pkgName
     * @author huyong
     */
    public static String getVersionNameByPkgName(Context context, String pkgName) {
        String versionName = "0.0";
        if (pkgName != null) {
            PackageManager pkgManager = context.getPackageManager();
            try {
                PackageInfo pkgInfo = pkgManager.getPackageInfo(pkgName, 0);
                versionName = pkgInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                //NOT to do anything
                //e.printStackTrace();
            }
        }
        return versionName;
    }

    /**
     * 手机上是否有电子市场
     *
     * @param context
     * @return
     */
    public static boolean isMarketExist(final Context context) {
        return isAppExist(context, "com.android.vending");
    }

    public static boolean isAppExist(final Context context, final String packageName) {
        if (context == null || packageName == null) {
            return false;
        }

        boolean result = false;
        try {
            // mContext.createPackageContext(packageName,
            // Context.CONTEXT_IGNORE_SECURITY);
            context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SHARED_LIBRARY_FILES);
            result = true;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO: handle exception
            result = false;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * 跳转到Android Market
     *
     * @param uriString market的uri
     * @return 成功打开返回true
     */
    public static boolean gotoMarket(Context context, String uriString) {
        boolean ret = false;
        Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriString));
        marketIntent.setPackage("com.android.vending");
        if (context instanceof Activity) {
            marketIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        } else {
            marketIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        try {
            context.startActivity(marketIntent);
            ret = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void gotoDownload(Context context, String mDownurl) {
        if (AppUtils.isMarketExist(context)) {
            int length = "https://play.google.com/store/apps/details?id=".length();
            String id = mDownurl.substring(length);
            String marketString = "market://details?id=" + id;
            AppUtils.gotoMarket(context, marketString);
        } else {
            AppUtils.gotoBrowser(context, mDownurl);
        }
    }

    /**
     * 浏览器直接访问uri
     *
     * @param uriString
     * @return 成功打开返回true
     */
    public static boolean gotoBrowser(Context context, String uriString) {
        boolean ret = false;
        if (uriString == null) {
            return ret;
        }
        Uri browserUri = Uri.parse(uriString);
        if (null != browserUri) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, browserUri);
            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                context.startActivity(browserIntent);
                ret = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static String getVersionCode(Context context) {
        String version = "unknown";

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 1);
            if (info != null) {
                version = "" + info.versionCode;
            }
        } catch (Exception var3) {
            ;
        }

        return version;
    }

    public static String getVersionName(Context context) {
        String version = "unknown";

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 1);
            if (info != null) {
                version = info.versionName;
            }
        } catch (Exception var3) {
            ;
        }

        return version;
    }

    /**
     * 获取当前进程名
     *
     * @param context
     * @return
     */
    public static String getCurrProcessName(Context context) {
        try {
            final int currProcessId = android.os.Process.myPid();
            final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
            if (processInfos != null) {
                for (ActivityManager.RunningAppProcessInfo info : processInfos) {
                    if (info.pid == currProcessId) {
                        return info.processName;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 根据应用包名跳转到其主界面
     *
     * @param context
     * @return
     */
    public static boolean openApp(Context context, String pkgName) {
        PackageManager packageMgr = context.getPackageManager();
        Intent launchIntent = packageMgr.getLaunchIntentForPackage(pkgName);
        if (null != launchIntent) {
            try {
                context.startActivity(launchIntent);
                return true;
            } catch (ActivityNotFoundException e) {
                Intent intent = new Intent();
                intent.setPackage(pkgName);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                try {
                    context.startActivity(intent);
                    return true;
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                return false;
            }
        } else {
            Intent intent = new Intent();
            intent.setPackage(pkgName);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            try {
                context.startActivity(intent);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * <br>功能简述:
     * <br>功能详细描述:获取所有已经安装的安装包的包名
     * <br>注意:
     *
     * @return
     */
    @SuppressWarnings("WrongConstant")
    public static List<String> getInstalledApp(Context context) {
        List<String> list = new ArrayList<String>();
        List<PackageInfo> packageInfos = context.getApplicationContext().getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);

        int size = packageInfos.size();
        for (int i = 0; i < size; i++) {
            list.add(packageInfos.get(i).packageName);
        }
        return list;
    }

    /**
     * 通过包名获取应用程序的名称。
     *
     * @param context     Context对象。
     * @param packageName 包名。
     * @return 返回包名所对应的应用程序的名称。
     */
    public static String getAppNameByPkgName(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        String name = null;
        try {
            name = pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 通过包名获取应用程序的图标。
     *
     * @param context     Context对象。
     * @param packageName 包名。
     * @return 返回包名所对应的应用程序的名称。
     */
    public static Drawable getAppIconByPkgName(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        Drawable drawable = null;
        try {
            drawable = pm.getApplicationIcon(packageName);
        } catch (PackageManager.NameNotFoundException mE) {
            mE.printStackTrace();
        }
        return drawable;
    }

    /**
     * 获取Google Advertising Id
     * 注:该方法需要在异步线程中调用,因为AdvertisingIdClient.getAdvertisingIdInfo(mContext)不能在UI线程中执行.
     *
     * @return the device specific Advertising ID provided by the Google Play Services, <em>null</em> if an error occurred.
     */
    public static String getGoogleAdvertisingId(Context context) {
        //获取GADID
        AdvertisingIdClient.Info adInfo = null;
        try {
            adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (GooglePlayServicesRepairableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (adInfo != null) {
            return adInfo.getId();
        } else {
            return "UNABLE-TO-RETRIEVE";
        }
    }
}
