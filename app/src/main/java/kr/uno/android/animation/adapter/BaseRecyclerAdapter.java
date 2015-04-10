package kr.uno.android.animation.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import kr.uno.android.animation.item.BaseViewHolder;
import kr.uno.android.animation.item.Row;

public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<Row> mRows;

    public BaseRecyclerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        return getViewHolder(parent, type);
    }

    public abstract BaseViewHolder getViewHolder(ViewGroup parent, int type);

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindView(getRow(position) != null ? getRow(position).item : null, position);
    }

    @Override
    public int getItemViewType(int position) {
        return getRow(position) != null ? getRow(position).type : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getItemCount() {
        return getRowCount();
    }

    public int getRowCount() {
        return mRows != null ? mRows.size() : 0;
    }

    public Row getRow(int position) {
        return getItemCount() > position ? mRows.get(position) : null;
    }

    public void setRows(List<Row> rows) {
        mRows = rows;
        notifyDataSetChanged();
    }
}