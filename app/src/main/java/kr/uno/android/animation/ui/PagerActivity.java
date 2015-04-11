package kr.uno.android.animation.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.uno.android.animation.R;
import kr.uno.android.animation.adapter.SamplePagerAdapter;
import kr.uno.android.animation.item.PagerSampleItem;
import kr.uno.android.animation.util.DateUtil;
import kr.uno.android.animation.util.DisplayUtil;


public class PagerActivity extends ActionBarActivity {

    @InjectView(R.id.pager) ViewPager mPager;
    @InjectView(R.id.rl_cover) RelativeLayout mRlCover;
    @InjectView(R.id.tv_timer) TextView mTvTimer;
    @InjectView(R.id.rl_icon) RelativeLayout mRlIcon;
    @InjectView(R.id.iv_icon_horizontal) ImageView mIvIconHorizontal;
    @InjectView(R.id.iv_icon_vertical) ImageView mIvIconVertical;

    private int mDisplayWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        ButterKnife.inject(this);

        initValue();
    }

    public void initValue() {
        mDisplayWidth = DisplayUtil.getWidth(this) / 2;

        final List<PagerSampleItem> itemList = new ArrayList<>();
        itemList.add(new PagerSampleItem("1", R.drawable.sample1, "#ffffff"));
        itemList.add(new PagerSampleItem("2", R.drawable.sample2, "#000000"));
        itemList.add(new PagerSampleItem("3", R.drawable.sample3, "#ffffff"));

        SamplePagerAdapter adapter = new SamplePagerAdapter(this, itemList);
        mPager.setAdapter(adapter);

        // 아이콘 회전
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float normalizedposition = Math.abs(positionOffset);
                PagerSampleItem item = itemList.get(normalizedposition < 0.5f ? position : position + 1);
                int color = Color.parseColor(item.color);
                mTvTimer.setTextColor(color);
                mIvIconHorizontal.setBackgroundColor(color);
                mIvIconVertical.setBackgroundColor(color);
                mRlIcon.setRotation(360 * normalizedposition);
                mRlCover.requestLayout();
            }
        });

        // 페이지 패딩 조절
        mPager.setPageTransformer(false, new ViewPager.PageTransformer() {

            @Override
            public void transformPage(View view, float position) {

                // view
                RelativeLayout rlContent = (RelativeLayout) view.findViewById(R.id.rl_content);

                float normalizedposition = Math.abs(Math.abs(position) - 1);
                int padding = (int) (mDisplayWidth - (mDisplayWidth * normalizedposition));

                if (position == 0.0f || position == 1.0f) {
                    rlContent.setPadding(0, 0, 0, 0);
                } else {
                    if (position < 0.0f) rlContent.setPadding(padding, 0, -padding, 0);
                    if (position > 0.0f) rlContent.setPadding(-padding, 0, padding, 0);
                }
                view.requestLayout();
            }
        });

        Timer time = new Timer();
        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                setDate();
            }
        }, 0, 1000) ;
    }

    public void setDate() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvTimer.setText(DateUtil.getNow("HH\nmm\nss"));
            }
        });
    }
}
