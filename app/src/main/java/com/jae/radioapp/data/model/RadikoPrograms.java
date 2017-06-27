package com.jae.radioapp.data.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by alex on 6/26/17.
 */

@Root(strict = false, name = "radiko")
public class RadikoPrograms {

    @Element(name = "stations")
    public Stations stationList;

}
