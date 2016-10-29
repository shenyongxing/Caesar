package com.study.shenxing.caesar.recycleview;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.study.shenxing.caesar.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity2 extends AppCompatActivity {
    private RecyclerView mRecyclerView;
//    private DataAdapter mDataAdapter;
    private NewVersionDataAdapter mDataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view2);

        mRecyclerView = (RecyclerView) findViewById(R.id.rc_content);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        // 设置每个item占据几个单元格，实现recyclerView的混排
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = mRecyclerView.getAdapter().getItemViewType(position);
                if (type == DataModel.TYPE_THREE) {
                    return gridLayoutManager.getSpanCount();
                } else {
                    return 1;
                }
            }
        });
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mDataAdapter = new NewVersionDataAdapter(this);
        mRecyclerView.setAdapter(mDataAdapter);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
                int spanSize = layoutParams.getSpanSize();
                int spanIndex = layoutParams.getSpanIndex();    // 指定单元格的索引
                outRect.top = 20;
                if (spanSize != gridLayoutManager.getSpanCount()) { // 不是横跨一行的
                    // 左10右10是为了两个item的大小一致，且加起来和top的20一致，UI外观会整齐一致
                    if (spanIndex == 1) {
                        outRect.left = 10;
                    } else {
                        outRect.right = 10;
                    }
                }
            }
        });

        initData();
    }

    private void initData() {
        List<DataModelOne> list1 = new ArrayList<DataModelOne>();
        List<DataModelTwo> list2 = new ArrayList<DataModelTwo>();
        List<DataModelThree> list3 = new ArrayList<DataModelThree>();

        for (int i = 0; i < 30; i++) {
            DataModelOne model = new DataModelOne();
            model.mTitle = "name" + i;
            model.mDesc = "age" + i;
            list1.add(model);
        }

        for (int i = 0; i < 30; i++) {
            DataModelTwo model = new DataModelTwo();
            model.mTitle = "name" + i;
            model.mDesc = "age" + i;
            model.mThumbnailColor = android.R.color.holo_blue_bright;
            list2.add(model);
        }


        for (int i = 0; i < 30; i++) {
            DataModelThree model = new DataModelThree();
            model.mTitle = "name" + i;
            model.mDesc = "age" + i;
            model.mThumbnailColor = android.R.color.holo_orange_dark;
            model.mContentColor = android.R.color.holo_red_light;
            list3.add(model);
        }

        mDataAdapter.addList(list1, list2, list3);
        mDataAdapter.notifyDataSetChanged();
    }
}
