package com.jae.radioapp.data.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 6/26/17.
 */

@Root(strict = false, name = "station")
public class Station {

    // for api get stations
    @Element(name = "id", required = false)
    public String id;

    @Element(name = "name", required = false)
    public String name;

    @Element(name = "ascii_name", required = false)
    public String asciiName;

    @Element(name = "logo_large", required = false)
    public String logoUrl;

    // for api get programs
    @Attribute(name = "id", required = false)
    public String idInProgramsAPI;
    @Element(name = "scd", required = false)
    public Scd scd;

    public String areaId;

}

