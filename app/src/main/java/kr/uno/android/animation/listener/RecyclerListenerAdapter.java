package kr.uno.android.animation.listener;

import android.support.v7.widget.RecyclerView;

public class RecyclerListenerAdapter extends RecyclerView.OnScrollListener {

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

    public void onScrollOffset(int offset) { }

    public void onScrollOffset(int offset, boolean isMove) {
        onScrollOffset(offset);
    }

    public boolean isOffsetEnable() { return false; }
}