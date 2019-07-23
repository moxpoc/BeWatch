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
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class MainActivity extends AppCompatActivity {

    Retrofit retrofit;
    File file;

    public String imei = "00000000000000";
    public static final String APP_PREFERENCES = "watchsettings";
    public static final String APP_PREFERENCES_IMEI = "imei";
    public static final String APP_PREFERENCES_WATCH = "watch";
    //Создаем экземпляр настроек
    SharedPreferences watchSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar profileToolbar = findViewById(R.id.profileToolbar);
        profileToolbar.setTitle("");
        setSupportActionBar(profileToolbar);

        profileToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        watchSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if(watchSettings.contains(APP_PREFERENCES_IMEI)){
            imei = watchSettings.getString(APP_PREFERENCES_IMEI,"");
        }

        file = new File(getFilesDir(), "watch.json");


        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.url))
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        BeWatchAPI beWatchAPI = retrofit.create(BeWatchAPI.class);
        Call<Watch> getWatch = beWatchAPI.getWatch(imei);
        getWatch.enqueue(new Callback<Watch>() {
            @Override
            public void onResponse(Call<Watch> call, Response<Watch> response) {
                Watch watch =  response.body();
                Toast.makeText(getApplicationContext(), watch.getLocation().getType(), Toast.LENGTH_LONG).show();
                ObjectMapper mapper = new ObjectMapper();
                ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

                try {
                    writer.writeValue(file, watch);
                    Toast.makeText(getApplicationContext(), watch.getImei(), Toast.LENGTH_LONG).show();
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

            @Override
            public void onFailure(Call<Watch> call, Throwable t) {
                Log.i("__GET_WATCH_FAIL__", t.getMessage());
            }
        });

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
