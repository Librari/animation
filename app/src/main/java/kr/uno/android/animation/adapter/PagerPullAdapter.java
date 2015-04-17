package kr.uno.android.animation.adapter;


import android.animation.ValueAnimator;
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
import kr.uno.android.animation.item.Product;
import kr.uno.android.animation.item.Row;
import kr.uno.android.animation.listener.ProductListener;
import kr.uno.android.animation.ui.widget.InfiniteViewPager;
import kr.uno.android.animation.util.DateUtil;
import kr.uno.android.animation.util.DisplayUtil;
import kr.uno.android.animation.util.ImageLoader;
import kr.uno.android.animation.util.LogUtil;

public class PagerPullAdapter extends BaseRecyclerAdapter {

    public static final int TYPE_DIVIDER = 0;
    public static final int TYPE_PAGER = 10;
    public static final int TYPE_LIST = 20;

    private List<Product> mPagerItems;
    private List<Product> mListItems;
    private int mPagerPosition;

    private View mHeaderView;
    private int mHeaderHeightDefault;
    private int mHeaderHeightMax;
    private boolean isExpanded;
    private ProductListener mListener;

    public PagerPullAdapter(Context context, ProductListener listener) {
        super(context);
        mListener = listener;
        mHeaderHeightDefault = DisplayUtil.getWidth(getContext());
        mHeaderHeightMax = (int) (mHeaderHeightDefault * 1.2f);
    }

    @Override
    public BaseViewHolder getViewHolder(ViewGroup parent, int type) {
        BaseViewHolder holder = null;
        switch (type) {
            case TYPE_DIVIDER: holder = new Divider(getContext(), parent, R.layout.row_product_divider); break;
            case TYPE_PAGER: holder = new PagerViewHolder(getContext(), parent, R.layout.row_product_pager); break;
            case TYPE_LIST: holder = new ListViewHolder(getContext(), parent, R.layout.row_product_list); break;
        }
        return holder;
    }

    public void setHeaderHeightOffset(int offset) {
        final View content = mHeaderView.findViewById(R.id.pager);
        final View blur = mHeaderView.findViewById(R.id.rl_cover);

        // height 조절
        float ratio = (float) content.getLayoutParams().height / mHeaderHeightMax;
        int height = content.getLayoutParams().height;
        height += offset < 0 ? offset : offset - (offset * 0.8f);
        if (content != null && height > mHeaderHeightDefault) {
            height = height > mHeaderHeightMax ? mHeaderHeightMax : height;
            content.getLayoutParams().height = height;
            blur.setAlpha(1f - ((float) (height - mHeaderHeightDefault) / (mHeaderHeightMax - height)));
            content.requestLayout();
            isExpanded = true;
        } else {
            isExpanded = false;
        }
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void foldingHeader() {
        final View content = mHeaderView.findViewById(R.id.pager);
        final View blur = mHeaderView.findViewById(R.id.rl_cover);
        if (content != null) {
            ValueAnimator animator = ValueAnimator.ofInt(content.getLayoutParams().height, mHeaderHeightDefault);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int height = (int) animation.getAnimatedValue();
                    content.getLayoutParams().height = height;
                    blur.setAlpha(1f - ((float) (height - mHeaderHeightDefault) / (mHeaderHeightMax - mHeaderHeightDefault)));
                    content.requestLayout();
                }
            });
            animator.setStartDelay(0);
            animator.setDuration(200);
            animator.start();
        }

        isExpanded = false;
    }

    public void setPagerItems(List<Product> itemList) {
        mPagerItems = itemList;
    }

    public void setListItems(List<Product> itemList) {
        mListItems = itemList;
        refresh();
    }

    public void refresh() {
        List<Row> rows = new ArrayList<>();

        // 헤더
        if (mPagerItems != null && mPagerItems.size() > 0) {
            rows.add(new Row(mPagerItems, TYPE_PAGER));
            rows.add(new Row(0f, TYPE_DIVIDER));
        }

        // 리스트
        if (mListItems != null && mListItems.size() > 0) {
            for (Product item : mListItems) {
                rows.add(new Row(item, TYPE_LIST));
                rows.add(new Row(0f, TYPE_DIVIDER));
            }
        }

        setRows(rows);
    }

    class PagerViewHolder extends BaseViewHolder<List<Product>> {

        @InjectView(R.id.pager) InfiniteViewPager mPager;
        @InjectView(R.id.rl_cover) RelativeLayout mRlCover;
        @InjectView(R.id.tv_timer) TextView mTvTimer;
        @InjectView(R.id.rl_icon) RelativeLayout mRlIcon;
        @InjectView(R.id.iv_icon_horizontal) ImageView mIvIconHorizontal;
        @InjectView(R.id.iv_icon_vertical) ImageView mIvIconVertical;

        private int mDisplayWidth;
        private int mNextLeft;

        public PagerViewHolder(Context context, ViewGroup parent, int resId) {
            super(context, parent, resId);
            mHeaderView = itemView;
            mDisplayWidth = DisplayUtil.getWidth(getContext()) / 2;

            mPager.getLayoutParams().height = DisplayUtil.getWidth(getContext());
        }

        @Override
        public void onBindView(final List<Product> itemList, int position) {

            if (mPager.getAdapter() == null) {
                Timer time = new Timer();
                time.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isExpanded() == false) {
                                    mNextLeft++;
                                    if (mNextLeft % 5 == 0)
                                        mPager.setCurrentItemAtNear(mPager.getCurrentItem() % itemList.size() + 1);
                                }
                                mTvTimer.setText(DateUtil.getNow("HH\nmm\nss"));
                            }
                        });
                    }
                }, 0, 1000);
            }

            final ProductPagerAdapter adapter = new ProductPagerAdapter(getContext(), itemList, mListener);
            InfinitePagerAdapter infinitePagerAdapter = new InfinitePagerAdapter(adapter);
            mPager.setAdapter(infinitePagerAdapter, mPagerPosition);

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
                    Product item = itemList.get(position);
                    int color = Color.parseColor(item.color);
                    mTvTimer.setTextColor(color);
                    mIvIconHorizontal.setBackgroundColor(color);
                    mIvIconVertical.setBackgroundColor(color);
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

                    mNextLeft = 0;
                    view.requestLayout();
                }
            });
        }
    }

    class ListViewHolder extends BaseViewHolder<Product> {

        @InjectView(R.id.iv_product) ImageView mIvProduct;
        @InjectView(R.id.tv_name) TextView mTvName;
        @InjectView(R.id.tv_description) TextView mTvDescription;

        public ListViewHolder(Context context, ViewGroup parent, int resId) {
            super(context, parent, resId);
            mIvProduct.getLayoutParams().height = (int) (DisplayUtil.getWidth(getContext()) * 1.365f);
        }

        @Override
        public void onBindView(final Product item, int position) {

            // control
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClickProduct(item);
                }
            });

            ImageLoader.getInstance(getContext()).loadGif(mIvProduct, item.image);
            mTvName.setText(item.name);
            mTvDescription.setText(item.description);
        }
    }

    class Divider extends BaseViewHolder<Float> {

        public Divider(Context context, ViewGroup parent, int resId) {
            super(context, parent, resId);
        }

        @Override
        public void onBindView(Float item, int position) {
            if (item == 0.0f) return;
            itemView.getLayoutParams().height = DisplayUtil.getPixelFromDp(getContext(), item);
        }
    }
}