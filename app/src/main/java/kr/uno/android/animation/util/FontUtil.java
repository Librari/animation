package kr.uno.android.animation.util;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

public class FontUtil {
     
    static final boolean USE_FONT = true;
    static Typeface mTypefaceR = null;

    public static void initFont(Context context){
        mTypefaceR = Typeface.createFromAsset(context.getAssets(), "monofonto.ttf");
    }
 
    public static Typeface getFontRegular(Context context){
        if(mTypefaceR == null && USE_FONT){
            initFont(context);
        }
        return mTypefaceR;
    }

    public static void initDefaultFont(Context context) {
        setDefaultFont(getFontRegular(context));
    }

    public static void setDefaultFont(Typeface font) {
        replaceFont("DEFAULT", font);
        replaceFont("SANS_SERIF", font);
        replaceFont("SERIF", font);
    }

    protected static void replaceFont(String staticTypefaceFieldName, final Typeface newTypeface) {
        try {
            Field StaticField = Typeface.class.getDeclaredField(staticTypefaceFieldName);
            StaticField.setAccessible(true);
            StaticField.set(null, newTypeface);
        } catch (Exception e) { LogUtil.e(e); }
    }
 
}