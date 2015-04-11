package kr.uno.android.animation.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import kr.uno.android.animation.util.FontUtil;

public class FontTextView extends TextView {

    public FontTextView(Context context) {
        super(context);
        init(context);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        setTypeface(FontUtil.getFontRegular(context));
    }

}
