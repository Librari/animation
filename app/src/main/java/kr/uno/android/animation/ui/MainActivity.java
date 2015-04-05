package kr.uno.android.animation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.uno.android.animation.R;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
    }

    @OnClick({ R.id.bt_search, R.id.bt_menu })
    public void onClick(View v) {

        Intent intent = null;

        switch (v.getId()) {
            case R.id.bt_search: intent = new Intent(this, SearchActivity.class); break;            // 검색
            case R.id.bt_menu: intent = new Intent(this, MenuActivity.class); break;                // 메뉴
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
