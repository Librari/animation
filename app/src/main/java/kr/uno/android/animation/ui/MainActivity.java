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

    public static final long DURATION = 200;

    @InjectView(R.id.rl_menu) RelativeLayout mRlMenu;
    @InjectView(R.id.iv_bg) ImageView mIvBg;
    @InjectViews({ R.id.rl_menu_search, R.id.rl_menu_menu, R.id.rl_menu_pager }) List<RelativeLayout> mListRlMenu;

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
        mRlMenu.setAlpha(0f);
        mIvBg.setAlpha(0f);
        mRlMenu.animate().alpha(1f).setDuration(DURATION).start();
        mIvBg.animate().alpha(1f).setDuration(DURATION).start();

        for (int i = 0; i < mListRlMenu.size(); i++) {
            final RelativeLayout rlMenu = mListRlMenu.get(i);
            rlMenu.setAlpha(0f);
            rlMenu.animate()
                    .alpha(1f)
                    .setStartDelay(DURATION * (i + 2))
                    .setDuration(DURATION)
                    .start();
        }
    }

    @OnClick({ R.id.rl_menu_search, R.id.rl_menu_menu, R.id.rl_menu_pager })
    public void onClick(View v) {

        Intent intent = null;

        switch (v.getId()) {
            case R.id.rl_menu_search: intent = new Intent(this, SearchActivity.class); break;       // 검색
            case R.id.rl_menu_menu: intent = new Intent(this, MenuActivity.class); break;           // 메뉴
            case R.id.rl_menu_pager: intent = new Intent(this, PagerActivity.class); break;         // 페이저
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
