package kr.uno.android.animation.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.uno.android.animation.R;
import kr.uno.android.animation.util.DisplayUtil;


public class PagerActivity extends ActionBarActivity {

    @InjectView(R.id.pager) ViewPager mPager;

    private int mDisplayWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        ButterKnife.inject(this);

        initValue();
    }

    public void initValue() {
        mDisplayWidth = DisplayUtil.getWidth(this) / 2;

        List<Integer> itemList = new ArrayList<>();
        itemList.add(R.drawable.sample1);
        itemList.add(R.drawable.sample2);
        itemList.add(R.drawable.sample3);

        SamplePagerAdapter adapter = new SamplePagerAdapter(this, itemList);
        mPager.setAdapter(adapter);
        mPager.setPageTransformer(false, new ViewPager.PageTransformer() {

            @Override
            public void transformPage(View view, float position) {
                float normalizedposition = Math.abs(Math.abs(position) - 1);
                int padding = (int) (mDisplayWidth - (mDisplayWidth * normalizedposition));

                RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.rl_content);
                if (position == 0.0f || position == 1.0f) {
                    rl.setPadding(0, 0, 0, 0);
                } else {
                    if (position < 0.0f) rl.setPadding(padding, 0, -padding, 0);
                    if (position > 0.0f) rl.setPadding(-padding, 0, padding, 0);
                }
                view.requestLayout();
            }
        });
    }

    class SamplePagerAdapter extends PagerAdapter {

        private Context mContext;
        private List<Integer> mItemList;
        private LayoutInflater mInflater;

        public SamplePagerAdapter(Context context, List<Integer> itemList) {
            mContext = context;
            mItemList = itemList;
            mInflater = LayoutInflater.from(mContext);
        }

        public int getItem(int position) {
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

            // view
            View view = mInflater.inflate(R.layout.row_pager, null);
            ImageView ivPager = (ImageView) view.findViewById(R.id.iv_pager);
            TextView tvPosition = (TextView) view.findViewById(R.id.tv_position);
            TextView tvRes = (TextView) view.findViewById(R.id.tv_res);

            // value
            ivPager.setImageResource(getItem(position));
            tvPosition.setText("position: " + position);
            tvRes.setText("resource: " + getItem(position));

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
