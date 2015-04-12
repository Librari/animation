package kr.uno.android.animation.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;

import java.util.List;

import kr.uno.android.animation.item.PagerItem;
import kr.uno.android.animation.ui.fragment.SamplePagerFragment;

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<PagerItem> mItemList;
    private SparseArrayCompat<Fragment> mSparseArrayCompat;

    public SampleFragmentPagerAdapter(FragmentManager fm, List<PagerItem> itemList) {
        super(fm);
        mItemList = itemList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mItemList.get(position).name;
    }

    @Override
    public int getCount() {
        return mItemList != null ? mItemList.size() : 0;
    }

    @Override
    public Fragment getItem(int position) {
        return getFragmentInstance(position);
    }

    public Fragment getFragmentInstance(int position) {
        if (mSparseArrayCompat == null) mSparseArrayCompat = new SparseArrayCompat<>();
        if (mSparseArrayCompat.get(position, null) == null) {
            Fragment fragment = new SamplePagerFragment();
            fragment.setArguments(new Bundle());
            fragment.getArguments().putSerializable("item", mItemList.get(position));
            mSparseArrayCompat.put(position, fragment);
        }
        return mSparseArrayCompat.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
