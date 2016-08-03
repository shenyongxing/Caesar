package com.study.shenxing.caesar.listviewdemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.study.shenxing.caesar.R;

import java.util.List;

/**
 * Created by shenxing on 16/2/20.
 */
public class ChattingListViewAdapter extends BaseAdapter {
    private Context mContext ;
    private List<ChatItemListViewBean> mData ;
    private LayoutInflater mLayoutInflater ;

    private Animation mAnimation;   // 进入listView的动画
    private boolean mIsScrollDown;  // 是否向下滑动

    private ListView mListView;

    public ChattingListViewAdapter(Context context, ListView listView, List<ChatItemListViewBean> data) {
        mContext = context ;
        mData = data ;
        mLayoutInflater = LayoutInflater.from(context) ;
        mAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom);
        mListView = listView;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if (convertView == null) {
            if (getItemViewType(position) == 0) {
                viewHolder = new ViewHolder() ;
                convertView = mLayoutInflater.inflate(R.layout.chat_item_in, null) ;
                viewHolder.mIcon = (ImageView) convertView.findViewById(R.id.icon_in);
                viewHolder.mMessage = (TextView) convertView.findViewById(R.id.chat_message_in);
            } else {
                viewHolder = new ViewHolder() ;
                convertView = mLayoutInflater.inflate(R.layout.chat_item_out, null) ;
                viewHolder.mIcon = (ImageView) convertView.findViewById(R.id.icon_out);
                viewHolder.mMessage = (TextView) convertView.findViewById(R.id.chat_message_out);
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int count = mListView.getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = mListView.getChildAt(i);
            childView.clearAnimation();
        }
        if (mIsScrollDown) {
            convertView.startAnimation(mAnimation);
            Log.i("sh", "onScrollDown &&&&&&&&&&&&&&&&&&&&&&&");
        } else {
            Log.i("sh", "onScrollUp");
        }

        viewHolder.mIcon.setImageBitmap(mData.get(position).getmIcon());
        viewHolder.mMessage.setText(mData.get(position).getmMessage());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getmType();
    }

    public void setIsScrollDown(boolean isScrollDown) {
        mIsScrollDown = isScrollDown;
    }

     public static class ViewHolder {
        public ImageView mIcon ;
        public TextView mMessage ;
    }
}
