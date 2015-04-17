package kr.uno.android.animation.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import kr.uno.android.animation.R;
import kr.uno.android.animation.adapter.PagerPullAdapter;
import kr.uno.android.animation.item.Product;
import kr.uno.android.animation.listener.ProductListener;
import kr.uno.android.animation.listener.RecyclerListenerAdapter;
import kr.uno.android.animation.ui.widget.BaseRecyclerView;
import kr.uno.android.animation.util.DisplayUtil;


public class PagerActivity extends ActionBarActivity {

    public static final long DURATION = 200;
    public static final long MENU_DURATION = 300;
    public static final long MENU_DELAY = 100;

    @InjectView(R.id.iv_menu_bg) ImageView mIvMenuBg;
    @InjectView(R.id.rl_menu) RelativeLayout mRlMenu;
    @InjectViews({ R.id.iv_menu_top, R.id.iv_menu_middle_left, R.id.iv_menu_middle_right, R.id.iv_menu_bottom }) List<ImageView> mListIvMenu;
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
        initControl();
        initValue();
    }

    private void initView() {
        mAdapterPagerPull = new PagerPullAdapter(this, new ProductListener() {
            @Override
            public void onClickProduct(Product item) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(item.url)));
            }
        });
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerPagerPull.setLayoutManager(mLayoutManager);
        mRecyclerPagerPull.setAdapter(mAdapterPagerPull);
    }

    private void initControl() {
        mRecyclerPagerPull.setOnRecyclerListener(new RecyclerListenerAdapter() {
            @Override
            public boolean isOffsetEnable() {
                return mAdapterPagerPull.isExpanded();
            }

            @Override
            public void onScrollOffset(int offset, boolean isMove) {
                super.onScrollOffset(offset, isMove);
                if (isMove) mAdapterPagerPull.setHeaderHeightOffset(offset);
                else mAdapterPagerPull.foldingHeader();
            }
        });
    }

    private void initValue() {

        int height = (int) (DisplayUtil.getHeight(this) * 2.2f);
        mScale = height / DisplayUtil.getPixelFromDp(this, 50);

        final List<Product> pagerItems = new ArrayList<>();
        pagerItems.add(new Product("헤던 카라배색 맥 코트", "인기 절정 맥코트!\n전사이즈 당일발송!", "http://www.mr-s.co.kr/web/product/medium/khj10229_24133.jpg", "http://m.mr-s.co.kr/product/detail.html?product_no=24133&cate_no=1&display_group=2", "#ffffff"));
        pagerItems.add(new Product("네이틀 밴딩 슬랙스", "초특가기획!! 밴딩으로 편안하게 착용!", "http://www.mr-s.co.kr/web/product/medium/khj10229_27934.jpg", "http://m.mr-s.co.kr/product/detail.html?product_no=27934&cate_no=1&display_group=3", "#ffffff"));
        pagerItems.add(new Product("루빈디 차이나 셔츠", "세련된 차이나 넥라인의\n슬림한 핏감으로 댄디함 UP!", "http://www.mr-s.co.kr/web/product/medium/khj10229_25857.jpg", "http://m.mr-s.co.kr/product/detail.html?product_no=25857&cate_no=1&display_group=2", "#000000"));
        mAdapterPagerPull.setPagerItems(pagerItems);

        final List<Product> listItems = new ArrayList<>();
        listItems.add(new Product("바딘 디스트로이드 스키니", "허벅지는 일자로 슬림한 핏!", "http://www.mr-s.co.kr/web/product/tiny/khj10229_25352.jpg", "http://m.mr-s.co.kr/product/detail.html?product_no=25352&cate_no=1&display_group=2","#ffffff"));
        listItems.add(new Product("체논 데님 스키니", "심플 데님 스키니로 다양한 패션 연출!", "http://www.mr-s.co.kr/web/product/tiny/khj10229_27639.jpg", "http://m.mr-s.co.kr/product/detail.html?product_no=27639&cate_no=1&display_group=2","#ffffff"));
        listItems.add(new Product("테일러 밴딩 슬랙스", "셔츠빠짐방지 디테일과 허리밴딩!", "http://www.mr-s.co.kr/web/product/tiny/khj10229_27503.gif", "http://m.mr-s.co.kr/product/detail.html?product_no=27503&cate_no=1&display_group=2","#ffffff"));
        listItems.add(new Product("티스컬 헨리넥 셔츠", "세련된 헨리넥 디자인으로 부담없이!", "http://www.mr-s.co.kr/web/product/tiny/khj10229_27972.jpg", "http://m.mr-s.co.kr/product/detail.html?product_no=27972&cate_no=1&display_group=3", "#ffffff"));
        mAdapterPagerPull.setListItems(listItems);
    }

    @OnClick({
            R.id.rl_menu_toggle, R.id.rl_menu,
            R.id.rl_menu_home, R.id.rl_menu_ideas, R.id.rl_menu_contact
    })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_menu_toggle: show(); break;
            case R.id.rl_menu: dismiss(); break;

            case R.id.rl_menu_home:
            case R.id.rl_menu_ideas:
            case R.id.rl_menu_contact: dismiss(); break;
        }
    }

    public void show() {
        mRlMenu.setVisibility(View.VISIBLE);
        mListIvMenu.get(0).animate().translationY(mListIvMenu.get(0).getLayoutParams().height * 2).setStartDelay(0).setDuration(DURATION).start();
        mListIvMenu.get(3).animate().translationY(mListIvMenu.get(0).getLayoutParams().height * -2).setStartDelay(0).setDuration(DURATION).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mListIvMenu.get(0).setVisibility(View.INVISIBLE);
                mListIvMenu.get(3).setVisibility(View.INVISIBLE);
            }
        }).start();
        mListIvMenu.get(1).animate().rotation(45).setStartDelay(DURATION).setDuration(DURATION).start();
        mListIvMenu.get(2).animate().rotation(-45).setStartDelay(DURATION).setDuration(DURATION).start();
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
        mListIvMenu.get(1).animate().rotation(0).setStartDelay(0).setDuration(DURATION).start();
        mListIvMenu.get(2).animate().rotation(0).setStartDelay(0).setDuration(DURATION).start();
        mListIvMenu.get(0).animate().translationY(0).setStartDelay(DURATION).setDuration(DURATION).start();
        mListIvMenu.get(3).animate().translationY(0).setStartDelay(DURATION).setDuration(DURATION).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mListIvMenu.get(0).setVisibility(View.VISIBLE);
                mListIvMenu.get(3).setVisibility(View.VISIBLE);
            }
        }).start();
        mIvMenuBg.animate().scaleX(1f).scaleY(1f).setDuration(DURATION).start();
    }

    @Override
    public void onBackPressed() {
        if (mRlMenu.getVisibility() == View.VISIBLE) dismiss();
        else super.onBackPressed();
    }
}
