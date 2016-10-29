RecyclerViewActivity2是学习慕课网时的实践

1.课程先通过一种数据bean中定义多个字段，实现多种type共用的recyclerView。
并通过

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

实现item的混排。

同时通过

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
实现修改RecyclerView的item的间距。

2. 在实例化Adapter的时候，可以先不用在构造方法里传入数据集，而是在数据准备完毕时在通过方法设置，然后调用adapter的notifyDataSetChanged()方法。
3. 通过定义多种数据bean和多种对应的ViewHolder有利于代码清晰和维护。
4. 注意NewVersionAdapter中的mPosition的数据结构的运行，它的作用是将position处的item映射到相应的list集合，从而取出对应的真实item，从而绑定item。