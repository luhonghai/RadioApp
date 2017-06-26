package com.jae.radioapp.player;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import timber.log.Timber;

/**
 * Created by alex on 6/25/17.
 */

public class MediaPlayerService extends Service {

    // Binder given to clients
    private final IBinder iBinder = new LocalBinder();

    SimpleExoPlayer exoPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    // ---------- SERVICE FUNCTIONS ----------

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.e("onStartCommand()");

        // init exoPlayer in case of not init yet
        if (exoPlayer == null) initPlayer();

        try {
            String streamUrl = intent.getExtras().getString("url");
            if (streamUrl != null) playMedia(streamUrl);
            Timber.e("streamUrl=" + streamUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Timber.e("onDestroy()");
        super.onDestroy();
        stopMedia();
    }

    // ---------- PLAYER FUNCTIONS ----------
    // ---------- PLAYER FUNCTIONS ----------
    // ---------- PLAYER FUNCTIONS ----------

    private void initPlayer() {
        // 1. Create a default TrackSelector
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create a default LoadControl
        LoadControl loadControl = new DefaultLoadControl();

        // 3. Create the exoPlayer
        exoPlayer = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector, loadControl);
        exoPlayer.setPlayWhenReady(true);
    }

    private void playMedia(String streamUrl) {
        exoPlayer.stop();

        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), getApplicationContext().getPackageName());

        MediaSource mediaSource = null;

        if (streamUrl.startsWith("rtmp")) {
            mediaSource = new HlsMediaSource(Uri.parse(streamUrl), dataSourceFactory, null, null);
        } else {
            mediaSource = new ExtractorMediaSource(Uri.parse(streamUrl), dataSourceFactory,
                    new DefaultExtractorsFactory(), null, null);
        }

        // Prepare the player with the source.
        exoPlayer.prepare(mediaSource);
    }

    private void stopMedia() {
        if (exoPlayer != null) exoPlayer.release();
    }

    public class LocalBinder extends Binder {
        public MediaPlayerService getService() {
            return MediaPlayerService.this;
        }
    }

}
