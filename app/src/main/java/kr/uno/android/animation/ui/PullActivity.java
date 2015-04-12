package kr.uno.android.animation.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.uno.android.animation.R;
import kr.uno.android.animation.adapter.PullAdapter;
import kr.uno.android.animation.listener.RecyclerListenerAdapter;
import kr.uno.android.animation.ui.widget.BaseRecyclerView;

public class PullActivity extends ActionBarActivity {

    @InjectView(R.id.recycler_pull) BaseRecyclerView mRecyclerPull;

    private RecyclerView.LayoutManager mLayoutManager;
    private PullAdapter mAdapterPull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);
        ButterKnife.inject(this);

        initView();
        initControl();
        initValue();
    }

    private void initView() {
        mAdapterPull = new PullAdapter(this);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerPull.setLayoutManager(mLayoutManager);
        mRecyclerPull.setAdapter(mAdapterPull);
    }

    private void initControl() {

        mRecyclerPull.setOnRecyclerListener(new RecyclerListenerAdapter() {
            @Override
            public boolean isOffsetEnable() {
                return mAdapterPull.isExpanded();
            }

            @Override
            public void onScrollOffset(int offset, boolean isMove) {
                super.onScrollOffset(offset, isMove);
                if (isMove) mAdapterPull.setHeaderHeightOffset(offset);
                else mAdapterPull.foldingHeader();
            }
        });
    }

    private void initValue() {
        mAdapterPull.setHeader(R.drawable.sample1);
        mAdapterPull.setItemList(Arrays.asList((getString(R.string.sample_list)).split("\\. ")));
    }

}
