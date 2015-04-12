package kr.uno.android.animation.ui;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewAnimationUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import kr.uno.android.animation.R;


public class RippleActivity extends ActionBarActivity {

    @InjectView(R.id.v_bg) View mVBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);
        ButterKnife.inject(this);
    }

    @OnClick({ R.id.v_bg })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.v_bg:
                // previously invisible view
                int cx = (mVBg.getLeft() + mVBg.getRight()) / 2;
                int cy = (mVBg.getTop() + mVBg.getBottom()) / 2;

                int finalRadius = Math.max(mVBg.getWidth(), mVBg.getHeight());
                Animator anim = ViewAnimationUtils.createCircularReveal(mVBg, cx, cy, 0, finalRadius);
                anim.start();
                break;
        }
    }
}
