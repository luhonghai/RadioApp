package com.jae.radioapp.player;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.halosolutions.rtmpdump.RTMPSuck;
import com.jae.radioapp.R;
import com.jae.radioapp.data.evenbus.MediaPlayerStateChangeEvent;
import com.jae.radioapp.data.local.PreferenceHelper;
import com.jae.radioapp.data.model.Station;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;

import timber.log.Timber;

/**
 * Created by alex on 6/25/17.
 */

public class MediaPlayerService extends Service {

    int NOTIFICATION_ID = 0x123;

    enum PlaybackStatus {
        PLAYING,
        PAUSED
    }

    public static String EXTRA_DATA = "com.jae.radio.EXTRA_DATA";
    public static String ACTION_PLAY_NEW = "com.jae.radio.ACTION_PLAY_NEW";
    public static String ACTION_RESUME = "com.jae.radio.ACTION_RESUME";
    public static String ACTION_PAUSE = "com.jae.radio.ACTION_PAUSE";

    // Binder given to clients
    private final IBinder iBinder = new LocalBinder();

    // variables for player
    SimpleExoPlayer exoPlayer;
    Station currentStation;
//    String  playingStreamUrl;

    // variables for RTMPDump
//    HashMap<String, RTMPSuck> rtmpSuckHashMap;
//    HashMap<String, Integer> rtmpSuckPortMap;
    int startPort = 2135;

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    // ---------- SERVICE FUNCTIONS ----------

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return super.onStartCommand(intent, flags, startId);

        Timber.e("onStartCommand()=" + intent.getAction());

        // init exoPlayer in case of not init yet
        if (exoPlayer == null) initPlayer();

        // init map of rtmpsuck in case of not init yet

        // check ACTION
        try {
            String action = intent.getAction();
            if (ACTION_PLAY_NEW.equals(action)) { // open new
//                exoPlayer.stop(); // stop player

                String stationData = intent.getStringExtra(EXTRA_DATA);
                Type type = new TypeToken<Station>(){}.getType();
                Station station = new Gson().fromJson(stationData, type);
                openStation(station);

                // build notification
                buildNotification(PlaybackStatus.PLAYING);
            } else if (ACTION_RESUME.equals(action)) { // resume
                playMedia(currentStation.streamUrl); // play current station

                // build notification
                buildNotification(PlaybackStatus.PLAYING);
            } else if (ACTION_PAUSE.equals(action)) {
                exoPlayer.stop(); // stop player

                // build notification
                buildNotification(PlaybackStatus.PAUSED);
            }
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

        exoPlayer.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {}

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}

            @Override
            public void onLoadingChanged(boolean isLoading) {}

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Timber.e("onPlayerStateChanged=" + playbackState);
                EventBus.getDefault().post(new MediaPlayerStateChangeEvent(playbackState));
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {}

