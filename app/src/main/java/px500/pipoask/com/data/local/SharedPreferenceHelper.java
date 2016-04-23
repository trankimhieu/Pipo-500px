package px500.pipoask.com.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {
    private final static String PREF_FILE = "PIPO_500PX_PREF";

    private static Context context;
    private static SharedPreferenceHelper instance;

    public SharedPreferenceHelper(Context context) {
        SharedPreferenceHelper.context = context;
    }

    public static SharedPreferenceHelper getInstance(Context context) {
        if (instance == null)
            instance = new SharedPreferenceHelper(context);
        return instance;
    }


    public static void setSharedPreferenceString(String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setSharedPreferenceInt(String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void setSharedPreferenceBoolean(String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static String getSharedPreferenceString(String key, String defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);

        return settings.getString(key, defValue);
    }

    public static int getSharedPreferenceInt(String key, int defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);

        return settings.getInt(key, defValue);
    }

    public static boolean getSharedPreferenceBoolean(String key, boolean defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defValue);
    }
}
