package com.starlayt.moxpoc.slwatch;

import android.content.Context;
import android.content.SharedPreferences;

import com.starlayt.moxpoc.slwatch.ModelAPI.BeatHeart;
import com.starlayt.moxpoc.slwatch.ModelAPI.Blood;
import com.starlayt.moxpoc.slwatch.ModelAPI.Location;
import com.starlayt.moxpoc.slwatch.ModelAPI.Watch;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class PreferencesLoad {

    public static final String APP_PREFERENCES = "watchsettings";
    public static final String APP_PREFERENCES_IMEI = "imei";
    public static final String APP_PREFERENCES_WATCH = "watch";
    public static final String APP_PREFERENCES_GOAL_STEPS = "goalsteps";
    public static final String APP_PREFERENCES_GOAL_DREAM = "goaldream";
    public static final String APP_PREFERENCES_GOAL_ACTIVITY = "goalactivity";
    public static final String APP_PREFERENCES_IMAGE_PATH = "image";

    public static final String APP_PREFERENCES_LOGIN = "login";
    public static final String APP_PREFERENCES_PASSWORD = "password";
    public static final String APP_PREFERENCES_TOKEN = "token";
    private SharedPreferences watchSettings;
    private static Watch watch;
    private ObjectMapper mapper = new ObjectMapper();

    public PreferencesLoad(Context context){
        watchSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        watch = new Watch();
    }

    public String getLogin(){
        if(watchSettings.contains(APP_PREFERENCES_LOGIN)){
            return watchSettings.getString(APP_PREFERENCES_LOGIN,"");
        }
        else {
            return null;
        }
    }

    public void setLogin(String login){
        SharedPreferences.Editor editor = watchSettings.edit();
        editor.putString(APP_PREFERENCES_LOGIN, login);
        editor.apply();
    }

    public String getPassword(){
        if(watchSettings.contains(APP_PREFERENCES_PASSWORD)){
            return watchSettings.getString(APP_PREFERENCES_PASSWORD,"");
        }
        else {
            return null;
        }
    }

    public void setPassword(String password){
        SharedPreferences.Editor editor = watchSettings.edit();
        editor.putString(APP_PREFERENCES_PASSWORD, password);
        editor.apply();
    }

    public String getToken(){
        if(watchSettings.contains(APP_PREFERENCES_TOKEN)){
            return watchSettings.getString(APP_PREFERENCES_TOKEN,"");
        }
        else {
            return null;
        }
    }

    public String getBearer(){
        if(watchSettings.contains(APP_PREFERENCES_TOKEN)){
            return "Bearer_" + watchSettings.getString(APP_PREFERENCES_TOKEN,"");
        }
        else {
            return "Bearer_";
        }
    }

    public void setToken(String token){
        SharedPreferences.Editor editor = watchSettings.edit();
        editor.putString(APP_PREFERENCES_TOKEN, token);
        editor.apply();
    }

    public String getGoalSteps(){
        String steps = "0";
        if(watchSettings.contains(APP_PREFERENCES_GOAL_STEPS)) {
            steps = watchSettings.getString(APP_PREFERENCES_GOAL_STEPS, "");
        }
        return steps;
    }

    public void setGoalSteps(String steps){
        SharedPreferences.Editor editor = watchSettings.edit();
        editor.putString(APP_PREFERENCES_GOAL_STEPS, steps);
        editor.apply();
    }

    public String getGoalDream(){
        String dream = "0";
        if(watchSettings.contains(APP_PREFERENCES_GOAL_DREAM)) {
            dream = watchSettings.getString(APP_PREFERENCES_GOAL_DREAM, "");
        }
        return dream;
    }

    public void setGoalDream(String dream){
        SharedPreferences.Editor editor = watchSettings.edit();
        editor.putString(APP_PREFERENCES_GOAL_DREAM, dream);
        editor.apply();
    }

    public String getGoalActivity(){
        String activity = "0";
        if(watchSettings.contains(APP_PREFERENCES_GOAL_ACTIVITY)) {
            activity = watchSettings.getString(APP_PREFERENCES_GOAL_ACTIVITY, "");
        }
        return activity;
    }

    public void setGoalActivity(String activity){
        SharedPreferences.Editor editor = watchSettings.edit();
        editor.putString(APP_PREFERENCES_GOAL_ACTIVITY, activity);
        editor.apply();
    }

    public Watch getWatch(){
        if(watchSettings.contains(APP_PREFERENCES_WATCH)) {
            String json = watchSettings.getString(APP_PREFERENCES_WATCH, "");
            try {
                watch = mapper.readValue(json, Watch.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return watch;
    }

    public void setWatch(Watch watch){
        try {
            String watchJson = mapper.writeValueAsString(watch);
            SharedPreferences.Editor editor = watchSettings.edit();
            editor.putString(APP_PREFERENCES_WATCH, watchJson);
            editor.apply();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateLocation(Location location){
        watch.setLocation(location);
        setWatch(watch);
    }

    public void updateBlood(Blood blood){
        watch.setBlood(blood);
        setWatch(watch);
    }

    public void updateBeatHeart(BeatHeart beatHeart){
        watch.setBeatHeart(beatHeart);
        setWatch(watch);
    }

    public String getImagePath(){
        String path = "©";
        if(watchSettings.contains(APP_PREFERENCES_IMAGE_PATH)) {
            path = watchSettings.getString(APP_PREFERENCES_IMAGE_PATH, "");

        }
        return path;
    }

    public void setImagePath(String path){
        SharedPreferences.Editor editor = watchSettings.edit();
        editor.putString(APP_PREFERENCES_IMAGE_PATH, path);
        editor.apply();
    }
}
