package com.jae.radioapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.jae.radioapp.databinding.ActivityMainBinding;
import com.jae.radioapp.player.MediaPlayerService;
import com.jae.radioapp.ui.FragmentChannelList;
import com.jae.radioapp.ui.FragmentLeftMenu;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;
    MediaPlayerService mediaPlayerService;
    ServiceConnection serviceConnection;
    boolean serviceBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initFragments();
        initUI();

//        bindService();
    }

    private void initFragments() {
        // menu
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.menu_frame, FragmentLeftMenu.getInstance())
                .commit();

        // channel list
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_frame, FragmentChannelList.getInstance())
                .commit();

        // player
    }

    private void initUI() {
        mBinding.toolBar.setNavigationOnClickListener(v -> mBinding.drawerLayout.openDrawer(Gravity.LEFT));


    }

    // ---------- FUNCTIONS ---------- //
    // ---------- FUNCTIONS ---------- //
    // ---------- FUNCTIONS ---------- //

    private void bindService() {
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                serviceBound = true;
                mediaPlayerService = ((MediaPlayerService.LocalBinder)service).getService();
                Toast.makeText(MainActivity.this, "onServiceConnected()", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                serviceBound = false;
                Toast.makeText(MainActivity.this, "onServiceDisconnected()", Toast.LENGTH_SHORT).show();
            }
        };

        Intent playerIntent = new Intent(this, MediaPlayerService.class);
        startService(playerIntent);
        bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (serviceBound) {
//            unbindService(serviceConnection);
//            mediaPlayerService.stopSelf();
//        }
    }
}
