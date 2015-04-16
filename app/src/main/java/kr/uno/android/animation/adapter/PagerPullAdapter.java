package kr.uno.android.animation.adapter;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import kr.uno.android.animation.R;
import kr.uno.android.animation.item.BaseViewHolder;
import kr.uno.android.animation.item.PagerItem;
import kr.uno.android.animation.item.Row;
import kr.uno.android.animation.ui.widget.InfiniteViewPager;
import kr.uno.android.animation.util.DateUtil;
import kr.uno.android.animation.util.DisplayUtil;
import kr.uno.android.animation.util.LogUtil;

public class PagerPullAdapter extends BaseRecyclerAdapter {

    public static final int TYPE_PAGER = 0;
    public static final int TYPE_LIST = 10;

    private List<PagerItem> mPagerItems;
    private List<String> mListItems;
    private boolean isExpanded;
    private int mPagerPosition;

    public PagerPullAdapter(Context context) {
        super(context);;
    }

    @Override
    public BaseViewHolder getViewHolder(ViewGroup parent, int type) {
        BaseViewHolder holder = null;
        switch (type) {
            case TYPE_PAGER: holder = new PagerViewHolder(getContext(), parent, R.layout.row_pager); break;
            case TYPE_LIST: holder = new ListViewHolder(getContext(), parent, R.layout.row_pull_list); break;
        }
        return holder;
    }

    public void setPagerItems(List<PagerItem> itemList) {
        mPagerItems = itemList;
    }

    public void setListItems(List<String> itemList) {
        mListItems = itemList;
        refresh();
    }

    public void refresh() {
        List<Row> rows = new ArrayList<>();

        // 헤더
        if (mPagerItems != null && mPagerItems.size() > 0) {
            rows.add(new Row(mPagerItems, TYPE_PAGER));
        }

        // 리스트
        if (mListItems != null && mListItems.size() > 0) {
            for (String item : mListItems) rows.add(new Row(item, TYPE_LIST));
        }

        setRows(rows);
    }

    class PagerViewHolder extends BaseViewHolder<List<PagerItem>> {

        @InjectView(R.id.pager) InfiniteViewPager mPager;
        @InjectView(R.id.rl_cover) RelativeLayout mRlCover;
        @InjectView(R.id.tv_timer) TextView mTvTimer;
        @InjectView(R.id.rl_icon) RelativeLayout mRlIcon;
        @InjectView(R.id.iv_icon_horizontal) ImageView mIvIconHorizontal;
        @InjectView(R.id.iv_icon_vertical) ImageView mIvIconVertical;
        @InjectView(R.id.rl_icon_guide) RelativeLayout mRlIconGuide;
        @InjectView(R.id.iv_icon_guide_left) ImageView mIvIconGuideLeft;
        @InjectView(R.id.iv_icon_guide_right) ImageView mIvIconGuideRight;

        private int mDisplayWidth;

        public PagerViewHolder(Context context, ViewGroup parent, int resId) {
            super(context, parent, resId);
            mDisplayWidth = DisplayUtil.getWidth(getContext()) / 2;
            mPager.getLayoutParams().height = DisplayUtil.getHeight(getContext());
        }

        @Override
        public void onBindView(final List<PagerItem> itemList, int position) {

//            final SampleFragmentPagerAdapter adapter = new SampleFragmentPagerAdapter(((FragmentActivity) getContext()).getSupportFragmentManager(), itemList);
            final SamplePagerAdapter adapter = new SamplePagerAdapter(getContext(), itemList);
            InfinitePagerAdapter infinitePagerAdapter = new InfinitePagerAdapter(adapter);
            mPager.setAdapter(infinitePagerAdapter, mPagerPosition);

            toBottomAnim();

            // 아이콘 회전
            mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    mPagerPosition = position % itemList.size();
                    LogUtil.i("position: " + mPagerPosition);
                }

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    float normalizedposition = Math.abs(positionOffset);
                    position = (normalizedposition < 0.5f ? position : position + 1) % adapter.getCount();
                    PagerItem item = itemList.get(position);
                    int color = Color.parseColor(item.color);
                    mTvTimer.setTextColor(color);
                    mIvIconHorizontal.setBackgroundColor(color);
                    mIvIconVertical.setBackgroundColor(color);
                    mIvIconGuideLeft.setBackgroundColor(color);
                    mIvIconGuideRight.setBackgroundColor(color);
                    mRlIcon.setRotation(360 * normalizedposition);
                    mRlCover.requestLayout();
                }
            });

            // 페이지 패딩 조절
            mPager.setPageTransformer(false, new ViewPager.PageTransformer() {

                @Override
                public void transformPage(View view, float position) {

                    // view
                    RelativeLayout rlContent = (RelativeLayout) view.findViewById(R.id.rl_content);

                    float normalizedposition = Math.abs(Math.abs(position) - 1);
                    if (position == 0.0f || position == 1.0f) {
                        rlContent.setPadding(0, 0, 0, 0);
                    } else {
                        int padding = (int) (mDisplayWidth - (mDisplayWidth * normalizedposition));
                        if (position < 0.0f) rlContent.setPadding(padding, 0, -padding, 0);
                        if (position > 0.0f) rlContent.setPadding(-padding, 0, padding, 0);
                    }
                    view.requestLayout();
                }
            });

            Timer time = new Timer();
            time.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    ((Activity) getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTvTimer.setText(DateUtil.getNow("HH\nmm\nss"));
                        }
                    });
                }
            }, 0, 1000);
        }

        private void toBottomAnim() {
            mRlIconGuide.animate()
                    .translationY(mRlIconGuide.getLayoutParams().height / 3)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            toUpAnim();
                        }
                    }).start();
        }

        private void toUpAnim() {
            mRlIconGuide.animate()
                    .translationY(0f)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            toBottomAnim();
                        }
                    }).start();
        }
    }

    class ListViewHolder extends BaseViewHolder<String> {

        @InjectView(R.id.tv_pull) TextView mTvPull;

        public ListViewHolder(Context context, ViewGroup parent, int resId) {
            super(context, parent, resId);
        }

        @Override
        public void onBindView(String item, int position) {
            mTvPull.setText(item);
        }
    }
}