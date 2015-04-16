package kr.uno.android.animation.ui.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import kr.uno.android.animation.adapter.InfinitePagerAdapter;

public class InfiniteViewPager extends ViewPager {

    public static final int MOVE_DIRECTION_DEFAULT = 0;
    public static final int MOVE_DIRECTION_NEAR = 1;
    public static final int MOVE_DIRECTION_RIGHT = 2;
    public static final int MOVE_DIRECTION_LEFT = 3;

    private int moveDirection;
    private boolean isEnableSwipe = true;

    public int getMoveDirection() {
        return moveDirection;
    }

    public void setMoveDirection(int moveDirection) {
        this.moveDirection = moveDirection;
    }

    public InfiniteViewPager(Context context) {
        super(context);
    }

    public InfiniteViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        setCurrentItem(getOffsetAmount());
    }

    public void setAdapter(PagerAdapter adapter, int position) {
        setAdapter(adapter);
        setCurrentItem(getOffsetAmount() + position);
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, true);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        switch (moveDirection) {
            case MOVE_DIRECTION_DEFAULT:
                setCurrentItemAtDefault(item, smoothScroll);
                break;

            case MOVE_DIRECTION_NEAR:
                setCurrentItemAtNear(item, smoothScroll);
                break;

            case MOVE_DIRECTION_RIGHT:
                setCurrentItemAtRight(item, smoothScroll);
                break;

            case MOVE_DIRECTION_LEFT:
                setCurrentItemAtLeft(item, smoothScroll);
                break;
        }
    }

    public void setCurrentItemAtDefault(int item) {
        setCurrentItemAtDefault(item, true);
    }

    public void setCurrentItemAtDefault(int item, boolean smoothScroll) {

        int realCount = ((InfinitePagerAdapter) getAdapter()).getRealCount();
        int virtualPosition = getCurrentItem() % realCount;

        if (virtualPosition > item) {
            setCurrentItemAtLeft(item, smoothScroll);
        } else {
            setCurrentItemAtRight(item, smoothScroll);
        }
    }

    public void setCurrentItemAtLeft(int item) {
        setCurrentItemAtLeft(item, true);
    }

    public void setCurrentItemAtLeft(int item, boolean smoothScroll) {

        int realCount = ((InfinitePagerAdapter) getAdapter()).getRealCount();
        int virtualPosition = getCurrentItem() % realCount;

        /** 좌측 방향으로 이동 */
        int offset = item - virtualPosition;
        if (offset > 0) {
            offset = -(realCount - virtualPosition + item);
        }

        super.setCurrentItem(getCurrentItem() + offset, smoothScroll);
    }

    public void setCurrentItemAtRight(int item) {
        setCurrentItemAtRight(item, true);
    }

    public void setCurrentItemAtRight(int item, boolean smoothScroll) {

        int realCount = ((InfinitePagerAdapter) getAdapter()).getRealCount();
        int virtualPosition = getCurrentItem() % realCount;

        /** 우측 방향으로 이동 */
        int offset = item - virtualPosition;
        if (offset < 0) {
            offset = realCount - virtualPosition + item;
        }

        super.setCurrentItem(getCurrentItem() + offset, smoothScroll);
    }

    public void setCurrentItemAtNear(int item) {
        setCurrentItemAtNear(item, true);
    }

    public void setCurrentItemAtNear(int item, boolean smoothScroll) {

        int realCount = ((InfinitePagerAdapter) getAdapter()).getRealCount();
        int virtualPosition = getCurrentItem() % realCount;

        /** 근접한 방향으로 이동 */
        // basic offset
        int offset = item - virtualPosition;
        int rightOverOffset = realCount - virtualPosition + item;
        int leftOverOffset = virtualPosition + realCount - item;

        // right over
        if (Math.abs(offset) > rightOverOffset) {
            offset = rightOverOffset;

            // left over
        } else if (Math.abs(offset) > leftOverOffset) {
            offset = -leftOverOffset;
        }

        super.setCurrentItem(getCurrentItem() + offset, smoothScroll);
    }

    private int getOffsetAmount() {
        if (getAdapter() instanceof InfinitePagerAdapter) {
            InfinitePagerAdapter infAdapter = (InfinitePagerAdapter) getAdapter();
            return infAdapter.getRealCount() * 100;
        } else {
            return 0;
        }
    }

    public boolean isEnableSwipe() {
        return isEnableSwipe;
    }

    public void setEnableSwipe(boolean enable) {
        isEnableSwipe = enable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return isEnableSwipe() ? super.onInterceptTouchEvent(arg0) : false;
    }
}