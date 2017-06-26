package com.jae.radioapp.data.remote;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by alex on 6/7/17.
 */

public interface RadioService {

    String END_POINT = "https://program.halo-solutions.com/";

    @GET("/")
    void getPrograms(@Query("provider") String provider, @Query("channel") String channel, @Query("area") String areaId);

}
