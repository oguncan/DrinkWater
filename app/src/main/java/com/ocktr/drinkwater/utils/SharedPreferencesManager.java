package com.ocktr.drinkwater.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void init(Context context){
        preferences = context.getSharedPreferences("drinkwater", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }
    public static boolean isInitialized()
    {
        return preferences != null && editor != null;
    }

    public static void putString(String key, String value){
        editor.putString(key, value);
        editor.apply();
    }

    public static void putStringPending(String key, Boolean value)
    {
        editor.putBoolean(key, value);
    }


    public static String getString(String key, String defaultValue)
    {
        return preferences.getString(key, defaultValue);
    }

    public static void putBoolean(String key, Boolean value)
    {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void putBooleanPending(String key, Boolean value)
    {
        editor.putBoolean(key, value);
    }

    public static Boolean getBoolean(String key, Boolean defaultValue)
    {
        return preferences.getBoolean(key, defaultValue);
    }
}