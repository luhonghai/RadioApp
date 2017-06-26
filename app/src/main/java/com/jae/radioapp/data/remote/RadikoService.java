package com.jae.radioapp.data.remote;

import com.jae.radioapp.data.model.StationList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alex on 6/26/17.
 */

public interface RadikoService {

    String END_POINT = "http://radiko.jp/v2/";

    @GET("program/today")
    void getPrograms(@Query("area_id") String areaId);

    @GET("station/list/{area_id}.xml")
    Observable<StationList> getStations(@Path("area_id") String areaId);


}
