package com.jae.radioapp.data.evenbus;

/**
 * Created by alex on 6/28/17.
 */

public class MediaPlayerStateChangeEvent {

    public int state;

    public MediaPlayerStateChangeEvent(int state) {
        this.state = state;
    }
}
