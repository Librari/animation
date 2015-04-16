package kr.uno.android.animation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import kr.uno.android.animation.R;


public class MainActivity extends ActionBarActivity {

    public static final long MENU_DURATION = 300;
    public static final long MENU_DELAY = 100;

    @InjectView(R.id.rl_menu) RelativeLayout mRlMenu;
    @InjectViews({
            R.id.rl_menu_search,
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
        mRlMenu.animate().alpha(1f).setDuration(1000).start();

        for (int i = 0; i < mListRlMenu.size(); i++) {
            final RelativeLayout rlMenu = mListRlMenu.get(i);
            rlMenu.setAlpha(0f);
            rlMenu.setTranslationY(-20);
            rlMenu.animate()
                    .alpha(1f)
                    .translationY(0)
                    .setStartDelay(MENU_DELAY * (i + 1))
                    .setDuration(MENU_DURATION)
                    .start();
        }
    }

    @OnClick({
            R.id.rl_menu_search,
            R.id.rl_menu_pager,
            R.id.rl_menu_pull,
            R.id.rl_menu_ripple
    })
    public void onClick(View v) {

        Intent intent = null;

        switch (v.getId()) {
            case R.id.rl_menu_search: intent = new Intent(this, SearchActivity.class); break;       // 검색
            case R.id.rl_menu_pager: intent = new Intent(this, PagerActivity.class); break;         // 페이저
            case R.id.rl_menu_pull: intent = new Intent(this, PullActivity.class); break;           // 당김 효과
            case R.id.rl_menu_ripple: intent = new Intent(this, RippleActivity.class); break;       // 리플 이펙트
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
