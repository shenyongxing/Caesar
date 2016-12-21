package com.study.shenxing.caesar.trafficstat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.shenxing.caesar.R;
import com.study.shenxing.caesar.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenxing
 * @description
 * @date 2016/12/15
 */

public class TrafficDataAdapter extends RecyclerView.Adapter<TrafficDataAdapter.TrafficDataViewHolder> {

    private List<TrafficInfoBean> mData = new ArrayList<TrafficInfoBean>();
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public TrafficDataAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<TrafficInfoBean> list) {
        mData = list;
        notifyDataSetChanged();
    }

    @Override
    public TrafficDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.traffic_item_layout, parent, false);
        TrafficDataViewHolder holder = new TrafficDataViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(TrafficDataViewHolder holder, int position) {
        TrafficInfoBean infoBean = mData.get(position);
        holder.mTvSendBytes.setText(String.valueOf(infoBean.getTxBytes()));
        holder.mTvReceivedBytes.setText(String.valueOf(infoBean.getRxBytes()));
        holder.mAppIcon.setImageDrawable(AppUtils.getAppIconByPkgName(mContext, infoBean.getPkgName()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class TrafficDataViewHolder extends RecyclerView.ViewHolder {

        private ImageView mAppIcon;

        private TextView mTvReceivedBytes;

        private TextView mTvSendBytes;

        public TrafficDataViewHolder(View itemView) {
            super(itemView);

            mAppIcon = (ImageView) itemView.findViewById(R.id.icon);
            mTvReceivedBytes = (TextView) itemView.findViewById(R.id.tv_received);
            mTvSendBytes = (TextView) itemView.findViewById(R.id.tv_send);
        }
    }
}
