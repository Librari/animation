package kr.uno.android.animation.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private Context mContext;

    public BaseViewHolder(Context mContext, ViewGroup parent, int resId) {
        super(LayoutInflater.from(mContext).inflate(resId, parent, false));
        this.mContext = mContext;
        ButterKnife.inject(this, itemView);
    }

    public void onBindView(final T item, final int position) { }

    public Context getContext() {
        return mContext;
    }
}