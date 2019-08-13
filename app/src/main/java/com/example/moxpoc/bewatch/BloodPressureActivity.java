package com.example.moxpoc.bewatch;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BloodPressureActivity extends AppCompatActivity {

    TextView chargeText, bpText;
    PreferencesLoad load;
    Button startBp;
    ApiImpl api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);

        Toolbar profileToolbar = findViewById(R.id.profileToolbar);
        profileToolbar.setTitle("");
        setSupportActionBar(profileToolbar);

        bpText = findViewById(R.id.bpTextBP);
        startBp = findViewById(R.id.startBp);
        //Событие кнопки назад(настройки)
        profileToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BloodPressureActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        load = new PreferencesLoad(getApplicationContext());
        api = new ApiImpl(getApplicationContext());
        startBp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.getBlood();
                try {
                    bpText.setText(load.getWatch().getBlood().getSbp() + "/" + load.getWatch().getBlood().getDbp());
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });

        try {
            bpText.setText(load.getWatch().getBlood().getSbp() + "/" + load.getWatch().getBlood().getDbp());
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        //Нижнее меню, навигация
        BottomNavigationView bottomMenu = findViewById(R.id.bottomNavigationView);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.statistics_item:
                        Intent intent = new Intent(BloodPressureActivity.this, StatisticsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.call_item:
                        String phoneNo = load.getWatch().getDeviceMobileNo();
                        if(!TextUtils.isEmpty(phoneNo)) {
                            String dial = "tel:" + phoneNo;
                            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                        }else {
                            Toast.makeText(BloodPressureActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.myProfile_item:
                        Intent intentT = new Intent(BloodPressureActivity.this, MainActivity.class);
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
