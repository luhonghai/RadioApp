package com.jae.radioapp.data.remote;

import com.jae.radioapp.data.model.RadikoPrograms;
import com.jae.radioapp.data.model.Stations;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alex on 6/26/17.
 */

public interface RadikoService {

    String END_POINT = "http://radiko.jp/v2/";

    @GET("station/list/{area_id}.xml")
    Observable<Stations> getStations(@Path("area_id") String areaId);

    @GET("api/program/today")
    Observable<RadikoPrograms> getPrograms(@Query("area_id") String areaId);

}
