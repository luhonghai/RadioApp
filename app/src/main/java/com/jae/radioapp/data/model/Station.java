package com.jae.radioapp.data.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by alex on 6/26/17.
 */

@Root(strict = false, name = "station")
public class Station {

    @Element(name = "id")
    public String id;

    @Element(name = "name")
    public String name;

    @Element(name = "ascii_name")
    public String asciiName;

    @Element(name = "logo_large" )
    public String logoUrl;

}

