package com.jae.radioapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jae.radioapp.data.model.Station;
import com.mhealth.core.ui.BaseActivity;

import java.lang.reflect.Type;

/**
 * Created by alex on 6/27/17.
 */

public class OpenActivity extends BaseActivity {
    public static final String EXTRA_DATA = "EXTRA_DATA";
    public static final String EXTRA_FRAGMENT_NAME = "EXTRA_FRAGMENT_NAME";
    public static final String EXTRA_FRAGMENT_PLAYER_FULL = "EXTRA_FRAGMENT_PLAYER_FULL";

    public static Intent getIntent(Context context, String fragmentName) {
        Intent intent = new Intent(context, OpenActivity.class);
        intent.putExtra(EXTRA_FRAGMENT_NAME, fragmentName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkIntent();
    }

    private void checkIntent() {
        Fragment targetFragment = null;

        String fragmentName = getIntent().getStringExtra(EXTRA_FRAGMENT_NAME);
        if (fragmentName != null) {
            if (fragmentName.equals(EXTRA_FRAGMENT_PLAYER_FULL)) {
                try {
                    String data = getIntent().getStringExtra(EXTRA_DATA);
                    Type type = new TypeToken<Station>(){}.getType();
                    Station station = new Gson().fromJson(data, type);
                    targetFragment = FragmentPlayerFull.getInstance(station);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (targetFragment == null) {
            finish();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, targetFragment)
                    .commit();
        }
    }

}
