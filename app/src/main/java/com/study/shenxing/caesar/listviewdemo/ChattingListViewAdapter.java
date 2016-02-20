package com.study.shenxing.caesar.listviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
    public ChattingListViewAdapter(Context context, List<ChatItemListViewBean> data) {
        mContext = context ;
        mData = data ;
        mLayoutInflater = LayoutInflater.from(context) ;
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

     public static class ViewHolder {
        public ImageView mIcon ;
        public TextView mMessage ;
    }
}
