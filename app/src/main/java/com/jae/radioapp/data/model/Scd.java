package com.jae.radioapp.data.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by alex on 6/27/17.
 */

@Root(name = "scd")
public class Scd {

    @Element(name = "progs")
    public Programs programs;
}
