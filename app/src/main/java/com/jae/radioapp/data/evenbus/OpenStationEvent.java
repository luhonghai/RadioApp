package com.jae.radioapp.data.evenbus;

import com.jae.radioapp.data.model.Station;

/**
 * Created by alex on 6/27/17.
 */

public class OpenStationEvent {

    public Station station;

    public OpenStationEvent(Station station) {
        this.station = station;
    }

}
