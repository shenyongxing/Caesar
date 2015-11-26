package com.study.shenxing.caesar.stickylistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.study.shenxing.caesar.R;

import java.util.*;

/**
 * Created by Jan on 2015/2/5.
 */
public class ListAdapter extends BaseAdapter implements SectionIndexer{

    private Context context;

    private List<String> value;
    private List<String> sectionsKey = new ArrayList<String>();
    private Map<String,Integer> sectionsPosition =new HashMap<String, Integer>();

    public ListAdapter(Context context, List<String> value) {
        this.context = context;
        initData(value);
    }

    private void initData(List<String> value) {
        Collections.sort(value);
        for(int i=0;i<value.size();i++){
            String group=value.get(i).substring(0,1).toUpperCase();
            if(!sectionsPosition.keySet().contains(group)) {
                sectionsPosition.put(group, i);
                sectionsKey.add(group);
            }
        }
        this.value=value;
    }


    @Override
    public int getCount() {
        return value.size();
    }

    @Override
    public Object getItem(int position) {
        return value.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemView iv;
        if(convertView==null){
            iv=new ItemView();
            convertView= LayoutInflater.from(context).inflate(R.layout.list_item_view,parent,false);

            iv.title= (TextView) convertView.findViewById(R.id.item_title_name);
            iv.content= (TextView) convertView.findViewById(R.id.item_content);
            convertView.setTag(iv);
        } else {
        	iv= (ItemView) convertView.getTag();
        }

        String itemInfo = value.get(position);
        iv.content.setText(itemInfo);

        int section = getSectionForPosition(position);
        iv.title.setText(getSectionName(section));
        iv.title.setVisibility(position == getPositionForSection(section)?View.VISIBLE:View.GONE);

        return convertView;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }
    public String getSectionName(int section){
        return sectionsKey.get(section);
    }
    
    // 返回section组的第一个元素的position索引
    @Override
    public int getPositionForSection(int section) {
        if(section>=sectionsKey.size())
            section=sectionsKey.size()-1;
        return sectionsPosition.get(sectionsKey.get(section));
    }

    // 返回position位置处的元素所在的组
    @Override
    public int getSectionForPosition(int position) {
        String group=value.get(position).substring(0,1).toUpperCase();
        return  sectionsKey.indexOf(group);
    }

    class ItemView{
        TextView title;
        TextView content;
    }

}
