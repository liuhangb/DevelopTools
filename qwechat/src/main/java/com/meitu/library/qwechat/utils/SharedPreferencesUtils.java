package com.meitu.library.qwechat.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {

    private static final String                 sSPname = "wechat";
    public static final String KEY_ADD_FRIEND_INFO = "KEY_ADD_FRIEND_INFO";
    private static SharedPreferencesUtils sInstance;
    private static SharedPreferences      sPreferences;

    private SharedPreferencesUtils() {
    }

    public static SharedPreferencesUtils getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SharedPreferencesUtils();
        }
        if (sPreferences == null) {
            sPreferences = context.getSharedPreferences(sSPname, Context.MODE_PRIVATE);
        }
        return sInstance;
    }

    public void put(String key, Object value) {
        SharedPreferences.Editor edit = sPreferences.edit();
        if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            edit.putFloat(key, (Float) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            edit.putLong(key, (Long) value);
        } else if (value instanceof String) {
            edit.putString(key, (String) value);
        }
        edit.apply();
    }

    public Object get(String key, Object defValue) {
        Object result = null;
        if (defValue instanceof Boolean) {
            result = sPreferences.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            result = sPreferences.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Integer) {
            result = sPreferences.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Long) {
            result = sPreferences.getLong(key, (Long) defValue);
        } else if (defValue instanceof String)
            result = sPreferences.getString(key, (String) defValue);
        return result;
    }
}
