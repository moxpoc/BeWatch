package com.starlayt.moxpoc.slwatch;

import com.starlayt.moxpoc.slwatch.ModelAPI.Blood;
import com.starlayt.moxpoc.slwatch.ModelAPI.Location;
import com.starlayt.moxpoc.slwatch.ModelAPI.LoginRequest;
import com.starlayt.moxpoc.slwatch.ModelAPI.RegistrationRequest;
import com.starlayt.moxpoc.slwatch.ModelAPI.RegistrationResponse;
import com.starlayt.moxpoc.slwatch.ModelAPI.ResetRequest;
import com.starlayt.moxpoc.slwatch.ModelAPI.TokenResponse;
import com.starlayt.moxpoc.slwatch.ModelAPI.Watch;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BeWatchAPI {

    //Авторизация пользователя и получение токена
    @Headers("Content-Type: application/json")
    @POST("/auth/login")
    Call<TokenResponse> auth(@Body LoginRequest loginRequest);

    //Регистрация пользователя
    @Headers("Content-Type: application/json")
    @POST("/registration")
    Call<RegistrationResponse> registration(@Body RegistrationRequest registrationRequest);

    //Сброс пароля пользователя
    @Headers("Content-Type: application/json")
    @POST("/auth/reset")
    Call<String> reset(@Body ResetRequest resetRequest);

    //Получение геолокации часов по их imei
    @Headers("Content-Type: application/json")
    @GET("/location/{imei}")
    Call <Location> getLocation(@Path("imei") String imei, @Header("Authorization") String token);

    //Обновление информации часов на сервере
    @Headers("Content-Type: application/json")
    @PUT("/watch")
    Call <Watch> updateWatch(@Body Watch watch, @Header("Authorization") String token);

    //Получение объекта часов по imei
    @Headers("Content-Type: application/json")
    @GET("/watch/{imei}")
    Call <Watch> getWatch(@Path("imei") String imei, @Header("Authorization") String token);

    //Получение объекта часов по пользователю
    @Headers("Content-Type: application/json")
    @GET("/watch/client")
    Call <Watch> getWatchByClient(@Header("Authorization") String token);

    //Получение информации о здорровье(пульс, шаги, давление) по imei
    @Headers("Content-Type: application/json")
    @GET("/health/{imei}")
    Call <Blood> getBlood(@Path("imei") String imei, @Header("Authorization") String token);


}
