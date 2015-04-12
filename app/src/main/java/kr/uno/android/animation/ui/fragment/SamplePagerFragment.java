package kr.uno.android.animation.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.uno.android.animation.R;
import kr.uno.android.animation.item.PagerItem;
import kr.uno.android.animation.util.ImageLoader;

public class SamplePagerFragment extends Fragment {

    View mConvertView;

    @InjectView(R.id.iv_pager) ImageView ivPager;
    @InjectView(R.id.tv_text) TextView tvText;

    private PagerItem mPagerSampleItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mConvertView != null) {
            ViewGroup parent = (ViewGroup) mConvertView.getParent();
            parent.removeView(mConvertView);
        } else {
            mConvertView = inflater.inflate(R.layout.fragment_pager, container, false);
            ButterKnife.inject(this, mConvertView);
            initValue();
        }
        return mConvertView;
    }

    private void initValue() {
        if (getArguments() == null || getArguments().get("item") == null) return;
        mPagerSampleItem = (PagerItem) getArguments().get("item");
        ImageLoader.getInstance(getActivity()).blur(ivPager, mPagerSampleItem.image);
        tvText.setText("position : " + mPagerSampleItem.name);
        tvText.setTextColor(Color.parseColor(mPagerSampleItem.color));
    }
}
