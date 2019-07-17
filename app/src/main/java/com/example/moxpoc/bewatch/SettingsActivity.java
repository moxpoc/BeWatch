package com.example.moxpoc.bewatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "watchsettings";
    public static final String APP_PREFERENCES_IMEI = "imei"; // imei
    public static final String APP_PREFERENCES_SEX = "sex";
    public static final String APP_PREFERENCES_WEIGHT = "weight";
    public static final String APP_PREFERENCES_HEIGHT = "height";
    public static final String APP_PREFERENCES_AGE = "age";
    //Создаем экземпляр настроек
    SharedPreferences watchSettings;

    EditText editSettImei;
    EditText editSettSex;
    EditText editSettWeight;
    EditText editSettHeight;
    EditText editSettAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        watchSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        Toolbar profileToolbar = findViewById(R.id.profileToolbar);
        profileToolbar.setTitle("");
        setSupportActionBar(profileToolbar);

        Button savePrefBtn = findViewById(R.id.savePreferencesBtn);
        editSettImei = findViewById(R.id.editSettImei);
        editSettSex = findViewById(R.id.editSettSex);
        editSettWeight = findViewById(R.id.editSettWeight);
        editSettHeight = findViewById(R.id.editSettHeight);
        editSettAge = findViewById(R.id.editSettAge);

        if(watchSettings.contains(APP_PREFERENCES_IMEI)){
            editSettImei.setText(watchSettings.getString(APP_PREFERENCES_IMEI,""));
        }
        if(watchSettings.contains(APP_PREFERENCES_SEX)){
            editSettSex.setText(watchSettings.getString(APP_PREFERENCES_SEX,""));
        }
        if(watchSettings.contains(APP_PREFERENCES_WEIGHT)){
            editSettWeight.setText(watchSettings.getString(APP_PREFERENCES_WEIGHT,""));
        }
        if(watchSettings.contains(APP_PREFERENCES_HEIGHT)){
            editSettHeight.setText(watchSettings.getString(APP_PREFERENCES_HEIGHT,""));
        }
        if(watchSettings.contains(APP_PREFERENCES_AGE)){
            editSettAge.setText(watchSettings.getString(APP_PREFERENCES_AGE,""));
        }

        profileToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomMenu = findViewById(R.id.bottomNavigationView);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.statistics_item:
                        Intent intent = new Intent(SettingsActivity.this, StatisticsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.call_item:
                        Intent intentS = new Intent(SettingsActivity.this, VoiceChatActivity.class);
                        startActivity(intentS);
                        break;
                    case R.id.myProfile_item:
                        Intent intentT = new Intent(SettingsActivity.this, MainActivity.class);
                        startActivity(intentT);
                        break;
                }
                return true;
            }
        });

        savePrefBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imei = editSettImei.getText().toString();
                String sex = editSettSex.getText().toString();
                String weight = editSettWeight.getText().toString();
                String height = editSettHeight.getText().toString();
                String age = editSettAge.getText().toString();
                SharedPreferences.Editor editor = watchSettings.edit();
                editor.putString(APP_PREFERENCES_IMEI, imei);
                editor.putString(APP_PREFERENCES_SEX, sex);
                editor.putString(APP_PREFERENCES_WEIGHT, weight);
                editor.putString(APP_PREFERENCES_HEIGHT, height);
                editor.putString(APP_PREFERENCES_AGE, age);
                editor.apply();
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        String imei = editSettImei.getText().toString();
        String sex = editSettSex.getText().toString();
        String weight = editSettWeight.getText().toString();
        String height = editSettHeight.getText().toString();
        String age = editSettAge.getText().toString();
        SharedPreferences.Editor editor = watchSettings.edit();
        editor.putString(APP_PREFERENCES_IMEI, imei);
        editor.putString(APP_PREFERENCES_SEX, sex);
        editor.putString(APP_PREFERENCES_WEIGHT, weight);
        editor.putString(APP_PREFERENCES_HEIGHT, height);
        editor.putString(APP_PREFERENCES_AGE, age);
        editor.apply();
    }
}
