package com.study.shenxing.caesar.displaybitmap;

import android.content.Context;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.study.shenxing.caesar.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ImageGridFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ImageGridFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageGridFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ImageAdapter mAdapter;
    private int mImageThumbSize;
    private int mImageThumbSpacing;

    public ImageGridFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImageGridFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ImageGridFragment newInstance(String param1, String param2) {
        ImageGridFragment fragment = new ImageGridFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mAdapter = new ImageAdapter(getActivity(), Arrays.asList(Images.imageThumbUrls));
        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelOffset(R.dimen.image_thumbnail_spacing);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_image_grid, container, false);
        final GridView gridView = (GridView) v.findViewById(R.id.image_grid_view);
        gridView.setOnItemClickListener(mOnItemClick);
        gridView.setOnScrollListener(mScrollListener);
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mAdapter.getmNumColumns() == 0) {
                    final int numColumns = (int) Math.floor(
                            gridView.getWidth() / (mImageThumbSize + mImageThumbSpacing));
                    if (numColumns > 0) {
                        final int columnWidth =
                                (gridView.getWidth() / numColumns) - mImageThumbSpacing;
                        mAdapter.setmNumColumns(numColumns);
                        mAdapter.setmItemHeight(columnWidth);
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
        return v;
    }

    /**
     * grid item点击事件
     */
    private AdapterView.OnItemClickListener mOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    /**
     * grid view 滑动监听
     */
    private AbsListView.OnScrollListener mScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == SCROLL_STATE_FLING) {

            } else {

            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    };

    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {

        }
    };

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * Image adapter
     */
    private class ImageAdapter extends BaseAdapter {

        private final Context mContext;
        private int mItemHeight = 0;
        private int mNumColumns = 0;
        private int mActionBarHeight = 0;
        private GridView.LayoutParams mImageViewLayoutParams;
        private List<String> mDatas;

        private ImageAdapter(Context context, List<String> data) {
            mContext = context;
            mDatas = data;
            mImageViewLayoutParams = new GridView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            // Calculate ActionBar height
            TypedValue tv = new TypedValue();
            if (context.getTheme().resolveAttribute(
                    android.R.attr.actionBarSize, tv, true)) {
                mActionBarHeight = TypedValue.complexToDimensionPixelSize(
                        tv.data, context.getResources().getDisplayMetrics());
            }
        }

        @Override
        public int getCount() {
            if (getmNumColumns() == 0) {
                return 0;
            }
            return Images.imageThumbUrls.length + mNumColumns;
        }

        @Override
        public Object getItem(int position) {
            return position < mNumColumns ? null : mDatas.get(position - mNumColumns);
        }

        @Override
        public long getItemId(int position) {
            return position < mNumColumns ? 0 : position - mNumColumns;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (position < mNumColumns) {
                if (convertView == null) {
                    convertView = new View(mContext);
                }
                convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mActionBarHeight));
                return convertView;
            }

            ImageView mImageView;
            if (convertView == null) {
                mImageView = new ImageView(mContext);
                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mImageView.setLayoutParams(mImageViewLayoutParams);
            } else {
                mImageView = (ImageView) convertView;
            }

//            loadImage();
            return convertView;
        }

        @Override
        public int getViewTypeCount() {
            // normal view and empty view above the grid view
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return position < mNumColumns ? 1 : 0;
        }

        /**
         * 是否具有固定的id
         * @return
         */
        @Override
        public boolean hasStableIds() {
            return true;
        }

        public int getmItemHeight() {
            return mItemHeight;
        }

        public void setmItemHeight(int mItemHeight) {
            if (mItemHeight == this.mItemHeight) {
                return;
            }
            // todo ImageFetcher
            mImageViewLayoutParams = new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mItemHeight);
            notifyDataSetChanged();
        }

        public int getmNumColumns() {
            return mNumColumns;
        }

        public void setmNumColumns(int mNumColumns) {
            this.mNumColumns = mNumColumns;
        }
    }
}
