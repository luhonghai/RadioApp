package com.jae.radioapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jae.radioapp.RadioApplication;
import com.jae.radioapp.data.model.Area;

import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 6/7/17.
 */

public class PreferenceHelper {

    public static final String KEY_AUTH_TOKEN = "KEY_AUTH_TOKEN";

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

    public List<Area> getAreas() {
        try {
            InputStream is = RadioApplication.getInstance().getAssets().open("areas.json");

            String data = IOUtils.toString(is);
            Type type = new TypeToken<List<Area>>(){}.getType();
            List<Area> areas = new Gson().fromJson(data, type);
            return areas;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
