package com.jae.radioapp.data.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by alex on 6/26/17.
 */

@Root(name = "radiko")
public class GetChannelResponse {

    @Element(name = "stations")
    public StationList stationList;

}
