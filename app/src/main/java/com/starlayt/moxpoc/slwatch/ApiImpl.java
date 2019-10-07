package com.starlayt.moxpoc.slwatch;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.starlayt.moxpoc.slwatch.ModelAPI.Blood;
import com.starlayt.moxpoc.slwatch.ModelAPI.Location;
import com.starlayt.moxpoc.slwatch.ModelAPI.LoginRequest;
import com.starlayt.moxpoc.slwatch.ModelAPI.RegistrationRequest;
import com.starlayt.moxpoc.slwatch.ModelAPI.RegistrationResponse;
import com.starlayt.moxpoc.slwatch.ModelAPI.ResetRequest;
import com.starlayt.moxpoc.slwatch.ModelAPI.TokenResponse;
import com.starlayt.moxpoc.slwatch.ModelAPI.Watch;

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
    private boolean status;

    public ApiImpl(Context context){
        retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.url))
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        beWatchAPI = retrofit.create(BeWatchAPI.class);
        load = new PreferencesLoad(context);
        try {
            imei = load.getWatch().getImei();
        }catch (NullPointerException e){
            e.printStackTrace();
            imei = "0000000000000000";
        }
    }


    public void auth(LoginRequest loginRequest){
        Call<TokenResponse> auth = beWatchAPI.auth(loginRequest);
        auth.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                TokenResponse tokenResponse = response.body();
                try{
                    load.setToken(tokenResponse.getToken());


                }catch (NullPointerException e ){
                    e.printStackTrace();
                }
                status = true;
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.i("___SOME_PROBLEMS___", t.getMessage());
                status = false;
            }
        });
    }

    public boolean isStatus() {
        return status;
    }

    public void registration(final RegistrationRequest registrationRequest){
        Call<RegistrationResponse> registration = beWatchAPI.registration(registrationRequest);
        registration.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                System.out.println(response.body());
                RegistrationResponse registrationResponse = response.body();
                load.setPassword(registrationRequest.getPassword());
                try {
                    load.setLogin(registrationResponse.getLogin());
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Log.i("___SOME_PROBLEMS___", t.getMessage());
            }
        });
    }

    public void reset(ResetRequest resetRequest){
        Call<String> reset = beWatchAPI.reset(resetRequest);
        reset.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println(response.code());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("___SOME_PROBLEMS___", t.getMessage());
            }
        });
    }

    public void getWatch(){
        Call<Watch> getWatch = beWatchAPI.getWatch(imei, load.getBearer());
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

    public void getWatchByClient(){
        Call<Watch> getWatchByClient = beWatchAPI.getWatchByClient(load.getBearer());
        getWatchByClient.enqueue(new Callback<Watch>() {
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
        Call<Watch> updateWatch = beWatchAPI.updateWatch(load.getWatch(), load.getBearer());
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
        Call<Location> getLocation =  beWatchAPI.getLocation(imei, load.getBearer());

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
        Call<Blood> getBlood = beWatchAPI.getBlood(imei, load.getBearer());
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
