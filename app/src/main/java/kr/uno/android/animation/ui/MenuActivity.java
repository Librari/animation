package kr.uno.android.animation.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import kr.uno.android.animation.R;
import kr.uno.android.animation.util.DisplayUtil;


public class MenuActivity extends ActionBarActivity {

    public static final long DURATION = 200;

    @InjectView(R.id.rl_menu_open) RelativeLayout mRlMenuOpen;
    @InjectView(R.id.iv_menu_bg) ImageView mIvMenuBg;
    @InjectView(R.id.rl_menu) RelativeLayout mRlMenu;
    @InjectView(R.id.tv_menu_open) TextView mTvMenuOpen;
    @InjectView(R.id.tv_menu_done) TextView mTvMenuDone;
    @InjectViews({ R.id.rl_menu_home, R.id.rl_menu_ideas, R.id.rl_menu_contact }) List<RelativeLayout> mListRlMenu;

    private float mScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.inject(this);

        initValue();
    }

    public void initValue() {
        int height = DisplayUtil.getHeight(this) * 3;
        mScale = height / DisplayUtil.getPixelFromDp(this, 50);
    }

    @OnClick({
            R.id.rl_menu_open, R.id.rl_menu,
            R.id.rl_menu_home, R.id.rl_menu_ideas, R.id.rl_menu_contact
    })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_menu_open: show(); break;
            case R.id.rl_menu: dismiss(); break;

            case R.id.rl_menu_home:
            case R.id.rl_menu_ideas:
            case R.id.rl_menu_contact: dismiss(); break;
        }
    }

    public void show() {
        mRlMenu.setVisibility(View.VISIBLE);
        mTvMenuOpen.animate().alpha(0f).setDuration(DURATION).start();
        mTvMenuDone.animate().alpha(1f).setDuration(DURATION).start();
        mIvMenuBg.animate().scaleX(mScale).scaleY(mScale).setDuration(DURATION).start();

        for (int i = 0; i < mListRlMenu.size(); i++) {
            final RelativeLayout rlMenu = mListRlMenu.get(i);
            rlMenu.setAlpha(0f);
            rlMenu.animate()
                    .alpha(1f)
                    .setStartDelay((DURATION / 2) * (i + 1))
                    .setDuration(DURATION / 2)
                    .start();
        }
    }

    public void dismiss() {
        mRlMenu.animate().alpha(0f).setDuration(DURATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mRlMenu.setAlpha(1f);
                        mRlMenu.setVisibility(View.GONE);
                    }
                }).start();
        mTvMenuOpen.animate().alpha(1f).setDuration(DURATION).start();
        mTvMenuDone.animate().alpha(0f).setDuration(DURATION).start();
        mIvMenuBg.animate().scaleX(1f).scaleY(1f).setDuration(DURATION).start();
    }

    @Override
    public void onBackPressed() {
        if (mRlMenu.getVisibility() == View.VISIBLE) dismiss();
        else super.onBackPressed();
    }
}
