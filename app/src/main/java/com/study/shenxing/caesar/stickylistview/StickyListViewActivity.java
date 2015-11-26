package com.study.shenxing.caesar.stickylistview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.study.shenxing.caesar.R;

public class StickyListViewActivity extends Activity {
    private ListView list;
    private ListAdapter adapter;
    private TextView titleView;
    private int lastVisibleIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sticky_listview_layout);

        list= (ListView) findViewById(R.id.main_list);
        titleView = (TextView) findViewById(R.id.item_title_name);

        List<String> vals=new ArrayList<String>();
        for (int i = 0; i < Countries.COUNTRIES.length; i++) {
        	vals.add(Countries.COUNTRIES[i]) ;
        }

        adapter=new ListAdapter(this,vals);
        list.setAdapter(adapter);
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            	// 获取第一个可见元素所在组索引
                int section = adapter.getSectionForPosition(firstVisibleItem);
                // 根据组索引获取下一个组的位置即position
                int nextSectionPosition = adapter.getPositionForSection(section + 1);
                float titleY=view.getY(); // getY是getTop()和y方向上的位移之和,若没有移动则是相等的 
                if(lastVisibleIndex != firstVisibleItem){
                    titleView.setText(adapter.getSectionName(section));
                }
                // 如果当前项的下一项就是新的组开始,则开始实现挤压
                if(firstVisibleItem+1 == nextSectionPosition){
                    View firstView=view.getChildAt(0);
                    int bottom=firstView.getBottom();
                    
                    // 通过高度和titleView的bottom计算都可以实现挤压效果
                    // 但是如果ListView有内外边距的情况下可能达不到效果
                    // 所以用titleView.getHeight()比较好
                    if(bottom < titleView.getHeight()){
                        titleY=bottom - titleView.getHeight();
                    }
//                    if (bottom < titleView.getBottom()) {
//                    	titleY=bottom - titleView.getBottom() ;
//                    }
                }
                // 挤压效果主要是靠在滑动过程中不断的改变titleView的位置实现的
                titleView.setY(titleY);
                lastVisibleIndex=firstVisibleItem;
            }
        });
        
        titleView.setText(adapter.getSectionName(0)) ;

    }
}


/**
 * 注意事项:在滑动事件处理时要想清楚原理,即先找到第一个可见元素所在组section,在根据section找到section + 1组中
 * 第一个元素的position,此时在做判断,当前第一个可见元素firstVisibleItem的下一个元素 firstVisibleItem + 1是否等于position
 * 若是则要发生挤压效果.
 * 同时注意getSectionForPosition(),
 * 和getPositionForSection()两个方法名字很相似,不要用反了.
 * 我在做这个效果时因为弄反了这两个方法导致没能实现相应的效果.
 */
