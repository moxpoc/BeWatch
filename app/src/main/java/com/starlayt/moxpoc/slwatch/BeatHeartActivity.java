package com.starlayt.moxpoc.slwatch;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;

import com.starlayt.moxpoc.slwatch.ModelAPI.Watch;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Retrofit;

public class BeatHeartActivity extends AppCompatActivity {

    TextView heartRateText, chargeText;
    Retrofit retrofit;
    Watch watch;
    ObjectMapper mapper;
    PreferencesLoad load;
    ApiImpl api;

    public String imei = "00000000000000";
    public static final String APP_PREFERENCES = "watchsettings";
    public static final String APP_PREFERENCES_IMEI = "imei";
    public static final String APP_PREFERENCES_WATCH = "watch";
    //Создаем экземпляр настроек
    SharedPreferences watchSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beat_heart);

        Toolbar profileToolbar = findViewById(R.id.profileToolbar);
        profileToolbar.setTitle("");
        setSupportActionBar(profileToolbar);

        watchSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        heartRateText = findViewById(R.id.textPulse);
        //mapper  = new ObjectMapper();

        /*if(watchSettings.contains(APP_PREFERENCES_WATCH)){
            String jsin = watchSettings.getString(APP_PREFERENCES_WATCH,"");
            try{
                watch = mapper.readValue(jsin, Watch.class);
                imei = watch.getImei();
            }catch (Exception e){
                e.printStackTrace();
            }
        }*/
        load = new PreferencesLoad(getApplicationContext());
        api = new ApiImpl(getApplicationContext());
        try {
            api.getBlood();//ошибка
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            heartRateText.setText(String.valueOf(load.getWatch().getBlood().getHeartrate()));
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        //Событие кнопки назад(настройки)
        profileToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BeatHeartActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        //Нижнее меню, навигация
        BottomNavigationView bottomMenu = findViewById(R.id.bottomNavigationView);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.statistics_item:
                        Intent intent = new Intent(BeatHeartActivity.this, StatisticsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.call_item:
                        /*Intent intentS = new Intent(BeatHeartActivity.this, VoiceChatActivity.class);
                        startActivity(intentS);*/
                        String phoneNo = watch.getDeviceMobileNo();
                        if(!TextUtils.isEmpty(phoneNo)) {
                            String dial = "tel:" + phoneNo;
                            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                        }else {
                            Toast.makeText(BeatHeartActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.myProfile_item:
                        Intent intentT = new Intent(BeatHeartActivity.this, MainActivity.class);
                        startActivity(intentT);
                        break;
                }
                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        final MenuItem item = menu.findItem(R.id.watchChargeItem);
        FrameLayout rootView = (FrameLayout)item.getActionView();
        chargeText = (TextView)rootView.findViewById(R.id.watchChargeText);
        try {
            chargeText.setText((load.getWatch().getBeatHeart().getBattery()) + "%");
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return true;
    }
}
