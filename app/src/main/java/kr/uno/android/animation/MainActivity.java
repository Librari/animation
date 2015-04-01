package kr.uno.android.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import kr.uno.android.animation.util.DisplayUtil;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.ll_search) LinearLayout mLlSearch;
    @InjectView(R.id.et_search) EditText mEtSearch;
    @InjectView(R.id.iv_open_bar) ImageView mIvOpenBar;

    // 닫기
    @InjectView(R.id.fl_toggle) FrameLayout mFlToggle;
    @InjectView(R.id.iv_close_bar_top) ImageView mIvCloseBarTop;
    @InjectView(R.id.iv_close_bar_bottom) ImageView mIvCloseBarBottom;

    private int mTargetExpandWidth;
    private boolean isAnimate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        ButterKnife.inject(this);

        int ovalWidth = (int) getResources().getDimension(R.dimen.oval_width);
        int padding = mLlSearch.getPaddingLeft() * 2;
        mTargetExpandWidth = DisplayUtil.getWidth(MainActivity.this) - ovalWidth - padding;
    }

    @OnClick({ R.id.fl_toggle })
    public void onClick(View v) {

        switch (v.getId()) {

            // 열기, 닫기
            case R.id.fl_toggle:
                if (mIvCloseBarTop.getVisibility() == View.GONE) {
                    show();
                } else {
                    gone();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mIvCloseBarTop.getVisibility() == View.VISIBLE) gone();
        else super.onBackPressed();
    }

    public void show() {
        if (isAnimate) return;
        isAnimate = true;
        dismissOpenBar();
    }

    public void dismissOpenBar() {
        mIvOpenBar.animate()
                .translationX(-mIvOpenBar.getLayoutParams().height)
                .translationY(-mIvOpenBar.getLayoutParams().height)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        expand();
                    }
                })
                .start();
    }

    public void expand() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mTargetExpandWidth);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mEtSearch.getLayoutParams().width =  (int) animation.getAnimatedValue();
                mEtSearch.requestLayout();

                if (mEtSearch.getLayoutParams().width == mTargetExpandWidth) {
                    showCloseBar();
                    showEditText();
                }
            }
        });

        animator.setDuration(300);
        animator.start();
    }

    public void showCloseBar() {
        mIvCloseBarTop.setTranslationX(mIvCloseBarTop.getLayoutParams().height);
        mIvCloseBarTop.setTranslationY(-mIvCloseBarTop.getLayoutParams().height);
        mIvCloseBarTop.setVisibility(View.VISIBLE);
        mIvCloseBarTop.setAlpha(0f);
        mIvCloseBarTop.animate()
                .alpha(1f)
                .translationX(0)
                .translationY(0)
                .setDuration(200)
                .setListener(null)
                .setInterpolator(new OvershootInterpolator())
                .start();

        mIvCloseBarBottom.setTranslationX(mIvCloseBarBottom.getLayoutParams().height);
        mIvCloseBarBottom.setTranslationY(mIvCloseBarBottom.getLayoutParams().height);
        mIvCloseBarBottom.setVisibility(View.VISIBLE);
        mIvCloseBarBottom.setAlpha(0f);
        mIvCloseBarBottom.animate()
                .alpha(1f)
                .setStartDelay(200)
                .translationX(0)
                .translationY(0)
                .setDuration(200)
                .setInterpolator(new OvershootInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isAnimate = false;
                    }
                })
                .start();
    }

    public void showEditText() {
        mEtSearch.animate()
                .alpha(1f)
                .setDuration(300)
                .setListener(null)
                .start();
    }

    public void gone() {
        if (isAnimate) return;
        isAnimate = true;
        dismissCloseBar();
        dismissEditText();
    }

    public void dismissCloseBar() {
        mIvCloseBarTop.animate()
                .alpha(0f)
                .translationX(mIvCloseBarTop.getLayoutParams().height)
                .translationY(-mIvCloseBarTop.getLayoutParams().height)
                .setDuration(200)
                .setInterpolator(new AnticipateInterpolator())
                .setListener(null)
                .start();

        mIvCloseBarBottom.animate()
                .alpha(0f)
                .setStartDelay(200)
                .translationX(mIvCloseBarBottom.getLayoutParams().height)
                .translationY(mIvCloseBarBottom.getLayoutParams().height)
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mIvCloseBarTop.setVisibility(View.GONE);
                        mIvCloseBarBottom.setVisibility(View.GONE);
                        close();
                    }
                })
                .setInterpolator(new AnticipateInterpolator())
                .start();
    }

    public void dismissEditText() {
        mEtSearch.animate()
                .alpha(0f)
                .setDuration(300)
                .setListener(null)
                .start();
    }

    public void close() {
        ValueAnimator animator = ValueAnimator.ofInt(mTargetExpandWidth, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mEtSearch.getLayoutParams().width =  (int) animation.getAnimatedValue();
                mEtSearch.requestLayout();

                if (mEtSearch.getLayoutParams().width == 0) {
                    showOpenBar();
                }
            }
        });

        animator.setDuration(300);
        animator.start();
    }

    public void showOpenBar() {
        mIvOpenBar.animate()
                .translationX(0)
                .translationY(0)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isAnimate = false;
                    }
                })
                .start();
    }
}
