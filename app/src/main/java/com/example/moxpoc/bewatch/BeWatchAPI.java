package com.example.moxpoc.bewatch;

import com.example.moxpoc.bewatch.ModelAPI.Blood;
import com.example.moxpoc.bewatch.ModelAPI.Location;
import com.example.moxpoc.bewatch.ModelAPI.Watch;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BeWatchAPI {

    @Headers("Content-Type: application/json")
    @GET("/location/{imei}")
    Call <Location> getLocation(@Path("imei") String imei);

    @Headers("Content-Type: application/json")
    @PUT("/watch")
    Call <Watch> updateWatch(@Body Watch watch);

    @Headers("Content-Type: application/json")
    @GET("/watch/{imei}")
    Call <Watch> getWatch(@Path("imei") String imei);

    @Headers("Content-Type: application/json")
    @GET("/health/{imei}")
    Call <Blood> getBlood(@Path("imei") String imei);
}
