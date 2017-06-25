package com.jae.radioapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by alex on 6/7/17.
 */

public class PreferenceHelper {

    SharedPreferences mPref;

    public PreferenceHelper(Context mContext) {
        mPref = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void saveString(String key, String value) {
        mPref.edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return mPref.getString(key, null);
    }

}
