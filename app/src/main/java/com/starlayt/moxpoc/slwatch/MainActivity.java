package com.starlayt.moxpoc.slwatch;


import android.Manifest;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.starlayt.moxpoc.slwatch.ModelAPI.Watch;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    File file;
    TextView  pedometerText, pulseText, chargeText;
    ProgressBar pedometerProgress, pulseProgress;
    CircleImageView profileImage;
    Watch watch;
    ApiImpl api;
    PreferencesLoad load;

    public String imei = "00000000000000";
    String goalSteps;

    private final int PERMISSIONS_REQEST_STRORAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar profileToolbar = findViewById(R.id.profileToolbar);
        profileToolbar.setTitle("");
        setSupportActionBar(profileToolbar);

        pedometerText = findViewById(R.id.textViewPedometer);
        pulseText = findViewById(R.id.textViewHeartRate);

        profileImage = findViewById(R.id.profile_image);

        pedometerProgress = findViewById(R.id.progressBarPedometr);
        pulseProgress = findViewById(R.id.progressBarHeartRate);

        profileToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQEST_STRORAGE);
        }



        api = new ApiImpl(getApplicationContext());
        api.getWatchByClient();

        load = new PreferencesLoad(getApplicationContext());
        watch = load.getWatch();
        goalSteps = load.getGoalSteps();

        if(!load.getImagePath().contains("©"))
            profileImage.setImageBitmap(BitmapFactory.decodeFile(load.getImagePath()));


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
                        /*Intent intentS = new Intent(MainActivity.this, VoiceChatActivity.class);
                        startActivity(intentS);*/
                        String phoneNo = watch.getDeviceMobileNo();
                        if(!TextUtils.isEmpty(phoneNo)) {
                            String dial = "tel:" + phoneNo;
                            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                        }else {
                            Toast.makeText(MainActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                        }
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
            //chargeText.setText(String.valueOf(load.getWatch().getBeatHeart().getBattery()));
            Toast.makeText(this,String.valueOf(load.getWatch().getBeatHeart().getBattery()), Toast.LENGTH_SHORT ).show();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        try {
            int max = Integer.parseInt(goalSteps);
            int current = load.getWatch().getBeatHeart().getPedometer();
            Float percent = (float)0;
            try {
                percent = (float) ((load.getWatch().getBeatHeart().getPedometer() * 100) / Integer.parseInt(goalSteps));
            }catch (ArithmeticException e) {
                e.printStackTrace();
            }

            pedometerProgress.setProgress(Math.round(percent));
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        try {
            pulseProgress.setProgress(load.getWatch().getBlood().getHeartrate());
        }catch (NullPointerException e){
            e.printStackTrace();
        }







        //Обработка Перехода в LocationActivity
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
                Toast.makeText(MainActivity.this, getApplicationContext().getString(R.string.soon),Toast.LENGTH_SHORT).show();
                //startActivity(intent);
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

        //Обработка Перехода в PedometerActivity
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
                //startActivity(intent);
                Toast.makeText(MainActivity.this, getApplicationContext().getString(R.string.soon),Toast.LENGTH_SHORT).show();
            }
        });
       /* ProgressBar progressBar = findViewById(R.id.progressBarPedometr);
        progressBar.setProgressDrawable(getDrawable(R.drawable.test_circle));*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.watchChargeItem);
        FrameLayout rootView = (FrameLayout)item.getActionView();
        chargeText = (TextView)rootView.findViewById(R.id.watchChargeText);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        final MenuItem item = menu.findItem(R.id.watchChargeItem);
        FrameLayout rootView = (FrameLayout)item.getActionView();
        chargeText = (TextView)rootView.findViewById(R.id.watchChargeText);
        try {
            chargeText.setText((load.getWatch().getBeatHeart().getBattery()) + "%");
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return super.onPrepareOptionsMenu(menu);
    }
}