            @Override
            public void onPositionDiscontinuity() {}
        });
    }

    private void playMedia(String streamUrl) {
        exoPlayer.stop();

        Timber.e("playMedia streamUrl=" + streamUrl);

        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), getApplicationContext().getPackageName());

        MediaSource mediaSource = null;

        if (streamUrl.startsWith("rtmp")) {
            RtmpDataSource.RtmpDataSourceFactory rtmpDataSourceFactory = new RtmpDataSource.RtmpDataSourceFactory();
            mediaSource = new ExtractorMediaSource(Uri.parse(streamUrl), rtmpDataSourceFactory, new DefaultExtractorsFactory(), null, null);
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




    // ---------- NOTIFICATION FUNCTIONS ----------
    // ---------- NOTIFICATION FUNCTIONS ----------
    // ---------- NOTIFICATION FUNCTIONS ----------

    private void buildNotification(PlaybackStatus playbackStatus) {

        int notificationAction = R.drawable.ic_pause_dark;//needs to be initialized
        PendingIntent play_pauseAction = null;

        //Build a new notification according to the current state of the MediaPlayer
        if (playbackStatus == PlaybackStatus.PLAYING) {
            notificationAction = R.drawable.ic_pause_dark;
            //create the pause action
            play_pauseAction = playbackAction(playbackStatus);
        } else if (playbackStatus == PlaybackStatus.PAUSED) {
            notificationAction = R.drawable.ic_play_dark;
            //create the play action
            play_pauseAction = playbackAction(playbackStatus);
        }

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        // Create a new Notification
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setShowWhen(false)
                // Set the Notification style
                .setStyle(new NotificationCompat.MediaStyle()
                    // Show our playback controls in the compact notification view.
                    .setShowActionsInCompactView(0))
                // Set the Notification color
                .setColor(getResources().getColor(R.color.colorPrimary))
                // Set the large and small icons
                .setLargeIcon(largeIcon)
                .setSmallIcon(android.R.drawable.stat_sys_headset)
                // Set Notification content information
                .setContentTitle(currentStation.name)
                .setContentText(currentStation.asciiName)
                // Add playback actions
                .addAction(notificationAction, "pause", play_pauseAction)
                .setOngoing(PlaybackStatus.PLAYING.equals(playbackStatus));

        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    private PendingIntent playbackAction(PlaybackStatus playbackStatus) {
        Intent playbackAction = new Intent(this, MediaPlayerService.class);

        if (PlaybackStatus.PLAYING.equals(playbackStatus)) {
            playbackAction.setAction(ACTION_PAUSE);
            return PendingIntent.getService(this, 0, playbackAction, 0);
        } else {
            playbackAction.setAction(ACTION_RESUME);
            return PendingIntent.getService(this, 1, playbackAction, 0);
        }
    }

    // ---------- RTMP Dump FUNCTIONS ----------
    // ---------- RTMP Dump FUNCTIONS ----------
    // ---------- RTMP Dump FUNCTIONS ----------

    private void openStation(Station station) {
        String token = new PreferenceHelper(getApplicationContext()).getAuthToken().getToken();
        String stationId = station.id;

        if (currentStation != null && currentStation.id.equals(station.id)) {
            return;
        } else {
            if (currentStation != null) {
                currentStation.rtmpSuck.stop();
            }

            exoPlayer.stop();
            currentStation = station;
            RTMPSuck rtmpSuck = new RTMPSuck();
            int rtmpSuckPort = startPort++;
            currentStation.rtmpSuck = rtmpSuck;
            currentStation.rtmpSuckPort = rtmpSuckPort;
            createRTMPDumpProxy(token, stationId, rtmpSuck, rtmpSuckPort);
            new Handler().postDelayed(() -> {
                getPlayingStreamAndPlay(token, stationId, rtmpSuck, rtmpSuckPort);
            }, 100);
        }

        // TBS < station ID
        //rtmpe://f-radiko.smartstream.ne.jp/TBS/_definst_/simul-stream.stream
        // -> rtmp://127.0.0.1:1935/TBS/_definst_/simul-stream.stream
    }

    private void createRTMPDumpProxy(String token, String stationId, RTMPSuck rtmpSuck, int rtmpSuckPort) {
        new Thread(() -> {
            rtmpSuck.init("S:" + token, rtmpSuckPort);
        }).start();
    }

    private void getPlayingStreamAndPlay(String token, String stationId, RTMPSuck rtmpSuck, int rtmpSuckPort) {
        Timber.e("getPlayingStreamAndPlay():" + token + "," + stationId + "," + rtmpSuck + "," + rtmpSuckPort);

        rtmpSuck.update("S:" + token,"rtmpe://f-radiko.smartstream.ne.jp/" +  stationId + "/_definst_", stationId + "/_definst_");

        String streamUrl = "rtmp://127.0.0.1:" + rtmpSuckPort + "/" + stationId + "/_definst_/simul-stream.stream";
        currentStation.streamUrl = streamUrl;

        playMedia(streamUrl);
    }

}
