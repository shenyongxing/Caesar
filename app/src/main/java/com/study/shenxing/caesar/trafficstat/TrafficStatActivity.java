package com.study.shenxing.caesar.trafficstat;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.study.shenxing.caesar.R;
import com.study.shenxing.caesar.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 流量统计demo
 */
public class TrafficStatActivity extends Activity {
    public static final String TAG = "sh_traff";
    public static final String TEST_PKG = "com.gomo.battery";


    private RecyclerView mRecyclerView;
    private TrafficDataAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_stat);

        initView();
        initData();

        Log.i(TAG, "onCreate: " + getUid("com.study.shenxing.caesar") + ", " + android.os.Process.myPid());
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rcl_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TrafficDataAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        List<TrafficInfoBean> dataList = getAllTrafficInfoBeans();
        mAdapter.setData(dataList);
    }


    /**
     * 获取应用的UserId
     * @param packageName
     * @return
     */
    private int getUid(String packageName) {
        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            int uid = applicationInfo.uid;
            Log.i(TAG, "getUid: " + uid);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private TrafficInfoBean getTrafficInfoBean(String packageName) {
        int uid = getUid(packageName);
        TrafficInfoBean infoBean = new TrafficInfoBean();
        infoBean.setPkgName(packageName);
        infoBean.setRxBytes(TrafficStats.getUidRxBytes(uid));
        infoBean.setTxBytes(TrafficStats.getUidTxBytes(uid));

        Log.i(TAG, packageName + ", rec:" + TrafficStats.getUidRxBytes(uid) + ", send:" + TrafficStats.getUidTxBytes(uid) + ", " + TrafficStats.getUidRxBytes(android.os.Process.myUid()) + ", " + TrafficStats.getUidTxBytes(android.os.Process.myUid()));
        return infoBean;
    }

    private List<TrafficInfoBean> getAllTrafficInfoBeans() {
        List<TrafficInfoBean> infoBeanList = new ArrayList<TrafficInfoBean>();
        List<String> installPackages = AppUtils.getInstalledApp(this);
        for (String pkgName : installPackages) {
            infoBeanList.add(getTrafficInfoBean(pkgName));
        }
        return infoBeanList;
    }
}
