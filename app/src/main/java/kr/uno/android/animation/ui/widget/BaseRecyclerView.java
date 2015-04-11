package kr.uno.android.animation.ui.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import kr.uno.android.animation.listener.RecyclerListenerAdapter;

public class BaseRecyclerView extends RecyclerView {

    private RecyclerListenerAdapter mListener;
    private boolean isTop = true;

    public BaseRecyclerView(Context context) {
        super(context);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnRecyclerListener(final RecyclerListenerAdapter listener) {
        super.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                listener.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                listener.onScrolled(recyclerView, dx, dy);
                isTop = getChildCount() > 0 && getChildPosition(getChildAt(0)) == 0 && getChildAt(0).getTop() == 0;
            }
        });

        super.setOnTouchListener(new View.OnTouchListener() {

            private float rawY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN: rawY = 0; break;
                    case MotionEvent.ACTION_UP: listener.onScrollOffset(0, false); break;

                    case MotionEvent.ACTION_MOVE:
                        if (isTop) {
                            if (rawY == 0) rawY = event.getRawY();
                            listener.onScrollOffset((int) (event.getRawY() - rawY), true);
                            rawY = event.getRawY();
                            return listener.isOffsetEnable();
                        }
                        break;
                }
                return false;
            }
        });
    }

    @Deprecated
    @Override
    public void setOnScrollListener(OnScrollListener listener) {
        super.setOnScrollListener(listener);
    }

    @Deprecated
    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }
}
