
package com.jae.radioapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Area {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("areaId")
    @Expose
    public String areaId;
    @SerializedName("lon")
    @Expose
    public Float lon;
    @SerializedName("lat")
    @Expose
    public Float lat;
    @SerializedName("radius")
    @Expose
    public Float radius;

}
