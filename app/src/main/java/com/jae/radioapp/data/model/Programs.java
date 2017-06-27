package com.jae.radioapp.data.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by alex on 6/27/17.
 */

@Root(name = "progs", strict = false)
public class Programs {

    @ElementList(inline = true)
    public List<Program> programs;

}
