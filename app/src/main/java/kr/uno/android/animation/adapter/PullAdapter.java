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

public class PullAdapter extends BaseRecyclerAdapter {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_LIST = 10;

    private int mHeaderResId;
    private List<String> mItemList;
    private View mHeaderView;
    private int mHeadertHeightDefaul;
    private int mHeaderHeightMax;
    private boolean isExpanded;

    public PullAdapter(Context context) {
        super(context);
        mHeaderHeightMax = DisplayUtil.getHeight(getContext());
        mHeadertHeightDefaul = mHeaderHeightMax / 2;
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
        final View view = mHeaderView.findViewById(R.id.rl_header);
        int height = view.getLayoutParams().height + offset;

        if (view != null && height > mHeadertHeightDefaul) {
            height = height > mHeadertHeightDefaul * 2 ? mHeadertHeightDefaul * 2 : height;
            view.getLayoutParams().height = height;
            view.requestLayout();
            isExpanded = true;
        } else {
            isExpanded = false;
        }
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void foldingHeader() {
        final View view = mHeaderView.findViewById(R.id.rl_header);
        if (view != null) {
            ValueAnimator animator = ValueAnimator.ofInt(view.getLayoutParams().height, mHeadertHeightDefaul);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    view.getLayoutParams().height = (int) animation.getAnimatedValue();
                    view.requestLayout();
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

        private int mPadding;

        public HeaderViewHolder(Context mContext, ViewGroup parent, int resId) {
            super(mContext, parent, resId);
            mHeaderView = itemView;
            mRlHeader.getLayoutParams().height = mHeadertHeightDefaul;
            mPadding = mRlHeader.getPaddingLeft();
        }

        @Override
        public void onBindView(Integer item, int position) {
            mIvHeader.setImageResource(item);
        }
    }

    class ListViewHolder extends BaseViewHolder<String> {

        @InjectView(R.id.tv_pull) TextView mTvPull;

        public ListViewHolder(Context mContext, ViewGroup parent, int resId) {
            super(mContext, parent, resId);
        }

        @Override
        public void onBindView(String item, int position) {
            mTvPull.setText(item);
        }
    }
}