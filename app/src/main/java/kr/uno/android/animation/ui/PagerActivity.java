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

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.uno.android.animation.R;
import kr.uno.android.animation.util.DisplayUtil;

import static android.view.ViewGroup.LayoutParams;


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
        mDisplayWidth = DisplayUtil.getWidth(this);

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
                if (position == 0.0f || position == 1.0f) {
                    view.setPadding(0, 0, 0, 0);
                } else {
                    if (position < 0.0f) view.setPadding(0, 0, -padding, 0);
                    if (position > 0.0f) view.setPadding(-padding, 0, 0, 0);
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
            ImageView iv = new ImageView(mContext);
            iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setImageResource(getItem(position));
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
