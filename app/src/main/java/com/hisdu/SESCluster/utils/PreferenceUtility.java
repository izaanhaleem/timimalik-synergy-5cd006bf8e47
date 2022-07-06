package com.hisdu.SESCluster.utils;

/**
 * Preference Utility
 * Created by Usman Arshad Kurd
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtility extends Activity {



    public static String getPreference(Context baseActivity, String prefName, String key) {
        SharedPreferences usePref = baseActivity.getSharedPreferences(prefName,
                MODE_PRIVATE);
        return usePref.getString(key, "");
    }

    /*
     * Function for setting values for shared preferences
     */

    public static void setPreference(Context base, String prefName, String key, String value) {

        SharedPreferences userPref = base.getSharedPreferences(prefName,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = userPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setBoolPreference(Context base, String prefName, String key, boolean value) {

        SharedPreferences userPref = base.getSharedPreferences(prefName,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = userPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolPreference(Context base, String prefName, String key) {
        SharedPreferences usePref = base.getSharedPreferences(prefName,
                MODE_PRIVATE);
        return usePref.getBoolean(key, false);
    }

    public static void setIntPreference(Context base, String prefName, String key, int value) {

        SharedPreferences userPref = base.getSharedPreferences(prefName,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = userPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getIntPreference(Context base, String prefName, String key) {
        SharedPreferences usePref = base.getSharedPreferences(prefName,
                MODE_PRIVATE);
        return usePref.getInt(key, 0);
    }

    public static void removeAllPreferences(Context base, String prefName) {

        SharedPreferences userPref = base.getSharedPreferences(prefName,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = userPref.edit();
        editor.clear();
        editor.apply();
    }
}
