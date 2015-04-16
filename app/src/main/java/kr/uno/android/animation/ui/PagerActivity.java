package kr.uno.android.animation.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import kr.uno.android.animation.R;
import kr.uno.android.animation.adapter.PagerPullAdapter;
import kr.uno.android.animation.item.PagerItem;
import kr.uno.android.animation.ui.widget.BaseRecyclerView;
import kr.uno.android.animation.util.DisplayUtil;


public class PagerActivity extends ActionBarActivity {

    public static final long DURATION = 200;
    public static final long MENU_DURATION = 300;
    public static final long MENU_DELAY = 100;

    @InjectView(R.id.rl_menu_open) RelativeLayout mRlMenuOpen;
    @InjectView(R.id.iv_menu_bg) ImageView mIvMenuBg;
    @InjectView(R.id.rl_menu) RelativeLayout mRlMenu;
    @InjectView(R.id.tv_menu_open) TextView mTvMenuOpen;
    @InjectView(R.id.tv_menu_done) TextView mTvMenuDone;
    @InjectViews({ R.id.rl_menu_home, R.id.rl_menu_ideas, R.id.rl_menu_contact }) List<RelativeLayout> mListRlMenu;
    @InjectView(R.id.recycler_pull) BaseRecyclerView mRecyclerPagerPull;

    private RecyclerView.LayoutManager mLayoutManager;
    private PagerPullAdapter mAdapterPagerPull;

    private float mScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        ButterKnife.inject(this);

        initView();
        initValue();
    }

    private void initView() {
        mAdapterPagerPull = new PagerPullAdapter(this);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerPagerPull.setLayoutManager(mLayoutManager);
        mRecyclerPagerPull.setAdapter(mAdapterPagerPull);
    }

    private void initValue() {

        int height = (int) (DisplayUtil.getHeight(this) * 2.2f);
        mScale = height / DisplayUtil.getPixelFromDp(this, 50);

        final List<PagerItem> itemList = new ArrayList<>();
        itemList.add(new PagerItem("1", R.drawable.sample1, "#ffffff"));
        itemList.add(new PagerItem("2", R.drawable.sample2, "#000000"));
        itemList.add(new PagerItem("3", R.drawable.sample3, "#ffffff"));

        mAdapterPagerPull.setPagerItems(itemList);
        mAdapterPagerPull.setListItems(Arrays.asList((getString(R.string.sample_list)).split("\\. ")));
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
            rlMenu.setTranslationY(-20);
            rlMenu.animate()
                    .alpha(1f)
                    .translationY(0)
                    .setStartDelay(i == 0 ? DURATION / 2 : MENU_DELAY * (i + 1))
                    .setDuration(MENU_DURATION)
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
