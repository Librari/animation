package kr.uno.android.animation.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kr.uno.android.animation.R;
import kr.uno.android.animation.item.Product;
import kr.uno.android.animation.listener.ProductListener;
import kr.uno.android.animation.util.ImageLoader;

public class ProductPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<Product> mItemList;
    private LayoutInflater mInflater;
    private ProductListener mListener;

    public ProductPagerAdapter(Context context, List<Product> itemList, ProductListener listener) {
        mContext = context;
        mItemList = itemList;
        mListener = listener;

        mInflater = LayoutInflater.from(mContext);
    }

    public Product getItem(int position) {
        return getCount() > position ? mItemList.get(position) : null;
    }

    @Override
    public int getCount() {
        return mItemList != null ? mItemList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        final Product item = getItem(position);

        // view
        View view = mInflater.inflate(R.layout.row_product_pager_item, null);
        ImageView ivPager = (ImageView) view.findViewById(R.id.iv_pager);
        TextView tvText = (TextView) view.findViewById(R.id.tv_text);

        // control
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickProduct(item);
            }
        });

        // value
        ImageLoader.getInstance(mContext).load(ivPager, item.image);
        tvText.setText(item.description);
        tvText.setTextColor(Color.parseColor(item.color));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}