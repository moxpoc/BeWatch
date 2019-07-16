package com.example.moxpoc.bewatch;

import com.example.moxpoc.bewatch.ModelAPI.Location;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface BeWatchAPI {

    @Headers("Content-Type: application/json")
    @GET("/location/{imei}")
    Call <Location> getLocation(@Path("imei") String imei);
}
