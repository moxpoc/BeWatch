package com.example.moxpoc.bewatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.moxpoc.bewatch.ModelAPI.Blood;
import com.example.moxpoc.bewatch.ModelAPI.Location;
import com.example.moxpoc.bewatch.ModelAPI.Watch;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiImpl {

    private Retrofit retrofit;
    private PreferencesLoad load;
    private BeWatchAPI beWatchAPI;
    private String imei;

    public ApiImpl(Context context){
        retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.url))
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        beWatchAPI = retrofit.create(BeWatchAPI.class);
        load = new PreferencesLoad(context);
        imei = load.getWatch().getImei();
    }

    public void getWatch(){
        Call<Watch> getWatch = beWatchAPI.getWatch(imei);
        getWatch.enqueue(new Callback<Watch>() {
            @Override
            public void onResponse(Call<Watch> call, Response<Watch> response) {
                Watch watch =  response.body();
                load.setWatch(watch);

            }

            @Override
            public void onFailure(Call<Watch> call, Throwable t) {
                Log.i("__GET_WATCH_FAIL__", t.getMessage());
            }
        });
    }

    public void updateWatch(){
        Call<Watch> updateWatch = beWatchAPI.updateWatch(load.getWatch());
        updateWatch.clone().enqueue(new Callback<Watch>() {
            @Override
            public void onResponse(Call<Watch> call, Response<Watch> response) {

            }

            @Override
            public void onFailure(Call<Watch> call, Throwable t) {

            }
        });
    }

    public Location getLocation(){
        Call<Location> getLocation =  beWatchAPI.getLocation(imei);

        getLocation.enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                Location location = response.body();
                load.updateLocation(location);
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {

            }
        });
        return load.getWatch().getLocation();
    }

    public Blood getBlood(){
        Call<Blood> getBlood = beWatchAPI.getBlood(imei);
        getBlood.enqueue(new Callback<Blood>() {
            @Override
            public void onResponse(Call<Blood> call, Response<Blood> response) {
                Blood blood = response.body();
                load.updateBlood(blood);
            }

            @Override
            public void onFailure(Call<Blood> call, Throwable t) {

            }
        });
        return load.getWatch().getBlood();
    }


}
