package com.example.moxpoc.bewatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.moxpoc.bewatch.ModelAPI.BeatHeart;
import com.example.moxpoc.bewatch.ModelAPI.Blood;
import com.example.moxpoc.bewatch.ModelAPI.Location;
import com.example.moxpoc.bewatch.ModelAPI.Watch;
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
    private SharedPreferences watchSettings;
    private static Watch watch;
    private ObjectMapper mapper = new ObjectMapper();

    public PreferencesLoad(Context context){
        watchSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
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
}