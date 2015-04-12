package kr.uno.android.animation.adapter;


import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import kr.uno.android.animation.R;
import kr.uno.android.animation.item.BaseViewHolder;
import kr.uno.android.animation.item.Row;
import kr.uno.android.animation.util.DisplayUtil;
import kr.uno.android.animation.util.ImageLoader;

public class PullAdapter extends BaseRecyclerAdapter {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_LIST = 10;

    private int mHeaderResId;
    private List<String> mItemList;
    private View mHeaderView;
    private int mHeaderHeightDefault;
    private int mHeaderHeightMax;
    private boolean isExpanded;

    public PullAdapter(Context context) {
        super(context);
        mHeaderHeightMax = DisplayUtil.getWidth(getContext());
        mHeaderHeightDefault = mHeaderHeightMax / 2;
    }

    @Override
    public BaseViewHolder getViewHolder(ViewGroup parent, int type) {
        BaseViewHolder holder = null;
        switch (type) {
            case TYPE_HEADER: holder = new HeaderViewHolder(getContext(), parent, R.layout.row_pull_header); break;      // 헤더
            case TYPE_LIST: holder = new ListViewHolder(getContext(), parent, R.layout.row_pull_list); break;            // 리스트
        }
        return holder;
    }

    public void setHeader(int resId) {
        mHeaderResId = resId;
    }

    public void setHeaderHeightOffset(int offset) {
        final View content = mHeaderView.findViewById(R.id.rl_header);
        final View blur = mHeaderView.findViewById(R.id.rl_blur);

        // height 조절
        float ratio = (float) content.getLayoutParams().height / mHeaderHeightMax;
        int height = (int) (content.getLayoutParams().height + (offset - (offset * ratio)));
        if (content != null && height > mHeaderHeightDefault) {
            height = height > mHeaderHeightDefault * 2 ? mHeaderHeightDefault * 2 : height;
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
        final View content = mHeaderView.findViewById(R.id.rl_header);
        final View blur = mHeaderView.findViewById(R.id.rl_blur);
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

    public void setItemList(List<String> itemList) {
        mItemList = itemList;
        refresh();
    }

    public void refresh() {
        List<Row> rows = new ArrayList<>();

        // 헤더
        if (mHeaderResId != 0) {
            rows.add(new Row(mHeaderResId, TYPE_HEADER));
        }

        // 리스트
        if (mItemList != null && mItemList.size() > 0) {
            for (String item : mItemList) {
                rows.add(new Row(item, TYPE_LIST));
            }
        }

        setRows(rows);
    }

    class HeaderViewHolder extends BaseViewHolder<Integer> {

        @InjectView(R.id.rl_header) RelativeLayout mRlHeader;
        @InjectView(R.id.iv_header) ImageView mIvHeader;
        @InjectView(R.id.rl_blur) RelativeLayout mRlBlur;
        @InjectView(R.id.iv_blur) ImageView mIvBlur;
        @InjectView(R.id.tv_blur) TextView mTvBlur;

        public HeaderViewHolder(Context context, ViewGroup parent, int resId) {
            super(context, parent, resId);
            mHeaderView = itemView;
            mRlHeader.getLayoutParams().height = mHeaderHeightDefault;
        }

        @Override
        public void onBindView(Integer item, int position) {
            ImageLoader.getInstance(getContext()).load(mIvHeader, item);
            ImageLoader.getInstance(getContext()).blur(mIvBlur, item);
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