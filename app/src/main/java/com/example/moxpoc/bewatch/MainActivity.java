package com.example.moxpoc.bewatch;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar profileToolbar = findViewById(R.id.profileToolbar);
        profileToolbar.setTitle("");
        setSupportActionBar(profileToolbar);

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
