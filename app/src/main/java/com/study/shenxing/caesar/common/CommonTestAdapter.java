package com.study.shenxing.caesar.common;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.study.shenxing.caesar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenxing on 16-3-4.
 * Adapter for test
 */
public class CommonTestAdapter<T> extends BaseAdapter {
    private List<T> mDatas = new ArrayList<T>();
    private LayoutInflater mInflater ;
    public CommonTestAdapter(Context context, List<T> data) {
        mDatas = data ;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.common_listview_layout, null) ;
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.common_textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        T object = mDatas.get(position) ;
        if (object instanceof String) {
            String str = (String) object;
            viewHolder.mTextView.setText(str);
        }
        return convertView;
    }

    public class ViewHolder {
        public TextView mTextView ;
    }
}
