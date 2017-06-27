package com.jae.radioapp.data.model;

import com.jae.radioapp.utils.DateUtils;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by alex on 6/27/17.
 */

@Root(name = "prog", strict = false)
public class Program {

    @Attribute(name = "ft")
    public String fromTime;
    @Attribute(name = "to")
    public String toTime;
    @Element(name = "title")
    public String title;

    private String convertedFromTime;
    private String convertedToTime;

    public String getConvertedFromTime() {
        if (convertedFromTime == null) {
            convertedFromTime = DateUtils.convertDate(fromTime);
        }
        return convertedFromTime;
    }

    public String getConvertedToTime() {
        if (convertedToTime == null) {
            convertedToTime = DateUtils.convertDate(toTime);
        }
        return convertedToTime;
    }

}
