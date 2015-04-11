package kr.uno.android.animation.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kr.uno.android.animation.R;
import kr.uno.android.animation.item.PagerSampleItem;
import kr.uno.android.animation.util.ImageLoader;

public class SamplePagerAdapter extends PagerAdapter {

        private Context mContext;
        private List<PagerSampleItem> mItemList;
        private LayoutInflater mInflater;

        public SamplePagerAdapter(Context context, List<PagerSampleItem> itemList) {
            mContext = context;
            mItemList = itemList;
            mInflater = LayoutInflater.from(mContext);
        }

        public PagerSampleItem getItem(int position) {
            return getCount() > position ? mItemList.get(position) : null;
        }

        @Override
        public int getCount() {
            return mItemList != null ? mItemList.size() : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            PagerSampleItem item = getItem(position);

            // view
            View view = mInflater.inflate(R.layout.row_pager, null);
            ImageView ivPager = (ImageView) view.findViewById(R.id.iv_pager);
            TextView tvText = (TextView) view.findViewById(R.id.tv_text);
            ImageLoader.getInstance(mContext).blur(ivPager, item.image);
            tvText.setText("position : " + item.name);
            tvText.setTextColor(Color.parseColor(item.color));

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }