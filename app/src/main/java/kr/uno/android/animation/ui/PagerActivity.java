package kr.uno.android.animation.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.uno.android.animation.R;
import kr.uno.android.animation.adapter.PagerPullAdapter;
import kr.uno.android.animation.item.PagerItem;
import kr.uno.android.animation.ui.widget.BaseRecyclerView;


public class PagerActivity extends ActionBarActivity {

    @InjectView(R.id.recycler_pull) BaseRecyclerView mRecyclerPagerPull;

    private RecyclerView.LayoutManager mLayoutManager;
    private PagerPullAdapter mAdapterPagerPull;

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

        final List<PagerItem> itemList = new ArrayList<>();
        itemList.add(new PagerItem("1", R.drawable.sample2, "#000000"));
        itemList.add(new PagerItem("2", R.drawable.sample1, "#ffffff"));
        itemList.add(new PagerItem("3", R.drawable.sample3, "#ffffff"));
        itemList.add(new PagerItem("4", R.drawable.sample2, "#000000"));

        mAdapterPagerPull.setPagerItems(itemList);
        mAdapterPagerPull.setListItems(Arrays.asList((getString(R.string.sample_list)).split("\\. ")));
    }
}
