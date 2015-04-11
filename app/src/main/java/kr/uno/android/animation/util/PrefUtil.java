package kr.uno.android.animation.util;

import android.content.Context;
import android.content.SharedPreferences;

import kr.uno.android.animation.R;

import static android.content.SharedPreferences.Editor;

public class PrefUtil {

    static SharedPreferences preferences;

    static SharedPreferences getPrefs(Context context) {
        if (preferences == null) preferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        return preferences;
    }

    private static Editor getEditor(Context context) {
        return getPrefs(context).edit();
    }

    public static String getString(Context context, String key) {
        return getPrefs(context).getString(key, null);
    }

    public static boolean getBoolean(Context context, String key) {
        return getPrefs(context).getBoolean(key, false);
    }

    public static float getFloat(Context context, String key) {
        return getPrefs(context).getFloat(key, 0);
    }

    public static int getInt(Context context, String key) {
        return getPrefs(context).getInt(key, 0);
    }

    public static long getLong(Context context, String key) {
        return getPrefs(context).getLong(key, 0);
    }

    public static void put(Context context, String key, Object object) {
        Editor editor = getPrefs(context).edit();
        if (object instanceof String) editor.putString(key, (String) object);
        else if (object instanceof Boolean) editor.putBoolean(key, (boolean) object);
        else if (object instanceof Integer) editor.putInt(key, (int) object);
        else if (object instanceof Long) editor.putLong(key, (long) object);
        else if (object instanceof Float) editor.putFloat(key, (float) object);
        editor.commit();
    }

    public static void remove(Context context, String key) {
        Editor editor = getPrefs(context).edit();
        editor.remove(key);
        editor.commit();
    }

    public static void clear(Context context) {
        Editor editor = getPrefs(context).edit();
        editor.clear();
        editor.commit();
    }
}

