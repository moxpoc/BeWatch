package com.example.moxpoc.bewatch;


import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.moxpoc.bewatch.ModelAPI.Watch;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class MainActivity extends AppCompatActivity {

    File file;
    TextView  pedometerText, pulseText, chargeText;
    ProgressBar pedometerProgress, pulseProgress;

    ApiImpl api;
    PreferencesLoad load;

    public String imei = "00000000000000";
    String goalSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar profileToolbar = findViewById(R.id.profileToolbar);
        profileToolbar.setTitle("");
        setSupportActionBar(profileToolbar);

        pedometerText = findViewById(R.id.textViewPedometer);
        pulseText = findViewById(R.id.textViewHeartRate);
        chargeText = findViewById(R.id.watchChargeText);

        pedometerProgress = findViewById(R.id.progressBarPedometr);
        pulseProgress = findViewById(R.id.progressBarHeartRate);

        profileToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });


        load = new PreferencesLoad(getApplicationContext());
        api = new ApiImpl(getApplicationContext());
        api.getWatch();
        Watch watch = load.getWatch();
        goalSteps = load.getGoalSteps();




        BottomNavigationView bottomMenu = findViewById(R.id.bottomNavigationView);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.statistics_item:
                        Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.call_item:
                        Intent intentS = new Intent(MainActivity.this, VoiceChatActivity.class);
                        startActivity(intentS);
                        break;
                    case R.id.myProfile_item:
                        Intent intentT = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intentT);
                        break;
                }
                return true;
            }
        });
        try {
            pedometerText.setText(String.valueOf(watch.getBeatHeart().getPedometer()));
            //Toast.makeText(this,String.valueOf(load.getWatch().getBeatHeart().getPedometer()), Toast.LENGTH_SHORT ).show();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        try {
            pulseText.setText(String.valueOf(load.getWatch().getBlood().getHeartrate()));
            //Toast.makeText(this,String.valueOf(load.getWatch().getBlood().getHeartrate()), Toast.LENGTH_SHORT ).show();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        try {
            chargeText.setText(String.valueOf(load.getWatch().getBeatHeart().getBattery()));
            Toast.makeText(this,String.valueOf(load.getWatch().getBeatHeart().getBattery()), Toast.LENGTH_SHORT ).show();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        try {
            Float percent = (float)((load.getWatch().getBeatHeart().getPedometer() * 100)/Integer.parseInt(goalSteps));
            pedometerProgress.setProgress(Math.round(percent));
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        try {
            pulseProgress.setProgress(load.getWatch().getBlood().getHeartrate());
        }catch (NullPointerException e){
            e.printStackTrace();
        }







        //Обработка Перехода в MyGoalsActivity
        CardView locationCard = findViewById(R.id.locationCard);
        locationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });

        //Обработка Перехода в MyGoalsActivity
        CardView goalCard = findViewById(R.id.goalCard);
        goalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyGoalsActivity.class);
                startActivity(intent);
            }
        });

        //Обработка Перехода в VoiceChatActivity
        CardView voicechatCard = findViewById(R.id.voicechatCard);
        voicechatCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VoiceChatActivity.class);
                startActivity(intent);
            }
        });

        //Обработка Перехода в BeatHeartActivity
        CardView pulseCard = findViewById(R.id.pulseCard);
        pulseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BeatHeartActivity.class);
                startActivity(intent);
            }
        });

        //Обработка Перехода в BloodPressureActivity
        CardView bpCard = findViewById(R.id.bpCard);
        bpCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BloodPressureActivity.class);
                startActivity(intent);
            }
        });

        //Обработка Перехода в OxygenActivity
        CardView oxygenCard = findViewById(R.id.oxygenCard);
        oxygenCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OxygenActivity.class);
                startActivity(intent);
            }
        });

        //Обработка Перехода в OxygenActivity
        CardView pedometerCard = findViewById(R.id.pedometerCard);
        pedometerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PedometerActivity.class);
                startActivity(intent);
            }
        });
        //Обработка Перехода в CalendarActivity
        CardView calendarCard = findViewById(R.id.calendarCard);
        calendarCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
       /* ProgressBar progressBar = findViewById(R.id.progressBarPedometr);
        progressBar.setProgressDrawable(getDrawable(R.drawable.test_circle));*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        final MenuItem item = menu.findItem(R.id.watchChargeItem);
        return true;
    }


    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu){
        final MenuItem chargeMenuItem = menu.findItem(R.id.watchChargeItem);
        //FrameLayout rootView = (FrameLayout) chargeMenuItem.getActionView();
        return super.onPrepareOptionsMenu(menu);
    }*/
}
