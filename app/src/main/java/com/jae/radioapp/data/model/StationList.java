package com.jae.radioapp.data.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by alex on 6/26/17.
 */

@Root(name = "stations")
public class StationList {

    @Attribute(name = "area_id")
    public String areaId;

    @Attribute(name = "area_name")
    public String areaName;

    @ElementList(inline = true)
    public List<Station> stations;

}
