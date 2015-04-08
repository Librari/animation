package kr.uno.android.animation.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import kr.uno.android.animation.R;
import kr.uno.android.animation.util.DisplayUtil;


public class SearchActivity extends ActionBarActivity {

    public static final long DURATION = 200;

    @InjectView(R.id.rl_search) RelativeLayout mRlSearch;
    @InjectView(R.id.et_search) EditText mEtSearch;
    @InjectView(R.id.iv_open_bar) ImageView mIvOpenBar;

    // 닫기
    @InjectView(R.id.iv_close_bar_top) ImageView mIvCloseBarTop;
    @InjectView(R.id.iv_close_bar_bottom) ImageView mIvCloseBarBottom;

    private int mOvalWidth;
    private int mTargetExpandWidth;
    private boolean isAnimate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
    }

    private void initView() {
        ButterKnife.inject(this);

        int padding = mRlSearch.getPaddingLeft() * 2;
        mOvalWidth = (int) getResources().getDimension(R.dimen.oval_width);
        mTargetExpandWidth = DisplayUtil.getWidth(SearchActivity.this) - mOvalWidth - padding;
    }

    @OnClick({ R.id.fl_toggle })
    public void onClick(View v) {

        switch (v.getId()) {

            // 열기, 닫기
            case R.id.fl_toggle:
                if (mIvCloseBarTop.getVisibility() == View.GONE) {
                    show();
                } else {
                    dismiss();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mIvCloseBarTop.getVisibility() == View.VISIBLE) dismiss();
        else super.onBackPressed();
    }

    /**
     * 1. 돋보기 손잡이 제거
     * 2. 텍스트창 펼침
     * 3. 닫기버튼 생성
     */
    public void show() {
        if (isAnimate) return;
        isAnimate = true;

        // 돋보기 손잡이
        mIvOpenBar.animate()
                .translationX(-mIvOpenBar.getLayoutParams().height)
                .translationY(-mIvOpenBar.getLayoutParams().height)
                .setStartDelay(0)
                .setDuration(DURATION)
                .setListener(null)
                .start();

        // 입력창
        ValueAnimator animator = ValueAnimator.ofInt(mOvalWidth, mTargetExpandWidth);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mEtSearch.getLayoutParams().width =  (int) animation.getAnimatedValue();
                mEtSearch.requestLayout();
            }
        });
        animator.setStartDelay(DURATION);
        animator.setDuration(DURATION);
        animator.start();

        // 닫기 상단바
        mIvCloseBarTop.setTranslationX(mIvCloseBarTop.getLayoutParams().height);
        mIvCloseBarTop.setTranslationY(-mIvCloseBarTop.getLayoutParams().height);
        mIvCloseBarTop.setVisibility(View.VISIBLE);
        mIvCloseBarTop.setAlpha(0f);
        mIvCloseBarTop.animate()
                .alpha(1f)
                .translationX(0)
                .translationY(0)
                .setStartDelay(DURATION * 2)
                .setDuration(DURATION)
                .setInterpolator(new OvershootInterpolator())
                .start();

        // 닫기 하단바
        mIvCloseBarBottom.setTranslationX(mIvCloseBarBottom.getLayoutParams().height);
        mIvCloseBarBottom.setTranslationY(mIvCloseBarBottom.getLayoutParams().height);
        mIvCloseBarBottom.setVisibility(View.VISIBLE);
        mIvCloseBarBottom.setAlpha(0f);
        mIvCloseBarBottom.animate()
                .alpha(1f)
                .translationX(0)
                .translationY(0)
                .setStartDelay(DURATION * 3)
                .setDuration(DURATION)
                .setInterpolator(new OvershootInterpolator())
                .start();

        // 텍스트
        mEtSearch.animate()
                .alpha(1f)
                .setStartDelay(DURATION * 2)
                .setDuration(DURATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isAnimate = false;
                    }
                })
                .start();
    }

    /**
     * 1. 닫기버튼 제거
     * 2. 텍스트창 접음
     * 3. 돋보기 손잡이 생성
     */
    public void dismiss() {
        if (isAnimate) return;
        isAnimate = true;

        // 닫기버튼 상단
        mIvCloseBarTop.animate()
                .alpha(0f)
                .translationX(mIvCloseBarTop.getLayoutParams().height)
                .translationY(-mIvCloseBarTop.getLayoutParams().height)
                .setDuration(DURATION)
                .setInterpolator(new AnticipateInterpolator())
                .setListener(null)
                .start();

        // 닫기버튼 하단
        mIvCloseBarBottom.animate()
                .alpha(0f)
                .translationX(mIvCloseBarBottom.getLayoutParams().height)
                .translationY(mIvCloseBarBottom.getLayoutParams().height)
                .setStartDelay(DURATION)
                .setDuration(DURATION).setInterpolator(new AnticipateInterpolator())
                .start();

        // 텍스트
        mEtSearch.animate()
                .alpha(0f)
                .setStartDelay(DURATION)
                .setDuration(DURATION)
                .setListener(null)
                .start();

        // 입력창
        ValueAnimator animator = ValueAnimator.ofInt(mTargetExpandWidth, mOvalWidth);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mEtSearch.getLayoutParams().width =  (int) animation.getAnimatedValue();
                mEtSearch.requestLayout();
            }
        });
        animator.setStartDelay(DURATION * 3);
        animator.setDuration(DURATION);
        animator.start();

        // 돋보기 손잡이
        mIvOpenBar.animate()
                .translationX(0)
                .translationY(0)
                .setStartDelay(DURATION * 4)
                .setDuration(DURATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        hideKeyboard();
                        mIvCloseBarTop.setVisibility(View.GONE);
                        mIvCloseBarBottom.setVisibility(View.GONE);
                        isAnimate = false;
                    }
                })
                .start();
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEtSearch.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
