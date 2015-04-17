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
import kr.uno.android.animation.item.PagerItem;
import kr.uno.android.animation.util.ImageLoader;

public class SamplePagerAdapter extends PagerAdapter {

        private Context mContext;
        private List<PagerItem> mItemList;
        private LayoutInflater mInflater;

        public SamplePagerAdapter(Context context, List<PagerItem> itemList) {
            mContext = context;
            mItemList = itemList;
            mInflater = LayoutInflater.from(mContext);
        }

        public PagerItem getItem(int position) {
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

            PagerItem item = getItem(position);

            // view
            View view = mInflater.inflate(R.layout.row_pager_item, null);
            ImageView ivPager = (ImageView) view.findViewById(R.id.iv_pager);
            TextView tvText = (TextView) view.findViewById(R.id.tv_text);
            ImageLoader.getInstance(mContext).load(ivPager, item.image);
//            ImageLoader.getInstance(mContext).blur(ivPager, item.image);
            tvText.setText(item.name);
            tvText.setTextColor(Color.parseColor(item.color));

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }