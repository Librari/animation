package kr.uno.android.animation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import kr.uno.android.animation.R;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.rl_menu) RelativeLayout mRlMenu;
    @InjectView(R.id.iv_bg) ImageView mIvBg;
    @InjectViews({
            R.id.rl_menu_search,
            R.id.rl_menu_menu,
            R.id.rl_menu_pager,
            R.id.rl_menu_pull,
            R.id.rl_menu_ripple
    })
    List<RelativeLayout> mListRlMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        initView();
    }

    private void initView() {

        mRlMenu.setVisibility(View.VISIBLE);
        mIvBg.setVisibility(View.VISIBLE);
        mIvBg.setAlpha(0f);

        mRlMenu.animate().alpha(1f).setDuration(1000).start();
        mIvBg.animate().alpha(1f).setDuration(1000).start();

        long startDelay = 500;
        long duration = 200;
        for (int i = 0; i < mListRlMenu.size(); i++) {
            final RelativeLayout rlMenu = mListRlMenu.get(i);
            rlMenu.setAlpha(0f);
            rlMenu.animate()
                    .alpha(1f)
                    .setStartDelay(startDelay + (duration * i))
                    .setDuration(duration)
                    .start();
        }
    }

    @OnClick({
            R.id.rl_menu_search,
            R.id.rl_menu_menu,
            R.id.rl_menu_pager,
            R.id.rl_menu_pull,
            R.id.rl_menu_ripple
    })
    public void onClick(View v) {

        Intent intent = null;

        switch (v.getId()) {
            case R.id.rl_menu_search: intent = new Intent(this, SearchActivity.class); break;       // 검색
            case R.id.rl_menu_menu: intent = new Intent(this, MenuActivity.class); break;           // 메뉴
            case R.id.rl_menu_pager: intent = new Intent(this, PagerActivity.class); break;         // 페이저
            case R.id.rl_menu_pull: intent = new Intent(this, PullActivity.class); break;           // 당김 효과
            case R.id.rl_menu_ripple: intent = new Intent(this, RippleActivity.class); break;       // 리플 이펙트
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
