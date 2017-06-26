package com.jae.radioapp.data.evenbus;

/**
 * Created by alex on 6/27/17.
 */

public class PlayStatusEvent {

    public enum PlayStatus {
        PLAYING,
        PAUSE
    }

    public PlayStatus playStatus;

    public PlayStatusEvent(PlayStatus playStatus) {
        this.playStatus = playStatus;
    }
}
