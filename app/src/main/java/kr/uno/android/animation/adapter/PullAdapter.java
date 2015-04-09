package kr.uno.android.animation.adapter;


import android.content.Context;
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


    public PullAdapter(Context context) {
        super(context);
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

        public HeaderViewHolder(Context mContext, ViewGroup parent, int resId) {
            super(mContext, parent, resId);
        }

        @Override
        public void onBindView(Integer item, int position) {
            mRlHeader.getLayoutParams().height = DisplayUtil.getWidth(getContext()) / 2;
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